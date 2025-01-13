package com.example.otp_service.utils;

import java.security.SecureRandom;

public class OtpGeneratorUtil {

    private static final int DEFAULT_OTP_LENGTH = 6;
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates a random numeric OTP with the default length of 6 digits.
     *
     * @return the generated OTP as a String
     */
    public static String generateOtp() {
        return generateOtp(DEFAULT_OTP_LENGTH);
    }

    /**
     * Generates a random numeric OTP with the specified length.
     *
     * @param length the length of the OTP to generate
     * @return the generated OTP as a String
     */
    public static String generateOtp(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("OTP length must be greater than zero.");
        }

        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(NUMERIC_CHARACTERS.charAt(random.nextInt(NUMERIC_CHARACTERS.length())));
        }
        return otp.toString();
    }
}
