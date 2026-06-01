package org.example.service;

import org.example.model.UserPreference;

public interface PreferenceServiceInterface {

    UserPreference getPreferencesByUserId(int userId);

    UserPreference savePreferences(UserPreference pref);   // upsert
}
