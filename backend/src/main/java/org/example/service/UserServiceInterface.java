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

    String editPassword(String password, int id);

    User addSkills(UserPreference skills, int id);

    User updateSkills(UserPreference skills, int id);

    void addJob(Job job, int id);

    TrackedJob updateJobstatus(int id, TrackedJob status);

    TrackedJob addJobstatus( TrackedJob status);


    void deleteUser(int id);

    User findUserByEmail(String email);


    User findUserByUsername(String name);

    String login(User user);
}