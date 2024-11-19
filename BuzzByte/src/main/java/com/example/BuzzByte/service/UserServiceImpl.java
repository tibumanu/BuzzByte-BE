package com.example.BuzzByte.service;

import com.example.BuzzByte.login_system.utils.dto.ChangePasswordDto;
import com.example.BuzzByte.login_system.utils.dto.EnableUserDto;
import com.example.BuzzByte.login_system.utils.exception.PasswordMissmatchException;
import com.example.BuzzByte.login_system.utils.exception.UserServiceException;
import com.example.BuzzByte.utils.validation.GenericValidator;
import com.example.BuzzByte.model.Role;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GenericValidator<User> validator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User addUser(User user) {
        try {
            validator.validate(user);
            return this.userRepository.save(user);
        } catch (ConstraintViolationException ex){
            throw new UserServiceException(ex.getMessage());
        }
    }
    @Override
    @Transactional
    public User updateUser(User user) {
        var updatedUser = this.userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with username %s, does not exist.", user.getUsername())
                ));
        updatedUser.setEmail(user.getEmail());
        try {
            validator.validate(user);
            return this.userRepository.save(updatedUser);
        } catch (ConstraintViolationException ex){
            throw new UserServiceException(ex.getMessage());
        }
    }

    @Override
    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return this.userRepository.findAllByRole(role.name());
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(()->new EntityNotFoundException(String.format("User with username: %s, not found", username)));
    }

    @Override
    public User getUserById(long id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(
                        ()->new EntityNotFoundException(String.format("User with id: %d, not found", id))
                );
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto) {
        var user = this.getUserByUsername(changePasswordDto.username());
        if (passwordEncoder.matches(changePasswordDto.oldPassword(), user.getHashedPassword())){
            user.setHashedPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
            this.userRepository.save(user);
        } else {
            throw new PasswordMissmatchException("Current password is not correct.");
        }
    }



    @Override
    @Transactional
    public void enableUser(EnableUserDto enableUserDto) {
        var user = this.getUserByUsername(enableUserDto.username());
        user.setEnabled(enableUserDto.isEnabled());
        this.userRepository.save(user);
    }
}
