package org.example.model;

import java.util.Date;
import java.util.List;

public class Job {
    int id;
    Double greenhouse_job_id;
    Double company_id;
    String title;
    String location;
    String description_html;
    String description_text;
    String absolute_url;
    String seniority_level;
    List<String>  skills_csv;
    Date posted_at;
    boolean is_active;
    Date last_seen_at;
    Date created_at;

}


