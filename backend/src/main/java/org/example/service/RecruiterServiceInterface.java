package org.example.service;

import org.example.model.TrackedJob;
import org.example.model.User;
import org.example.model.UserPreference;

import java.util.List;

public interface RecruiterServiceInterface {

    User findUserById(int id);

    User editUser(User user);

    void editPassword(String password, int id);





    String login(User user);
}
