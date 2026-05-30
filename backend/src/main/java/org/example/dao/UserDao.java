package org.example.dao;


import org.example.model.User;

import java.util.List;

public interface UserDao {
    User createNewUser(User user);

    List<User> getAllUsers();

    User findUserById(int id);

    User findUserByEmail(String email);

    void editUser(User user);

    void editPassword(String passwordHash, int id);

    void deleteUser(int id);


}
