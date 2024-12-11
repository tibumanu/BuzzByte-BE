package com.example.BuzzByte.controller;

import com.example.BuzzByte.login_system.utils.converter.UserDtoConverter;
import com.example.BuzzByte.login_system.utils.dto.ChangePasswordDto;
import com.example.BuzzByte.login_system.utils.dto.ModifyUserDto;
import com.example.BuzzByte.login_system.utils.dto.UserDto;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.Result;
import com.example.BuzzByte.utils.dto.TagDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
    @Transactional
    //@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_ARHEO', 'SCOPE_LABWORKER', 'SCOPE_GUEST')")
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
    //@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_ARHEO', 'SCOPE_LABWORKER', 'SCOPE_GUEST')")
    public Result<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        this.userService.changePassword(changePasswordDto);
        return new Result<>(true, HttpStatus.OK.value(), "Password changed.", null);
    }

    @Operation(
            summary = "Modify user endpoint; returns the user",
            description = "Updates a given user's fields based on the username; returns the updated user."
    )
    @PutMapping("/{userId}/modify")
    //@PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_ARHEO', 'SCOPE_LABWORKER', 'SCOPE_GUEST')")
    public Result<UserDto> modifyUser(@PathVariable Long userId, @RequestBody ModifyUserDto modifyUserDto){
        var user = userDtoConverter.createFromModifyUserDto(modifyUserDto);
        user.setId(userId);
        UserDto userDto = userDtoConverter.createFromEntity(userService.updateUser(user));
        return new Result<>(true, HttpStatus.OK.value(), "User updated successfully.", userDto);
    }

    @PostMapping("/{userId}/tags")
    public Result<UserDto> addTagsToUser(@PathVariable Long userId, @RequestBody List<String> tags) {
        var updatedUser = userService.addTagsToUser(userId, tags);
        UserDto updatedUserDto = userDtoConverter.createFromEntity(updatedUser);
        return new Result<>(true, HttpStatus.OK.value(), "Tags added successfully.", updatedUserDto);
    }

}
