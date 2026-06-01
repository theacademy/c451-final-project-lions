package org.example.service;

import org.example.dao.UserDao;
import org.example.model.TrackedJob;
import org.example.model.User;
import org.example.model.UserPreference;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User createNewUser(User user) {

        if (user == null
                || isBlank(user.getEmail_address())
                || isBlank(user.getPassword())
                || isBlank(user.getFirst_name())
                || isBlank(user.getLast_name())) {
            return null;
        }

        // Reject duplicate emails
        if (userDao.findUserByEmail(user.getEmail_address()) != null) {
            return null;
        }

        // Hash before persisting
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userDao.createNewUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Override
    public void editUser(User user) {
        userDao.editUser(user);
    }

    @Override
    public TrackedJob updateJobstatus(int id, TrackedJob status) {
        if (status == null ) {
            return null;
        }
        trackedJobDao.updateTrackedJob(status);
        return status;
    }

    @Override
    public TrackedJob addJobstatus( TrackedJob status) {
        if (status == null ) {
            return null;
        }
        status = trackedJobDao.createNewTrackedJob(status);
        return status;
    }
    @Override
    public void editPassword(String password, int id) {
        if (password == null) {
            return;
        }
        // Hash the new password before it hits the DB
        userDao.editPassword(passwordEncoder.encode(password), id);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public String login(User user) {

        if (user == null
                || isBlank(user.getEmail_address())
                || isBlank(user.getPassword())) {
            return null;
        }

        User userCheck = userDao.findUserByEmail(user.getEmail_address());

        if (userCheck != null &&
                userCheck.getPassword() != null &&
                passwordEncoder.matches(user.getPassword(), userCheck.getPassword())) {
            // Token subject = the real user's email
            return jwtUtil.generateToken(userCheck.getEmail_address());
        }
        return null;
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}