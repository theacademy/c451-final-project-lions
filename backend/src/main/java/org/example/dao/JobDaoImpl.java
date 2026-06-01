package org.example.dao;

import org.example.dao.mappers.jobsmapper;
import org.example.model.Job;
import org.example.model.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class JobDaoImpl  implements JobDao{

    @Autowired
    JdbcTemplate jdbc;
    @Override
    public Job createNewJob(Job job) {
        return null;
    }

    @Override
    public List<Job> getAllJobs(Long company_id, int page) {
        int pageSize = 20;
        int offset = page * pageSize;
        try {
            final String SELECT_JOBS_BY_ID = "SELECT * FROM jobs WHERE company_id = ?" +
                    "LIMIT ? OFFSET ?";
            return jdbc.query(SELECT_JOBS_BY_ID, new jobsmapper(), company_id, pageSize, offset);
        } catch(DataAccessException ex) {
            return null;
        }

    }

    @Override
    public Job findJobById(int id) {

        try {
            final String SELECT_JOBS_BY_ID = "SELECT * FROM jobs WHERE id = ?";
            return jdbc.queryForObject(SELECT_JOBS_BY_ID, new jobsmapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Job> searchJob(Search search) {
        int pageSize = 20;

        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT * FROM jobs WHERE company_id = ? "
            );

            List<Object> params = new ArrayList<>();
            params.add(search.getCompanyId());

            if (search.getLocation() != null && !search.getLocation().isBlank()) {
                sql.append("AND location = ? ");
                params.add(search.getLocation());
            }

            if (search.getSeniority_level() != null && !search.getSeniority_level().isBlank()) {
                sql.append("AND seniority_level = ? ");
                params.add(search.getSeniority_level());
            }

            sql.append("LIMIT ?");
            params.add(pageSize);

            return jdbc.query(
                    sql.toString(),
                    new jobsmapper(),
                    params.toArray()
            );

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void deleteJob(int id) {

    }
}
