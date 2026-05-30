package org.example.dao.mappers;

import org.example.model.Company;
import org.example.model.Job;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class companiesmapper implements RowMapper<Company> {
    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        company.setId(rs.getLong("id"));
        company.setGreenhouseToken(rs.getString("greenhouse_token"));
        company.setName(rs.getString("name"));
        return company;
    }
}
