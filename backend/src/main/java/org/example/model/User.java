package org.example.model;

import java.util.List;

public class User {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return first_name+" "+ last_name;
    }

    public void setName(String fName) {
        this.first_name=fName;
       // this.last_name=lName;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Job> getJods() {
        return jods;
    }

    public void setJods(List<Job> jods) {
        this.jods = jods;
    }

    int id;
    String first_name;
    String last_name;
    String email_address;
    List<String> skills;
    String password;
    List<Job> jods;
}

