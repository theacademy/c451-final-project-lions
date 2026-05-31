package org.example.dao.mappers;

import org.example.model.TrackedJob;
import org.example.model.UserPreference;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class trackedJobsMapper  implements RowMapper<TrackedJob> {

    @Override
    public TrackedJob mapRow(ResultSet rs, int rowNum) throws SQLException {
        TrackedJob trackedJob = new TrackedJob();
        trackedJob.setId( rs.getInt("id"));
        trackedJob.setUser_id(rs.getInt("user_id"));
        trackedJob.setJob_id(rs.getInt("job_id"));
        trackedJob.setStatus(rs.getString("status"));
        trackedJob.setNotes(rs.getString("notes"));
        trackedJob.setApplied_at(rs.getTimestamp("applied_at"));
        return trackedJob;
    }
}
