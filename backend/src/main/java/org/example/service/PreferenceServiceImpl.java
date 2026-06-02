package org.example.service;

import org.example.dao.UserPreferenceDao;
import org.example.model.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenceServiceImpl implements PreferenceServiceInterface {

    @Autowired
    private UserPreferenceDao userPreferenceDao;   // inject the interface, not the Impl

    @Override
    public UserPreference getPreferencesByUserId(int userId) {
        return userPreferenceDao.findUserPreferenceById(userId);
    }

    @Override
    public UserPreference savePreferences(UserPreference pref) {
        if (pref == null) {
            return null;
        }
        // Upsert: insert if this user has no prefs yet, otherwise update
        UserPreference existing = userPreferenceDao.findUserPreferenceById(pref.getUser_id());
        if (existing == null) {
            return userPreferenceDao.createNewUserPreference(pref);
        }
        userPreferenceDao.updateUserPreference(pref);
        return pref;
    }
}

