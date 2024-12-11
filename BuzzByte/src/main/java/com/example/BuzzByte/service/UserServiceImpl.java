package com.example.BuzzByte.service;

import com.example.BuzzByte.login_system.utils.dto.ChangePasswordDto;
import com.example.BuzzByte.login_system.utils.dto.EnableUserDto;
import com.example.BuzzByte.login_system.utils.exception.PasswordMissmatchException;
import com.example.BuzzByte.login_system.utils.exception.UserServiceException;
import com.example.BuzzByte.model.Tag;
import com.example.BuzzByte.repository.TagRepository;
import com.example.BuzzByte.utils.converter.TagDtoConverter;
import com.example.BuzzByte.utils.dto.TagDto;
import com.example.BuzzByte.utils.validation.GenericValidator;
import com.example.BuzzByte.model.Role;
import com.example.BuzzByte.model.User;
import com.example.BuzzByte.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GenericValidator<User> validator;
    private final PasswordEncoder passwordEncoder;
    private final TagRepository tagRepository;

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
        var updatedUser = this.userRepository.findById(user.getId()).orElseThrow(
            () -> new EntityNotFoundException(String.format("User with id %d does not exist.", user.getId())
        ));
        updatedUser.setUsername(user.getUsername());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setProfilePicture(user.getProfilePicture());
        updatedUser.getTags().clear();
        updatedUser.getTags().addAll(user.getTags());
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
    @Transactional
    public User getUserByUsername(String username) {
        /*var user = this.userRepository
                .findByUsername(username)
                .orElseThrow(()->new EntityNotFoundException(String.format("User with username: %s, not found", username)));
        Hibernate.initialize(user.getTags());
        return user;*/
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException(String.format("User with username: %s, not found", username));
        }
        Hibernate.initialize(user.get().getTags());
        return user.get();

    }

    @Override
    @Transactional
    public User getUserById(long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException(String.format("User with id: %d, not found", id));
        }
        Hibernate.initialize(user.get().getTags());
        return user.get();
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

    @Override
    @Transactional
    public User addTagsToUser(Long userId, List<String> newTags) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        var tags = newTags.stream()
                .map(tagName ->
                        tagRepository.findByName(tagName)
                                .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with name %s not found.", tagName)))
                )
                .toList();
        user.getTags().clear();
        user.getTags().addAll(tags);
        return userRepository.save(user);
    }
}
