package org.example.dao;

import org.example.model.Job;
import org.example.model.Search;

import java.util.List;

public interface JobDao {
    Job createNewJob(Job job);

    List<Job> getAllJobs(Long company_id, int page);

    Job findJobById(int id);

    List<Job> searchJob(Search search);

    void deleteJob(int id);

    public List<Job> getAllJobs(int page);

    List<Job> findActiveJobs(String role, String location, String seniority, int page);

}
