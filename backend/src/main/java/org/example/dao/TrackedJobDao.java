package org.example.dao;

import org.example.model.TrackedJob;

import java.util.List;

public interface TrackedJobDao {


    TrackedJob createNewTrackedJob(TrackedJob trackedJob );


    List<TrackedJob> findTrackedJobById(int id);

    void updateTrackedJob(TrackedJob trackedJob);

    void deleteTrackedJob(int id);

    TrackedJob findTrackedJobByjobId(Long id);
}
