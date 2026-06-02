package org.example.service;

import org.example.model.Job;
import org.example.model.Search;

import java.util.List;

public interface JobServiceInterface {
    Job createNewJob(Job job);

    List<Job> getAllJobs();

    Job findJobById(int id);

    void updateJob(Job job);

    void deleteJob(int id);

    List<Job> searchJob(Search search);

    List<Job> getBoardJobs(String role, String location, String seniority, int page);

    Job getJobApplicantMatch(int jobId, int userId);

    public List<Job> getCompanyJobs(Long id) ;

}
