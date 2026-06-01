package org.example.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Search {
    private String location;
    private String seniority_level;
    private Long companyId;
    private List<String> skills;
    private String skillsCsv;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeniority_level() {
        return seniority_level;
    }

    public void setSeniority_level(String seniority_level) {
        this.seniority_level = seniority_level;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
        this.skillsCsv = skills == null ? null : String.join(",", skills);
    }

    public String getSkillsCsv() {
        return skillsCsv;
    }

    public void setSkillsCsv(String skillsCsv) {
        this.skillsCsv = skillsCsv;
        this.skills = skillsCsv == null ? null : Arrays.stream(skillsCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }
}
