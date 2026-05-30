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
        preference.setUser_id( rs.getInt("id"));
        preference.setYears_experience(rs.getInt("password_hash"));
        preference.setDesired_location(rs.getString("first_name"));
        preference.setRemote_preference(rs.getString("last_name"));
        preference.setJob_type(rs.getString("email_address"));
        preference.setSkills_csv(rs.getString("skills_csv"));
        return preference;
    }
}

