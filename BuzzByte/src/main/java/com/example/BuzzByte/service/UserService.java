package com.example.BuzzByte.service;


import com.example.BuzzByte.login_system.utils.dto.ChangePasswordDto;
import com.example.BuzzByte.login_system.utils.dto.EnableUserDto;
import com.example.BuzzByte.model.Role;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.utils.dto.PostDto;
import com.example.BuzzByte.utils.dto.TagDto;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(long id);
    List<User> getUsersByRole(Role role);
    List<User> getUsers();
    User getUserByUsername(String username);
    User getUserById(long id);
    void changePassword(ChangePasswordDto changePasswordDto);
    void enableUser(EnableUserDto enableUserDto);
    User addTagsToUser(Long userId, List<String> newTags);
    User addBookmarkToUser(Long userId, Long postId);
    User removeBookmarkFromUser(Long userId, Long postId);
    boolean isPostBookmarkedByUser(Long userId, Long postId);
    List<Long> getBookmarksIdsOfUser(Long userId);
    // List<PostDto> getBookmarksPostsDtosOfUser(Long userId);
    // ^ not able to implement this method because of the cyclic dependency between PostDtoConverter and UserService
}
