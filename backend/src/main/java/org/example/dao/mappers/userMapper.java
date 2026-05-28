package org.example.dao.mappers;

import org.example.model.User;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class userMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum)  throws SQLException {
        User user = new User();
        // user.setUserid(rs.getDouble("id"));
        // user.setPassword_hash(rs.getString("password_hash"))
        // user.setFname(rs.getString("first_name"))
        // user.setLname(rs.getString("last_name"))

        return user;
    }
}

