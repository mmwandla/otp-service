package com.example.otp_service.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OtpGeneratorUtilTest {

    @Test
    void testGenerateOtp_DefaultLength() {
        // Generate an OTP with the default length (6 digits)
        String otp = OtpGeneratorUtil.generateOtp();

        // Assert that the OTP is not null and has the correct length
        assertNotNull(otp, "Generated OTP should not be null.");
        assertEquals(6, otp.length(), "Generated OTP should have a length of 6.");

        // Assert that the OTP contains only numeric characters
        assertTrue(otp.matches("\\d+"), "Generated OTP should contain only numeric characters.");
    }

    @Test
    void testGenerateOtp_ZeroOrNegativeLength() {
        // Assert that an exception is thrown when the length is zero or negative
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            OtpGeneratorUtil.generateOtp(0);
        });
        assertEquals("OTP length must be greater than zero.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            OtpGeneratorUtil.generateOtp(-5);
        });
        assertEquals("OTP length must be greater than zero.", exception.getMessage());
    }

    @Test
    void testGenerateOtp_Uniqueness() {
        // Generate multiple OTPs and ensure they are unique
        String otp1 = OtpGeneratorUtil.generateOtp();
        String otp2 = OtpGeneratorUtil.generateOtp();

        // Assert that the OTPs are different
        assertNotEquals(otp1, otp2, "Generated OTPs should be unique.");
    }
}
