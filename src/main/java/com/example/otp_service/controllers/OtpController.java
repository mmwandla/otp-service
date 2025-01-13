package com.example.otp_service.controllers;

import com.example.otp_service.dtos.OtpRequestDto;
import com.example.otp_service.services.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    /**
     * Endpoint to generate an OTP for a given phone number.
     *
     * @param otpRequestDto the request containing the phone number
     * @return a ResponseEntity with the generated OTP code
     */
    @PostMapping("/generate")
    public ResponseEntity<String> generateOtp(@Valid @RequestBody OtpRequestDto otpRequestDto) {
        String otpCode = otpService.generateOtp(otpRequestDto);
        return ResponseEntity.ok("OTP generated successfully: " + otpCode);
    }

    /**
     * Endpoint to validate an OTP for a given phone number.
     *
     * @param phoneNumber the phone number to validate
     * @param otpCode the OTP code to validate
     * @return a ResponseEntity indicating whether the OTP is valid or not
     */
    @PostMapping("/validate")
    public ResponseEntity<String> validateOtp(@RequestParam String phoneNumber, @RequestParam String otpCode) {
        boolean isValid = otpService.validateOtp(phoneNumber, otpCode);

        if (isValid) {
            return ResponseEntity.ok("OTP is valid.");
        } else {
            return ResponseEntity.status(400).body("Invalid or expired OTP.");
        }
    }

    /**
     * Endpoint to manually trigger the deletion of expired OTPs.
     * This can also be scheduled as a background task.
     *
     * @return a ResponseEntity indicating the operation status
     */
    @DeleteMapping("/cleanup")
    public ResponseEntity<String> deleteExpiredOtps() {
        otpService.deleteExpiredOtps();
        return ResponseEntity.ok("Expired OTPs deleted successfully.");
    }
}
