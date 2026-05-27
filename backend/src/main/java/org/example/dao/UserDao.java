package org.example.dao;

import org.server.model.Job;
import org.server.model.User;

import java.util.List;

public interface UserDao {
    User createNewUser(User user);

    List<User> getAllJobs();

    User findUserById(int id);

    void editUser(User user);

    void editPassword(String password, int id);

    User addSkills(List<String> skills, int id);

    void addJob(Job job, int id);

    void deleteUser(int id);


}
