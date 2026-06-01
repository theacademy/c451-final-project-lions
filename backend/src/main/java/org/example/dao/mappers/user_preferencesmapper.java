package org.example.dao.mappers;

import org.example.model.User;
import org.example.model.UserPreference;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class user_preferencesmapper implements RowMapper<UserPreference> {

    @Override
    public UserPreference mapRow(ResultSet rs, int rowNum)  throws SQLException {
        UserPreference preference = new UserPreference();
        preference.setUser_id( rs.getInt("user_id"));
        preference.setYears_experience(rs.getInt("years_experience"));
        preference.setDesired_location(rs.getString("desired_location"));
        preference.setRemote_preference(rs.getString("remote_preference"));
        preference.setJob_type(rs.getString("job_type"));
        preference.setSkills_csv(rs.getString("skills_csv"));
        return preference;
    }
}

