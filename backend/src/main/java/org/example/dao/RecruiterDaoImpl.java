package org.example.dao;

import org.example.dao.mappers.RecruiterMapper;
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
            SELECT id, email_address, password_hash, first_name, last_name, company_id
            FROM recruiters WHERE id = ?
            """;
        return jdbc.query(sql, new RecruiterMapper(), id)
                .stream().findFirst().orElse(null);

    }

    @Override
    public void editRecrutiter(String passwordHash, int id) {
        String sql = "UPDATE recruiters SET password_hash = ? WHERE id = ?";
        jdbc.update(sql, passwordHash, id);
    }



}
