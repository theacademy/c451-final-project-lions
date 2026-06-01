package org.example.dao;

import org.example.dao.mappers.userMapper;
import org.example.model.Job;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public User createNewUser(User user) {
        String sql = """
            INSERT INTO users (email_address, password_hash, first_name, last_name)
            VALUES (?, ?, ?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail_address());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirst_name());
            ps.setString(4, user.getLast_name());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            user.setId(keyHolder.getKey().intValue());
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT id, email_address, password_hash, first_name, last_name FROM users";
        return jdbc.query(sql, new userMapper());
    }

    @Override
    public User findUserById(int id) {
        String sql = """
            SELECT id, email_address, password_hash, first_name, last_name
            FROM users WHERE id = ?
            """;
        return jdbc.query(sql, new userMapper(), id)
                .stream().findFirst().orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        String sql = """
            SELECT id, email_address, password_hash, first_name, last_name
            FROM users WHERE email_address = ?
            """;
        return jdbc.query(sql, new userMapper(), email)
                .stream().findFirst().orElse(null);
    }

    @Override
    public void editUser(User user) {
        String sql = """
            UPDATE users
            SET email_address = ?, first_name = ?, last_name = ?
            WHERE id = ?
            """;
        jdbc.update(sql,
                user.getEmail_address(),
                user.getFirst_name(),
                user.getLast_name(),
                user.getId());
    }

    @Override
    public void editPassword(String passwordHash, int id) {
        String sql = "UPDATE users SET password_hash = ? WHERE id = ?";
        jdbc.update(sql, passwordHash, id);
    }

    @Override
    public void deleteUser(int id) {
        jdbc.update("DELETE FROM users WHERE id = ?", id);
    }
}
