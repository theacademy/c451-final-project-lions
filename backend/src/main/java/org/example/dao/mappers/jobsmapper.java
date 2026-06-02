package org.example.dao.mappers;

import org.example.model.Job;
import org.example.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class jobsmapper  implements RowMapper<Job> {
    @Override
    public Job mapRow(ResultSet rs, int rowNum)  throws SQLException {
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
         job.setPostedAt(rs.getTimestamp("posted_at"));
        job.setLastSeenAt(rs.getTimestamp("last_seen_at"));
        job.setCreatedAt(rs.getTimestamp("created_at"));
        job.setActive(rs.getBoolean("is_active"));
//<<<<<<< HEAD
       // job.setSkillsCsv(rs.getString("skills_csv"));
//=======
        job.setSkillsCsv(rs.getString("skills_Csv"));
//>>>>>>> 9fc7b4df93cd8c29886b1875513962ab1e71c068
        return job;
    }
}
