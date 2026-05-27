package org.example.service;

import org.server.model.Job;

import java.util.List;

public interface JobServiceInterface {
    Job createNewJob(Job job);

    List<Job> getAllJobs();

    Job findJobById(int id);

    void updateJob(Job job);

    void deleteJob(int id);
}
