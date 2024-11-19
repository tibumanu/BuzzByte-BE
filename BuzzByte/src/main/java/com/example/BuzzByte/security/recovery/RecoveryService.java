package com.example.BuzzByte.security.recovery;

import com.example.BuzzByte.login_system.utils.dto.PasswordResetDto;

public interface RecoveryService {
    void recoveryRequest(String email);
    void resetPassword(String token);
    void resetPassword(PasswordResetDto passwordResetDto);

}
