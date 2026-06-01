package org.example.service;

import org.example.model.Job;
import org.example.model.User;
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

    @Override
    public User createNewUser(User user) {
        if (user == null || user.getEmail_address() == null || user.getPassword() == null) {
            return null;
        }

        if (findUserByEmail(user.getEmail_address()) != null) {
            return null;
        }

        String sql = "INSERT INTO users (email_address, password_hash, first_name, last_name) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail_address());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirst_name());
            ps.setString(4, user.getLast_name());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            return null;
        }

        int id = key.intValue();
        user.setId(id);
        if (user.getSkills() == null) {
            user.setSkills(new ArrayList<>());
        }
        saveOrUpdatePreferences(id, user.getSkills());
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT u.id, u.email_address, u.password_hash, u.first_name, u.last_name, COALESCE(up.skills_csv, '') AS skills_csv " +
                "FROM users u LEFT JOIN user_preferences up ON u.id = up.user_id";
        return jdbc.query(sql, new UserRowMapper());
    }

    @Override
    public User findUserById(int id) {
        String sql = "SELECT u.id, u.email_address, u.password_hash, u.first_name, u.last_name, COALESCE(up.skills_csv, '') AS skills_csv " +
                "FROM users u LEFT JOIN user_preferences up ON u.id = up.user_id WHERE u.id = ?";
        List<User> users = jdbc.query(sql, new Object[]{id}, new UserRowMapper());
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void editUser(User user) {
        if (user == null || user.getId() == 0) {
            return;
        }

        User existing = findUserById(user.getId());
        if (existing == null) {
            return;
        }

        String newEmail = user.getEmail_address();
        if (newEmail != null && !newEmail.equals(existing.getEmail_address())) {
            User byEmail = findUserByEmail(newEmail);
            if (byEmail != null && byEmail.getId() != user.getId()) {
                return;
            }
        }

        String sql = "UPDATE users SET email_address = ?, first_name = ?, last_name = ?, password_hash = ? WHERE id = ?";
        jdbc.update(sql,
                user.getEmail_address() != null ? user.getEmail_address() : existing.getEmail_address(),
                user.getFirst_name() != null ? user.getFirst_name() : existing.getFirst_name(),
                user.getLast_name() != null ? user.getLast_name() : existing.getLast_name(),
                user.getPassword() != null ? user.getPassword() : existing.getPassword(),
                user.getId());

        if (user.getSkills() != null) {
            saveOrUpdatePreferences(user.getId(), user.getSkills());
        }
    }

    @Override
    public void editPassword(String password, int id) {
        if (password == null) {
            return;
        }
        String sql = "UPDATE users SET password_hash = ? WHERE id = ?";
        jdbc.update(sql, password, id);
    }

    @Override
    public User addSkills(List<String> skills, int id) {
        if (skills == null || skills.isEmpty()) {
            return findUserById(id);
        }

        User user = findUserById(id);
        if (user == null) {
            return null;
        }

        List<String> mergedSkills = new ArrayList<>(user.getSkills() != null ? user.getSkills() : Collections.emptyList());
        mergedSkills.addAll(skills);
        saveOrUpdatePreferences(id, mergedSkills);
        user.setSkills(mergedSkills);
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
    public void updateJobstatus(int id, String status) {
        if (status == null || status.isEmpty()) {
            return;
        }

        String sql = "UPDATE tracked_jobs SET status = ?, updated_at = NOW() WHERE user_id = ?";
        jdbc.update(sql, status, id);
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbc.update(sql, id);
    }

    @Override
    public User findUserByEmail(String email) {
        if (email == null) {
            return null;
        }

        String sql = "SELECT u.id, u.email_address, u.password_hash, u.first_name, u.last_name, COALESCE(up.skills_csv, '') AS skills_csv " +
                "FROM users u LEFT JOIN user_preferences up ON u.id = up.user_id WHERE u.email_address = ?";
        List<User> users = jdbc.query(sql, new Object[]{email}, new UserRowMapper());
        return users.isEmpty() ? null : users.get(0);
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