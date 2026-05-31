package org.example.model;

import java.util.Arrays;
import java.util.List;

public class UserPreference {
    private int user_id;
    private int years_experience;
    private String desired_location;
    private String remote_preference;
    private String job_type;
    private List<String> skills_csv;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getYears_experience() {
        return years_experience;
    }

    public void setYears_experience(int years_experience) {
        this.years_experience = years_experience;
    }

    public String getDesired_location() {
        return desired_location;
    }

    public void setDesired_location(String desired_location) {
        this.desired_location = desired_location;
    }

    public String getRemote_preference() {
        return remote_preference;
    }

    public void setRemote_preference(String remote_preference) {
        this.remote_preference = remote_preference;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public List<String> getSkills_csv() {
        return skills_csv;
    }

    public void setSkills_csv(List<String> skills_csv) {
        this.skills_csv = skills_csv;
    }

    public void setSkills_csv(String skills_csv) {
        this.skills_csv = Arrays.stream(skills_csv.split(","))
                .map(String::trim)
                .toList();
    }
    public String getSkills() {
        return String.join(",", skills_csv);

    }



}
