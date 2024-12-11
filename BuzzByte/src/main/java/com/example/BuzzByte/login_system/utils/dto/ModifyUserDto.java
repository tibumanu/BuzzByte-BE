package com.example.BuzzByte.login_system.utils.dto;

import com.example.BuzzByte.model.Role;
import com.example.BuzzByte.utils.dto.TagDto;

import java.util.List;

public record ModifyUserDto (
        String username,
        String email,
        List<String> tags
        // todo: should this dto also have a profile picture field?
) {}