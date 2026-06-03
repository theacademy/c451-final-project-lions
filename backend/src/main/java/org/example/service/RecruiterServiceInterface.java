package org.example.service;

import org.example.model.Job;
import org.example.model.Recrutiter;
import org.example.model.TrackedJob;
import org.example.model.User;

import java.util.List;

public interface RecruiterServiceInterface {

    Recrutiter findRecruiterById(int id);


    void editPassword(String password, int id);

    List<TrackedJob> getUserJobs(Long id);

    public List<Job> getCompanyJobs(Long id);

    String login(User user);
}
