package org.example.service;

import org.example.dao.TrackedJobDao;
import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.dao.UserPreferenceDao;
import org.example.model.Job;
import org.example.model.TrackedJob;
import org.example.model.User;
import org.example.model.UserPreference;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private UserPreferenceDao userPreferenceDao;

    @Autowired
    private TrackedJobDao trackedJobDao;

    @Override
    public User createNewUser(User user) {
        if (user == null || user.getEmail_address() == null || user.getPassword() == null) {
            return null;
        }

        if (findUserByEmail(user.getEmail_address()) != null) {
            return null;
        }


        //saveOrUpdatePreferences(id, user.getSkills());
        return userDao.createNewUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT u.id, u.email_address, u.password_hash, u.first_name, u.last_name, COALESCE(up.skills_csv, '') AS skills_csv " +
                "FROM users u LEFT JOIN user_preferences up ON u.id = up.user_id";
        return jdbc.query(sql, new UserRowMapper());
    }

    @Override
    public User findUserById(int id) {

        return userDao.findUserById(id);
    }

    @Override
    public User editUser(User user) {
        if (user == null || user.getId() == 0) {
            return null;
        }

        User existing = findUserById(user.getId());
        if (existing == null) {
            return null;
        }

//        String newEmail = user.getEmail_address();
//        if (newEmail != null && !newEmail.equals(existing.getEmail_address())) {
//            User byEmail = findUserByEmail(newEmail);
//            if (byEmail != null && byEmail.getId() != user.getId()) {
//                return;
//            }
//        }
        userDao.editUser(user);
        return user;

    }

    @Override
    public String editPassword(String password, int id) {
        if (password == null) {
            return null;
        }
        userDao.editPassword(password, id);
        return password;
    }

    @Override
    public User addSkills(UserPreference skills, int id) {
        if (skills == null ) {
            return null;
        }

        User user = findUserById(id);
        if (user == null) {
            return null;
        }

        userPreferenceDao.createNewUserPreference(skills);
        //saveOrUpdatePreferences(id, mergedSkills);
        return user;
    }

    @Override
    public User updateSkills(UserPreference skills, int id) {
        if (skills == null ) {
            return null;
        }

        User user = findUserById(id);
        if (user == null) {
            return null;
        }

        userPreferenceDao.updateUserPreference(skills);
        //saveOrUpdatePreferences(id, mergedSkills);
        return user;
    }

    @Override
    public void addJob(Job job, int id) {
        if (job == null || job.getId() == null || job.getId() == 0) {
            return;
        }

        String sql = "INSERT INTO tracked_jobs (user_id, job_id, status, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW()) " +
                "ON DUPLICATE KEY UPDATE updated_at = NOW()";
        jdbc.update(sql, id, job.getId(), "wishlist");
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
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public User findUserByEmail(String email) {
        if (email == null) {
            return null;
        }

        return userDao.findUserByEmail(email);
    }

    @Override
    public User findUserByUsername(String name) {
        if (name == null) {
            return null;
        }

        String sql = "SELECT u.id, u.email_address, u.password_hash, u.first_name, u.last_name, COALESCE(up.skills_csv, '') AS skills_csv " +
                "FROM users u LEFT JOIN user_preferences up ON u.id = up.user_id WHERE u.email_address = ? OR u.first_name = ? OR u.last_name = ? OR CONCAT(u.first_name, ' ', u.last_name) = ?";
        List<User> users = jdbc.query(sql, new Object[]{name, name, name, name}, new UserRowMapper());
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public String login(User user) {
        if (user == null || user.getEmail_address() == null || user.getPassword() == null) {
            return null;
        }

        User userCheck = findUserByEmail(user.getEmail_address());
        if (userCheck != null && userCheck.getPassword() != null && userCheck.getPassword().equals(user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail_address());
        }
        return null;
    }

    private void saveOrUpdatePreferences(int userId, List<String> skills) {
        String skillsCsv = skills == null || skills.isEmpty() ? "" : String.join(",", skills);
        String sql = "INSERT INTO user_preferences (user_id, skills_csv) VALUES (?, ?) ON DUPLICATE KEY UPDATE skills_csv = VALUES(skills_csv)";
        jdbc.update(sql, userId, skillsCsv);
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail_address(rs.getString("email_address"));
            user.setPassword(rs.getString("password_hash"));
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            String skillsCsv = rs.getString("skills_csv");
            if (skillsCsv == null || skillsCsv.isBlank()) {
                user.setSkills(new ArrayList<>());
            } else {
                user.setSkills(new ArrayList<>(Arrays.asList(skillsCsv.split(","))));
            }
            return user;
        }
    }
}