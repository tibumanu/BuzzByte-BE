package utils.converter;


import org.springframework.stereotype.Component;
import utils.dto.UserDto;
import model.User;
import utils.converter.Converter;

@Component
public class UserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto createFromEntity(User entity) {
        return new UserDto(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getRole());
        }
    @Override

    public User createFromDto(UserDto dto)
    {
        return User.builder()
                .id(dto.id())
                .username(dto.username())
                .email(dto.email())
                .role(dto.role())
                .build();
        }
}
