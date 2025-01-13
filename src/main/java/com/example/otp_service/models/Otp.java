package com.example.otp_service.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "otps")
    public class Otp {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        private String phoneNumber;

        @Column(nullable = false)
        private String otpCode;

        @Column(nullable = false)
        private LocalDateTime issuedAt;

        @Column(nullable = false)
        private LocalDateTime expiresAt;

        @Column
        private Boolean validated = false;

        public Otp() {
        }

        public Otp(String phoneNumber, String otpCode, LocalDateTime issuedAt, LocalDateTime expiresAt) {
            this.phoneNumber = phoneNumber;
            this.otpCode = otpCode;
            this.issuedAt = issuedAt;
            this.expiresAt = expiresAt;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getOtpCode() {
            return otpCode;
        }

        public void setOtpCode(String otpCode) {
            this.otpCode = otpCode;
        }

        public LocalDateTime getIssuedAt() {
            return issuedAt;
        }

        public void setIssuedAt(LocalDateTime issuedAt) {
            this.issuedAt = issuedAt;
        }

        public LocalDateTime getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
        }

        public Boolean getValidated() {
            return validated;
        }

        public void setValidated(Boolean validated) {
            this.validated = validated;
        }
    }