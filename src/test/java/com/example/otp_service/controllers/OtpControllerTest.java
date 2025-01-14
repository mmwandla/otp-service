package com.example.otp_service.controllers;

import com.example.otp_service.dtos.OtpRequestDto;
import com.example.otp_service.services.OtpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OtpControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OtpService otpService;

    @InjectMocks
    private OtpController otpController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private OtpRequestDto otpRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(otpController).build();

        // Prepare a sample request DTO
        otpRequestDto = new OtpRequestDto();
        otpRequestDto.setPhoneNumber("+1234567890");
    }

    @Test
    void testGenerateOtp_Success() throws Exception {
        // Mock service response
        when(otpService.generateOtp(any(OtpRequestDto.class))).thenReturn("123456");

        // Perform the request
        mockMvc.perform(post("/api/v1/otp/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(otpRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("OTP generated successfully: 123456"));
    }

    @Test
    void testGenerateOtp_InvalidRequest() throws Exception {
        // Invalid request (empty phone number)
        otpRequestDto.setPhoneNumber("");

        // Perform the request
        mockMvc.perform(post("/api/v1/otp/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(otpRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testValidateOtp_Success() throws Exception {
        // Mock service response
        when(otpService.validateOtp(anyString(), anyString())).thenReturn(true);

        // Perform the request
        mockMvc.perform(post("/api/v1/otp/validate")
                        .param("phoneNumber", "+1234567890")
                        .param("otpCode", "123456"))
                .andExpect(status().isOk())
                .andExpect(content().string("OTP is valid."));
    }

    @Test
    void testValidateOtp_InvalidOtp() throws Exception {
        // Mock service response
        when(otpService.validateOtp(anyString(), anyString())).thenReturn(false);

        // Perform the request
        mockMvc.perform(post("/api/v1/otp/validate")
                        .param("phoneNumber", "+1234567890")
                        .param("otpCode", "654321"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid or expired OTP."));
    }

    @Test
    void testDeleteExpiredOtps_Success() throws Exception {
        // Perform the request
        mockMvc.perform(delete("/api/v1/otp/cleanup"))
                .andExpect(status().isOk())
                .andExpect(content().string("Expired OTPs deleted successfully."));
    }
}
