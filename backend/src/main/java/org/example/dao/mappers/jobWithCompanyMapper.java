package org.example.dao.mappers;

import org.example.model.Job;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps a jobs row that has been joined with companies (aliased company_name).
 * Used by the job board and recommended queries that need the company name for display.
 */
public class jobWithCompanyMapper implements RowMapper<Job> {
    @Override
    public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
        Job job = new Job();
        job.setId(rs.getLong("id"));
        job.setGreenhouseJobId(rs.getLong("greenhouse_job_id"));
        job.setCompanyId(rs.getLong("company_id"));
        job.setTitle(rs.getString("title"));
        job.setLocation(rs.getString("location"));
        job.setDescriptionHtml(rs.getString("description_html"));
        job.setDescriptionText(rs.getString("description_text"));
        job.setAbsoluteUrl(rs.getString("absolute_url"));
        job.setSeniorityLevel(rs.getString("seniority_level"));
        job.setSkillsCsv(rs.getString("skills_csv"));
        job.setPostedAt(rs.getTimestamp("posted_at"));
        job.setActive(rs.getBoolean("is_active"));
        job.setLastSeenAt(rs.getTimestamp("last_seen_at"));
        job.setCreatedAt(rs.getTimestamp("created_at"));
        job.setCompanyName(rs.getString("company_name"));
        return job;
    }
}
