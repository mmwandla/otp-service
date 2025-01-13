package com.example.otp_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class OtpRequestDto {

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format.")
    private String phoneNumber;

    public OtpRequestDto() {
    }

    public OtpRequestDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
