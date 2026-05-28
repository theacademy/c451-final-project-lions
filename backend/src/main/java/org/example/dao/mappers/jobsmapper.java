package org.example.dao.mappers;

import org.example.model.Job;
import org.example.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class jobsmapper  implements RowMapper<Job> {
    @Override
    public Job mapRow(ResultSet rs, int rowNum)  throws SQLException {
        Job job = new Job();
        // job.setjobid(rs.getInt("id"));
        // job.setgreenhouse_job_id(rs.getInt("greenhouse_job_id"));
        // job.setcompany_id(rs.getInt("company_id"));
        // job.settitle(rs.getString("title"))
        // job.setlocation(rs.getString("location"))
        // job.setdescription_html(rs.getString("description_html"))
        // job.setdescription_text(rs.getString("description_text"))
        // job.setabsolute_url(rs.getString("absolute_url"))
        // job.setseniority_level(rs.getString("seniority_level"))



        return job;
    }
}

//skills_csv VARCHAR(500),
//posted_at TIMESTAMP NULL,
//is_active BOOLEAN NOT NULL DEFAULT TRUE,
//last_seen_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
//created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
//CONSTRAINT fk_jobs_company FOREIGN KEY (company_id) REFERENCES companies(id)
//        );