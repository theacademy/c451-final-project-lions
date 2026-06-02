package org.example.dao;

import org.example.dao.mappers.user_preferencesmapper;
import org.example.model.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserPreferenceDaoImpl implements UserPreferenceDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public UserPreference createNewUserPreference(UserPreference preference) {
        final String INSERT_PREFERENCE = "INSERT INTO user_preferences( user_id, years_experience, desired_location, remote_preference, job_type, skills_csv) "
                + "VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_PREFERENCE,
                preference.getUser_id(),
                preference.getYears_experience(),
                preference.getDesired_location(),
                preference.getRemote_preference(),
                preference.getJob_type(),
                preference.getSkills());

        return preference;
    }

    @Override
    public UserPreference findUserPreferenceById(int id) {
        try {
            final String SELECT_PREFERENCE_BY_ID = "SELECT * FROM user_preferences WHERE user_id = ?";
            return jdbc.queryForObject(SELECT_PREFERENCE_BY_ID, new user_preferencesmapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateUserPreference(UserPreference preference) {
        final String UPDATE_PREFERENCE= "UPDATE user_preferences SET years_experience = ?, desired_location = ?, remote_preference = ?, job_type = ?, skills_csv = ? "
                + "WHERE user_id = ?";
        jdbc.update(UPDATE_PREFERENCE,
                preference.getYears_experience(),
                preference.getDesired_location(),
                preference.getRemote_preference(),
                preference.getJob_type(),
                preference.getSkills(),
                preference.getUser_id());
    }

    @Override
    public void deleteUserPreference(int id) {
        final String DELETE_PREFERENCE = "DELETE FROM user_preferences "
                + "WHERE user_id = ?";
        jdbc.update(DELETE_PREFERENCE, id);

    }
}
