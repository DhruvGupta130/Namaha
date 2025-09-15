package com.trulydesignfirm.namaha.serviceImpl;

import com.trulydesignfirm.namaha.dto.OtpRecord;
import com.trulydesignfirm.namaha.service.OtpService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpServiceImpl implements OtpService {

    private static final Map<String, OtpRecord> OTP_STORE = new ConcurrentHashMap<>();
    private static final long OTP_EXPIRY_MINUTES = 15;

    @Override
    public int generateOtp(String mobile) {
        int otp = new Random().nextInt(900000) + 100000;
        OTP_STORE.put(mobile, new OtpRecord(
                Integer.toString(otp),
                Instant.now().plus(OTP_EXPIRY_MINUTES, ChronoUnit.MINUTES)
        ));
        return otp;
    }

    @Override
    public boolean verifyOtp(String mobile, String otp) {
        OtpRecord record = OTP_STORE.get(mobile);
        if (record == null) return false;
        if (record.expiry().isBefore(Instant.now())) {
            OTP_STORE.remove(mobile); // cleanup expired OTP
            return false;
        }
        boolean isValid = record.otp().equals(otp);
        if (isValid) OTP_STORE.remove(mobile); // remove only if valid
        return isValid;
    }
}
