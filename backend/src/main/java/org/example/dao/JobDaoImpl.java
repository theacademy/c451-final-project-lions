package org.example.dao;

import org.example.dao.mappers.jobsmapper;
import org.example.dao.mappers.jobWithCompanyMapper;
import org.example.model.Job;
import org.example.model.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
            final String SELECT_JOBS_BY_ID = "SELECT * FROM jobs WHERE company_id = ? " +
                    "LIMIT ? OFFSET ?";
            return jdbc.query(SELECT_JOBS_BY_ID, new jobsmapper(), company_id, pageSize, offset);
        } catch(DataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Job> getAllJobs(int page) {
        int pageSize = 20;
        int offset = page * pageSize;
        try {
            final String SELECT_JOBS_BY_ID = "SELECT * FROM jobs " +
                    "LIMIT ? OFFSET ?";
            return jdbc.query(SELECT_JOBS_BY_ID, new jobsmapper(), pageSize, offset);
        } catch(DataAccessException ex) {
            return null;
        }

    }

    @Override
    public Job findJobById(int id) {

        try {
            final String SELECT_JOBS_BY_ID =
                    "SELECT j.*, c.name AS company_name " +
                    "FROM jobs j JOIN companies c ON j.company_id = c.id " +
                    "WHERE j.id = ?";
            return jdbc.queryForObject(SELECT_JOBS_BY_ID, new jobWithCompanyMapper(), id);
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

            List<Job> jobs = jdbc.query(
                    sql.toString(),
                    new jobsmapper(),
                    params.toArray()
            );

            if (jobs == null) {
                return null;
            }

            Set<String> searchSkills = normalizeSkills(search.getSkills());
            for (Job job : jobs) {
                Set<String> jobSkills = normalizeSkills(job.getSkillsCsv());
                int matchPercent = calculateMatchPercent(jobSkills, searchSkills);
                job.setMatchPercent(matchPercent);
            }

            return jobs.stream()
                    .sorted((a, b) -> Integer.compare(b.getMatchPercent(), a.getMatchPercent()))
                    .collect(Collectors.toList());

        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Set<String> normalizeSkills(List<String> skills) {
        if (skills == null) {
            return new HashSet<>();
        }
        return skills.stream()
                .filter(s -> s != null && !s.isBlank())
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private Set<String> normalizeSkills(String skillsCsv) {
        if (skillsCsv == null || skillsCsv.isBlank()) {
            return new HashSet<>();
        }
        return Arrays.stream(skillsCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private int calculateMatchPercent(Set<String> jobSkills, Set<String> searchSkills) {
        if (jobSkills.isEmpty() || searchSkills.isEmpty()) {
            return 0;
        }

        Set<String> intersection = new HashSet<>(jobSkills);
        intersection.retainAll(searchSkills);
        if (intersection.isEmpty()) {
            return 0;
        }

        return (int) Math.round(100.0 * intersection.size() / jobSkills.size());
    }

    @Override
    public List<Job> findActiveJobs(String role, String location, String seniority, int page) {
        int pageSize = 20;
        int offset = page * pageSize;
        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT j.*, c.name AS company_name " +
                    "FROM jobs j JOIN companies c ON j.company_id = c.id " +
                    "WHERE j.is_active = TRUE "
            );
            List<Object> params = new ArrayList<>();

            if (role != null && !role.isBlank()) {
                sql.append("AND j.title LIKE ? ");
                params.add("%" + role.trim() + "%");
            }
            if (location != null && !location.isBlank()) {
                sql.append("AND j.location LIKE ? ");
                params.add("%" + location.trim() + "%");
            }
            if (seniority != null && !seniority.isBlank()) {
                sql.append("AND j.seniority_level = ? ");
                params.add(seniority);
            }

            sql.append("ORDER BY j.posted_at DESC LIMIT ? OFFSET ?");
            params.add(pageSize);
            params.add(offset);

            return jdbc.query(sql.toString(), new jobWithCompanyMapper(), params.toArray());
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteJob(int id) {

    }
}
