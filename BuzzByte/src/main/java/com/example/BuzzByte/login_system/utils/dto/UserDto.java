package com.example.BuzzByte.login_system.utils.dto;


import com.example.BuzzByte.model.Role;
import com.example.BuzzByte.utils.dto.TagDto;

import java.util.List;

public record UserDto(
        long id,
        String username,
        String email,
        Role role,
        byte[] profilePicture,
        List<TagDto> tags
) {
}
