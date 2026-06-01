package org.example.dao;

import org.example.dao.mappers.userMapper;
import org.example.model.Recrutiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class RecruiterDaoImpl implements RecruiterDao{
    @Autowired
    private JdbcTemplate jdbc;
    @Override
    public Recrutiter findRecrutiterById(int id) {
        String sql = """
            SELECT id, email_address, password_hash, first_name, last_name
            FROM users WHERE id = ?
            """;
   //     return jdbc.query(sql, new userMapper(), id)
      //          .stream().findFirst().orElse(null);
        return null;
    }

    @Override
    public void editRecrutiter(String passwordHash, int id) {
        String sql = "UPDATE users SET password_hash = ? WHERE id = ?";
        jdbc.update(sql, passwordHash, id);
    }



}
