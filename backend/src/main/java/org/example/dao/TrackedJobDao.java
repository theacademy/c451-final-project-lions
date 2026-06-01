package org.example.dao;

import org.example.model.TrackedJob;

public interface TrackedJobDao {


    TrackedJob createNewUserPreference(TrackedJob trackedJob );


    TrackedJob findUserPreferenceById(int id);

    void updateUserPreference(TrackedJob trackedJob);

    void deleteUserPreference(int id);
}
