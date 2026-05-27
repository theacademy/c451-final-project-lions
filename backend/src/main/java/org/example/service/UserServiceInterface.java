package org.example.service;

import org.example.model.Job;
import org.example.model.User;

import java.util.List;

public interface UserServiceInterface {

    User createNewUser(User user);

    List<User> getAllJobs();

    User findUserById(int id);

    void editUser(User user);

    void editPassword(String password, int id);

    User addSkills(List<String> skills, int id);

    void addJob(Job job, int id);

    void updateJobstatus(int id, String status);

    void deleteUser(int id);

    public User findUserByUsername(String name);

    public String login(User user);
}
