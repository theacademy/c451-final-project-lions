package org.example.dao;

import org.example.dao.mappers.userMapper;
import org.example.model.Job;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public User createNewUser(User user) {



        final String INSERT_USER = "INSERT INTO users(email_address,password_hash,first_name,last_name)"+
                "VALUES(?,?,?,?)";
        jdbc.update(INSERT_USER,
                user.getEmail_address(),
                user.getPassword(),
                user.getFirst_name(),
                user.getLast_name());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        user.setId(newId);
        return user;
    }

    @Override
    public List<User> getAllJobs() {

        return List.of();
    }

    @Override
    public User findUserById(int id) {
        try {
            final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
            return jdbc.queryForObject(SELECT_USER_BY_ID, new userMapper(), id);
        } catch (DataAccessException ex){
            return null;
        }


    }

    @Override
    public void editUser(User user) {
        final String INSERT_USER = "UPDATE users SET email_address = ?, password_hash = ?, first_name = ?, last_name = ?"+
                "WHERE id = ?";
        jdbc.update(INSERT_USER,
                user.getEmail_address(),
                user.getPassword(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getId());
    }

    @Override
    public void editPassword(String password, int id) {
        final String INSERT_USER = "UPDATE users SET password_hash = ?"+
                "WHERE id = ?";
        jdbc.update(INSERT_USER,
                password,
                id);
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
        final String DELETE_TRACKED_JOBS = "DELETE FROM tracked_jobs "
                + "WHERE user_id = ?";
        jdbc.update(DELETE_TRACKED_JOBS, id);
        final String DELETE_PEREFRANCE = "DELETE FROM user_preferences "
                + "WHERE user_id = ?";
        jdbc.update(DELETE_PEREFRANCE, id);

        final String DELETE_USER = "DELETE FROM users "
                + "WHERE id = ?";
        jdbc.update(DELETE_USER, id);
    }
}
