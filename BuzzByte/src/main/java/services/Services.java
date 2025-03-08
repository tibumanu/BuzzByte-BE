package services;

import repository.*;
import domain.User.*;
import java.util.List;


public class Services {
    IRepository repository;

    public Services(IRepository repository) {
        this.repository = repository;
    }

    public void addUser(String username, String email, String hashedPassword, String avatarUrl, String role) {
        IUser user = new User(username, email, hashedPassword, avatarUrl, role);
        this.repository.addUser(user);
    }

    public List<IUser> getUsers() {
        return this.repository.getAllUsers();
    }

    public IUser getUserById(Integer id) {
        return this.repository.getUserById(id);
    }

    public void updateUser(Integer id, String username, String email, String hashedPassword, String avatarUrl, String role) {
        IUser user = new User(id, username, email, hashedPassword, avatarUrl, role);
        this.repository.updateUser(user);
    }

    public void deleteUser(Integer id) {
        this.repository.deleteUser(id);
    }
}
