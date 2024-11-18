package utils.dto;


public record UserDto(
        long id,
        String username,
        String email,
        String role
) {
}
