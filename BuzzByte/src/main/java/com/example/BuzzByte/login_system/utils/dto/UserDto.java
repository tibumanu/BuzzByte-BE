package com.example.BuzzByte.login_system.utils.dto;


import com.example.BuzzByte.model.Role;

public record UserDto(
        long id,
        String username,
        String email,
        Role role
) {
}
