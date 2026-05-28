package org.example.service;

import org.example.model.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements  JobServiceInterface{


    @Override
    public Job createNewJob(Job job) {
        return null;
    }

    @Override
    public List<Job> getAllJobs() {
        return List.of();
    }

    @Override
    public Job findJobById(int id) {
        return null;
    }

    @Override
    public void updateJob(Job job) {

    }

    @Override
    public void deleteJob(int id) {

    }
}
