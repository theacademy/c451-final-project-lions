package org.example.dao;

import org.example.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public class CompanyDaoDbImpl implements CompanyDaoDb {

    @Autowired
    private JdbcTemplate jdbc;
    @Override
    public List<Company> findAll() {
        String sql = "SELECT id, greenhouse_token, name, last_synced_at FROM companies";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Company.class));
    }

    @Override
    public void updateLastSyncedAt(Long companyId, Timestamp syncedAt) {
        String sql = "UPDATE companies SET last_synced_at = ? WHERE id = ?";
        jdbc.update(sql, syncedAt, companyId);
    }
}
