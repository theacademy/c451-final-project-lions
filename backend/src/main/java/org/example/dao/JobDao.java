package org.example.dao;

import org.example.model.Job;

import java.util.List;

public interface JobDao {
    Job createNewJob(Job job);

    List<Job> getAllJobs(int company_id, int page);

    Job findJobById(int id);

    void updateJob(Job job);

    void deleteJob(int id);

}
