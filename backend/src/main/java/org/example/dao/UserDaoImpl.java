package org.example.dao;

import org.example.model.Job;
import org.example.model.User;

import java.util.List;

public class UserDaoImpl implements UserDao{
    @Override
    public User createNewUser(User user) {
        return null;
    }

    @Override
    public List<User> getAllJobs() {
        return List.of();
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public void editUser(User user) {

    }

    @Override
    public void editPassword(String password, int id) {

    }

    @Override
    public User addSkills(List<String> skills, int id) {
        return null;
    }

    @Override
    public void addJob(Job job, int id) {

    }

    @Override
    public void deleteUser(int id) {

    }
}
