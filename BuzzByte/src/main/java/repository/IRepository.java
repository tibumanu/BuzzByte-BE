package repository;

import domain.User.IUser;
import java.util.List;


public interface IRepository {
    void addUser(IUser user);

    List<IUser> getAllUsers();

    IUser getUserById(Integer id);

    void updateUser(IUser user);

    void deleteUser(Integer id);
}
