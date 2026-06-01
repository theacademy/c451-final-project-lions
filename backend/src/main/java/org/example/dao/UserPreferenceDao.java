package org.example.dao;

import org.example.model.Job;
import org.example.model.UserPreference;

public interface UserPreferenceDao{

    UserPreference createNewUserPreference(UserPreference preference );


    UserPreference findUserPreferenceById(int id);

    void updateUserPreference(UserPreference preference);

    void deleteUserPreference(int id);
}
