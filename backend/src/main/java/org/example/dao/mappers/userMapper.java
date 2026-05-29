package org.example.dao.mappers;

import org.example.model.User;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class userMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum)  throws SQLException {
        User user = new User();
         user.setId( rs.getInt("id"));
         user.setPassword(rs.getString("password_hash"));
         user.setFirst_name(rs.getString("first_name"));
         user.setLast_name(rs.getString("last_name"));
         user.setEmail_address(rs.getString("email_address"));
        return user;
    }
}

