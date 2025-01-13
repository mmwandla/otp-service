package com.example.otp_service.services;

import com.example.otp_service.dtos.OtpRequestDto;
import com.example.otp_service.models.Otp;
import com.example.otp_service.repositories.OtpRepository;
import com.example.otp_service.utils.OtpGeneratorUtil;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {

    private static final int OTP_EXPIRATION_MINUTES = 5;

    private final OtpRepository otpRepository;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    /**
     * Generates an OTP for a given phone number and saves it to the database.
     *
     * @param otpRequestDto the request containing the phone number
     * @return the generated OTP
     */
    @Transactional
    public String generateOtp(OtpRequestDto otpRequestDto) {
        String phoneNumber = otpRequestDto.getPhoneNumber();

        // Check if an OTP already exists for this phone number
        Optional<Otp> existingOtp = otpRepository.findByPhoneNumber(phoneNumber);
        existingOtp.ifPresent(otpRepository::delete);

        // Generate a new OTP
        String otpCode = OtpGeneratorUtil.generateOtp();

        // Create a new Otp entity and save it
        Otp otp = new Otp(phoneNumber, otpCode, LocalDateTime.now(), LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));
        otpRepository.save(otp);

        return otpCode;
    }

    /**
     * Validates the OTP for a given phone number.
     *
     * @param phoneNumber the phone number to validate the OTP against
     * @param otpCode the OTP code to validate
     * @return true if the OTP is valid, false otherwise
     */
    public boolean validateOtp(String phoneNumber, String otpCode) {
        Optional<Otp> otpOptional = otpRepository.findByPhoneNumber(phoneNumber);

        if (otpOptional.isEmpty()) {
            return false;
        }

        Otp otp = otpOptional.get();

        // Check if OTP is already validated or expired
        if (otp.getValidated() || otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            otpRepository.delete(otp);
            return false;
        }

        // Check if the OTP code matches
        if (otp.getOtpCode().equals(otpCode)) {
            otp.setValidated(true);
            otpRepository.save(otp);
            return true;
        }

        return false;
    }

    /**
     * Deletes expired OTPs from the database.
     */
    @Transactional
    public void deleteExpiredOtps() {
        otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }

    /**
     * Scheduled task to delete expired OTPs every hour.
     */
    @Scheduled(cron = "0 0 * * * *") // Every hour at the top of the hour
    @Transactional
    public void scheduledDeleteExpiredOtps() {
        otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        System.out.println("Scheduled cleanup of expired OTPs executed.");
    }
}
