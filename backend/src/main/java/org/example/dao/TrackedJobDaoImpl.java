package org.example.dao;

import org.example.dao.mappers.trackedJobsMapper;
import org.example.model.TrackedJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrackedJobDaoImpl implements TrackedJobDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public TrackedJob createNewTrackedJob(TrackedJob trackedJob) {
        final String INSERT_TRACKED = "INSERT INTO tracked_jobs(  user_id, job_id, status, notes, applied_at) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_TRACKED,
                trackedJob.getUser_id(),
                trackedJob.getJob_id(),
                trackedJob.getStatus(),
                trackedJob.getNotes(),
                trackedJob.getApplied_at());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        trackedJob.setId(newId);
        return trackedJob;
    }

    @Override
    public TrackedJob findTrackedJobById(int id) {
        try {
            final String SELECT_TRACKED_BY_ID = "SELECT * FROM tracked_jobs WHERE id = ?";
            return jdbc.queryForObject(SELECT_TRACKED_BY_ID, new trackedJobsMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<TrackedJob> findTrackedJobByjobId(Long id) {
        try {
            final String SELECT_TRACKED_BY_ID = "SELECT * FROM tracked_jobs WHERE job_id = ?";
            return jdbc.query(SELECT_TRACKED_BY_ID, new trackedJobsMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateTrackedJob(TrackedJob trackedJob) {
        final String UPDATE_TRACKED = "UPDATE tracked_jobs SET user_id = ?, job_id = ?, status = ?, notes = ?, applied_at = ?  "
                + "WHERE id = ?";
        jdbc.update(UPDATE_TRACKED,
                trackedJob.getUser_id(),
                trackedJob.getJob_id(),
                trackedJob.getStatus(),
                trackedJob.getNotes(),
                trackedJob.getApplied_at(),
                trackedJob.getId());
    }

    @Override
    public void deleteTrackedJob(int id) {
        final String DELETE_TRACKED = "DELETE FROM tracked_jobs "
                + "WHERE id = ?";
        jdbc.update(DELETE_TRACKED, id);
    }
}
