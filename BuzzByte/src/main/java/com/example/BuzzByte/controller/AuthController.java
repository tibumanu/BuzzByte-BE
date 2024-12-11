package com.example.BuzzByte.controller;

import com.example.BuzzByte.login_system.utils.converter.RegistrationUserDtoConverter;
import com.example.BuzzByte.login_system.utils.converter.UserDtoConverter;
import com.example.BuzzByte.login_system.utils.dto.LoginRequest;
import com.example.BuzzByte.login_system.utils.dto.PasswordResetDto;
import com.example.BuzzByte.login_system.utils.dto.RegistrationUserDto;
import com.example.BuzzByte.login_system.utils.dto.UserDto;
import com.example.BuzzByte.security.auth.AuthenticationService;
import com.example.BuzzByte.security.recovery.RecoveryService;
import com.example.BuzzByte.security.registration.RegistrationService;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.endpoint.base-url}/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final RegistrationUserDtoConverter registrationUserDtoConverter;
    private final RegistrationService registrationService;
    private final RecoveryService recoveryService;
    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    @Operation(
            description = "Login endpoint.",
            summary = "Login endpoint. Generates the JWT needed for further operations."
    )
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest loginRequest) {

        var token = this.authenticationService.authenticate(loginRequest);
        return new Result<>(true, HttpStatus.OK.value(), "Login successful.", token);
    }

    @Operation(
            description = "REGISTER",
            summary = "Registration endpoint. After registering it sends an email with confirmation token."
    )
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegistrationUserDto registrationUserDto) {
        try {
            this.registrationService.addUser(this.registrationUserDtoConverter.createFromDto(registrationUserDto));
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

//        this.registrationService.addUser(this.registrationUserDtoConverter.createFromDto(registrationUserDto));
        return new Result<>(true, HttpStatus.CREATED.value(), "User created successfully", null);
    }
    @Operation(
            description = "ENABLE USER",
            summary = "To enable the user the token received in the email must be passed to the endpoint."
    )
    @PutMapping("/enable/{token}")
    public Result<?> enableUser(@PathVariable String token) {
        this.registrationService.enableUser(token);
        return new Result<>(true, HttpStatus.OK.value(), "User enabled successfully.", null);
    }

    @Operation(
            description = "RESET PASSWORD REQUEST",
            summary = "Forgot password. It will send an email with a token/link for password reset."
    )
    @PostMapping("/reset-password")
    public Result<?> resetPasswordRequest(@RequestBody String email) {
        this.recoveryService.recoveryRequest(email);
        return new Result<>(true, HttpStatus.OK.value(), "Email with token sent. Please check your email.", null);
    }
    @Operation(
            description = "RESET PASSWORD",
            summary = "Token from the email must be passed as a path variable to reset the password. Random password is generated"
    )
    @PostMapping("/reset-password-email/{token}")
    public Result<?> sendPassword(@PathVariable String token) {
        this.recoveryService.resetPassword(token);
        return new Result<>(true, HttpStatus.OK.value(), "Password sent to email.", null);
    }

    @Operation(
            description = "RESET PASSWORD WITH LINK",
            summary = "Input new password along with the token provided in email."
    )
    @PutMapping("/reset-password")
    public Result<?> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        this.recoveryService.resetPassword(passwordResetDto);
        return new Result<>(true, HttpStatus.OK.value(), "Password reset successfully", null);
    }

    @PostMapping("/{username}/tags")
    public Result<UserDto> addTagsToUser(@PathVariable String username, @RequestBody List<String> tags) {
        var user = userService.getUserByUsername(username);
        var updatedUser = userService.addTagsToUser(user.getId(), tags);
        UserDto updatedUserDto = userDtoConverter.createFromEntity(updatedUser);
        return new Result<>(true, HttpStatus.OK.value(), "Tags added successfully.", updatedUserDto);
    }

}
