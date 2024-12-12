package com.example.BuzzByte.utils.dto;

import com.example.BuzzByte.login_system.utils.dto.UserDto;

public record  PostLikeDto (
        Long id,
        UserDto user,
        Long postId
){}
