package com.example.BuzzByte.login_system.utils.dto;


import com.example.BuzzByte.model.Role;

public record UserAdminDto(
        long id,
        String username,
        String email,
        Role role,
        boolean isEnabled
) {
}
