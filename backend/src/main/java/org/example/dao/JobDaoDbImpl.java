package org.example.dao;

import org.example.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class JobDaoDbImpl implements JobDaoDb {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void save(Job job) {
        // Inserts a new job, or updates an existing one matched by greenhouse_job_id
        String sql = """
        INSERT INTO jobs (
            greenhouse_job_id, company_id, title, location,
            description_html, description_text, absolute_url,
            seniority_level, skills_csv, posted_at,
            is_active, last_seen_at
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE, NOW())
        ON DUPLICATE KEY UPDATE
            title = VALUES(title),
            location = VALUES(location),
            description_html = VALUES(description_html),
            description_text = VALUES(description_text),
            absolute_url = VALUES(absolute_url),
            seniority_level = VALUES(seniority_level),
            skills_csv = VALUES(skills_csv),
            posted_at = VALUES(posted_at),
            is_active = TRUE,
            last_seen_at = NOW()
        """;

        jdbc.update(sql,
                job.getGreenhouseJobId(),
                job.getCompanyId(),
                job.getTitle(),
                job.getLocation(),
                job.getDescriptionHtml(),
                job.getDescriptionText(),
                job.getAbsoluteUrl(),
                job.getSeniorityLevel(),
                job.getSkillsCsv(),
                job.getPostedAt()
        );
    }

    @Override
    public int deactivateStaleJobs(Timestamp syncStart) {
        String sql = "UPDATE jobs SET is_active = FALSE WHERE last_seen_at < ? AND is_active = TRUE";
        return jdbc.update(sql, syncStart);
    }
}
