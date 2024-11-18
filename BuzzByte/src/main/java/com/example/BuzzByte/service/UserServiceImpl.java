package com.example.BuzzByte.service;

import com.example.BuzzByte.model.User;
import com.example.BuzzByte.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;


    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id " + id + " not found")
        );
    }
}
