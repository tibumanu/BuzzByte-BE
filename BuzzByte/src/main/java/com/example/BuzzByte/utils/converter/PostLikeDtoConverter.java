package com.example.BuzzByte.utils.converter;

import com.example.BuzzByte.login_system.utils.converter.UserDtoConverter;
import com.example.BuzzByte.login_system.utils.dto.UserDto;
import com.example.BuzzByte.model.Post;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.model.likes.PostLike;
import com.example.BuzzByte.service.UserService;
import com.example.BuzzByte.utils.dto.PostLikeDto;
import com.example.BuzzByte.utils.dto.requests.AddPostLikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeDtoConverter implements Converter<PostLike, PostLikeDto>{
    private final UserService userService;
    private final UserDtoConverter userDtoConverter;


    public PostLike createFromAddPostLikeDtoWithPost(AddPostLikeDto dto, Post post) {
        return PostLike.builder()
                .post(post).build();
    }

    @Override
    public PostLike createFromDto(PostLikeDto dto) {
        return PostLike.builder()
                .build();
    }

    @Override
    public PostLikeDto createFromEntity(PostLike entity) {
        User user = userService.getUserById(entity.getUserId());
        UserDto userDto = userDtoConverter.createFromEntity(user);
        return new PostLikeDto(entity.getId(),
                userDto,
                entity.getPost().getId());
    }

}
