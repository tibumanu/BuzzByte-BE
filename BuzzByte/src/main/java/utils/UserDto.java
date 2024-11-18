package utils;


public record UserDto(
        long id,
        String username,
        String email,
        String role
) {
}
