package org.example.service;

import org.example.model.Job;
import org.example.model.User;

import java.util.List;

public interface UserServiceInterface {

    User createNewUser(User user);

    List<User> getAllUsers();

    User findUserById(int id);

    void editUser(User user);

    void editPassword(String password, int id);

    void deleteUser(int id);

    User findUserByEmail(String email);

    String login(User user);
}