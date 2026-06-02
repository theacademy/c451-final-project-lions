package org.example.service;

import org.example.model.TrackedJob;
import org.example.model.User;

import java.util.List;

public interface RecruiterServiceInterface {

    User findRecruiterById(int id);


    void editPassword(String password, int id);

    List<TrackedJob> getUserJobs(Long id);



    String login(User user);
}
