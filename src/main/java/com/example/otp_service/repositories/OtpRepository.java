package com.example.otp_service.repositories;

import com.example.otp_service.models.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    /**
     * Finds an OTP record by phone number.
     *
     * @param phoneNumber the phone number associated with the OTP
     * @return an Optional containing the Otp entity if found, or empty if not found
     */
    Optional<Otp> findByPhoneNumber(String phoneNumber);

    /**
     * Deletes expired OTP records by their expiration time.
     *
     * @param expiresAt the expiration time threshold
     */
    void deleteByExpiresAtBefore(java.time.LocalDateTime expiresAt);
}
