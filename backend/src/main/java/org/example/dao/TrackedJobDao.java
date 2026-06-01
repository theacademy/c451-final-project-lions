package org.example.dao;

import org.example.model.TrackedJob;

public interface TrackedJobDao {


    TrackedJob createNewTrackedJob(TrackedJob trackedJob );


    TrackedJob findTrackedJobById(int id);

    void updateTrackedJob(TrackedJob trackedJob);

    void deleteTrackedJob(int id);
}
