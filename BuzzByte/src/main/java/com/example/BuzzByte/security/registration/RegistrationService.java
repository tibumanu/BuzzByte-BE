package com.example.BuzzByte.security.registration;


import com.example.BuzzByte.model.User;

public interface RegistrationService {
    void addUser(User user);

    boolean enableUser(String token);

    void removeUser(User user);

    boolean updateUser(User user);
}
