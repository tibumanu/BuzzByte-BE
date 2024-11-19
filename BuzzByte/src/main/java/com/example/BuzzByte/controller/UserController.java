package com.example.BuzzByte.controller;

import com.example.BuzzByte.login_system.utils.converter.UserDtoConverter;
import com.example.BuzzByte.login_system.utils.dto.ChangePasswordDto;
import com.example.BuzzByte.login_system.utils.dto.UserDto;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.endpoint.base-url}/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Management")
public class UserController {
    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    @Operation(
            summary = "Get user endpoint",
            description = "Retrieves details about the current user, based on the JWT token."
    )
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_ARHEO', 'SCOPE_LABWORKER', 'SCOPE_GUEST')")
    public Result<UserDto> getUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userDtoConverter.createFromEntity(this.userService.getUserByUsername(username));
        return new Result<>(true, HttpStatus.OK.value(), "Details about user served.", user);
    }

    @Operation(
            summary = "Change user password endpoint; returns message",
            description = "Updates a given user's password based on the username; requires old password for security and new password."
    )
    @PutMapping("/password")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_ARHEO', 'SCOPE_LABWORKER', 'SCOPE_GUEST')")
    public Result<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        this.userService.changePassword(changePasswordDto);
        return new Result<>(true, HttpStatus.OK.value(), "Password changed.");
    }
}
