package com.example.BuzzByte.service;


import com.example.BuzzByte.login_system.utils.dto.ChangePasswordDto;
import com.example.BuzzByte.login_system.utils.dto.EnableUserDto;
import com.example.BuzzByte.model.Role;
import com.example.BuzzByte.model.User;

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
}
