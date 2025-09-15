package com.trulydesignfirm.namaha.dto;

import java.time.Instant;

public record OtpRecord(String otp, Instant expiry) {}
