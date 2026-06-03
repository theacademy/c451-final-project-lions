package org.example.service;

import org.example.model.Job;
import org.example.model.TrackedJob;
import org.example.model.User;
import org.example.model.UserPreference;

import java.util.List;

public interface UserServiceInterface {

    User createNewUser(User user);

    List<User> getAllUsers();

    User findUserById(int id);

    User editUser(User user);

    void updateName(String firstName, String lastName, int id);

    void editPassword(String password, int id);

    void deleteUser(int id);

    User findUserByEmail(String email);

    String login(User user);

    TrackedJob updateJobstatus(int id, TrackedJob status);

     TrackedJob addJobstatus( TrackedJob status);


}