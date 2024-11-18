package com.example.BuzzByte.service;

import com.example.BuzzByte.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getUserById(long id);
}
