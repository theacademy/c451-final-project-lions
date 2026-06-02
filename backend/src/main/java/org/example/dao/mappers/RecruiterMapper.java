package org.example.dao.mappers;

import org.example.model.Recrutiter;
import org.example.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecruiterMapper implements RowMapper<Recrutiter> {
    @Override
    public Recrutiter mapRow(ResultSet rs, int rowNum)  throws SQLException {
        Recrutiter user = new Recrutiter();
        user.setId( rs.getInt("id"));
        user.setPassword(rs.getString("password_hash"));
        user.setFirst_name(rs.getString("first_name"));
        user.setLast_name(rs.getString("last_name"));
        user.setEmail_address(rs.getString("email_address"));
        user.setCompanyId(rs.getLong("company_id"));
        return user;
    }
}
