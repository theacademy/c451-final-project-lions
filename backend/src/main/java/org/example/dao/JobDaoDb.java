package org.example.dao;

import org.example.model.Job;

import java.sql.Timestamp;

public interface JobDaoDb {

    void save(Job job);

    int deactivateStaleJobs(Timestamp syncStart);
}
