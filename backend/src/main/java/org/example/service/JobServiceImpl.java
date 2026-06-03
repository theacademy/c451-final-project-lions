package org.example.service;

import org.example.dao.JobDao;
import org.example.dao.JobDaoImpl;
import org.example.dao.TrackedJobDaoImpl;
import org.example.dao.UserPreferenceDaoImpl;
import org.example.model.Job;
import org.example.model.Search;
import org.example.model.TrackedJob;
import org.example.model.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobServiceInterface {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private JobDaoImpl JobDao;

    @Autowired
    private TrackedJobDaoImpl trackedJobDao;
    @Autowired
    private UserPreferenceDaoImpl userPreferenceDao;

    @Override
    public Job createNewJob(Job job) {
        if (job == null) {
            return null;
        }

        String sql = "INSERT INTO jobs (greenhouse_job_id, company_id, title, location, description_html, description_text, absolute_url, seniority_level, skills_csv, posted_at, is_active, last_seen_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE, NOW()) ON DUPLICATE KEY UPDATE company_id = VALUES(company_id), title = VALUES(title), location = VALUES(location), description_html = VALUES(description_html), description_text = VALUES(description_text), absolute_url = VALUES(absolute_url), seniority_level = VALUES(seniority_level), skills_csv = VALUES(skills_csv), posted_at = VALUES(posted_at), is_active = TRUE, last_seen_at = NOW()";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, job.getGreenhouseJobId());
            ps.setObject(2, job.getCompanyId());
            ps.setString(3, job.getTitle());
            ps.setString(4, job.getLocation());
            ps.setString(5, job.getDescriptionHtml());
            ps.setString(6, job.getDescriptionText());
            ps.setString(7, job.getAbsoluteUrl());
            ps.setString(8, job.getSeniorityLevel());
            ps.setString(9, job.getSkillsCsv());
            ps.setTimestamp(10, job.getPostedAt());
            return ps;
        }, holder);

        if (job.getId() == null || job.getId() == 0) {
            Number key = holder.getKey();
            if (key != null) {
                job.setId(key.longValue());
            } else {
//                Job existingJob = findJobByGreenhouseId(job.getGreenhouseJobId());
//                if (existingJob != null) {
//                    job.setId(existingJob.getId());
//                }
            }
        }

        return job;
    }

    @Override
    public List<Job> getAllJobs() {


        return JobDao.getAllJobs(1);
    }

    @Override
    public Job findJobById(int id) {
        Job jod = JobDao.findJobById(id);
        return jod;
    }

    @Override
    public Job getJobApplicantMatch(int jobId, int userId) {
        if (jobId <= 0 || userId <= 0) {
            return null;
        }

        Job job = findJobById(jobId);
        if (job == null) {
            return null;
        }

        UserPreference preference = userPreferenceDao.findUserPreferenceById(userId);
        if (preference == null) {
            job.setMatchPercent(0);
            return job;
        }

        String jobSkillsCsv = job.getSkillsCsv();
        String userSkillsCsv = preference.getSkills();

        // Explicitly normalize both skill strings before computing overlap.
        if (jobSkillsCsv == null || jobSkillsCsv.isBlank()) {
            job.setMatchPercent(0);
            return job;
        }

        if (userSkillsCsv == null || userSkillsCsv.isBlank()) {
            List<String> userSkillsList = preference.getSkills_csv();
            if (userSkillsList == null || userSkillsList.isEmpty()) {
                job.setMatchPercent(0);
                return job;
            }
            userSkillsCsv = String.join(",", userSkillsList);
        }

        int matchPercent = calculateMatchPercent(normalizeSkills(jobSkillsCsv), normalizeSkills(userSkillsCsv));
        job.setMatchPercent(matchPercent);
        return job;
    }

    @Override
    public TrackedJob addJobMatch(int jobId, int userId) {
        if (jobId <= 0 || userId <= 0) {
            return null;
        }

        Job job = findJobById(jobId);
        if (job == null) {
            return null;
        }

        UserPreference preference = userPreferenceDao.findUserPreferenceById(userId);
        if (preference == null) {

            return null;
        }

        TrackedJob trackedJob = new TrackedJob();
        trackedJob.setJob_id(jobId);
        trackedJob.setUser_id(userId);

        String jobSkillsCsv = job.getSkillsCsv();
        String userSkillsCsv = preference.getSkills();

        // Explicitly normalize both skill strings before computing overlap.
        if (jobSkillsCsv == null ) {
            return null;
        }

        if (userSkillsCsv == null || userSkillsCsv.isBlank()) {
            List<String> userSkillsList = preference.getSkills_csv();
            if (userSkillsList == null || userSkillsList.isEmpty()) {
                trackedJob.setMatchedPercent(0);
                return trackedJobDao.createNewTrackedJob(trackedJob);
            }
            userSkillsCsv = String.join(",", userSkillsList);
        }

        int matchPercent = calculateMatchPercent(normalizeSkills(jobSkillsCsv), normalizeSkills(userSkillsCsv));
        trackedJob.setMatchedPercent(matchPercent);
        return trackedJobDao.createNewTrackedJob(trackedJob);
    }

    @Override
    public List<Job> searchorder(Search search, int userId){
        List<Job> jobs=  JobDao.searchJob(search);
        UserPreference preference = userPreferenceDao.findUserPreferenceById(userId);
        String userSkillsCsv = preference.getSkills();
        if (preference.getSkills().isBlank()) {

            return null;
        }
        int matchPercent;
        for (Job job : jobs) {
                 matchPercent = calculateMatchPercent(
                    normalizeSkills(job.getSkillsCsv()),
                    normalizeSkills(userSkillsCsv)
            );
            job.setMatchPercent(matchPercent);
        }

        jobs = jobs.stream()
                .filter(job -> job.getMatchPercent() >= 40)
                .sorted(Comparator.comparingInt(Job::getMatchPercent).reversed())
                .toList();
        return jobs;
    }

    @Override
    public List<Job> getCompanyJobs(Long id) {
        return JobDao.findJobByCompanyId(id);
    }

    @Override
    public void updateJob(Job job) {
        if (job == null || job.getId() == null) {
            return;
        }

        String sql = "UPDATE jobs SET greenhouse_job_id = ?, company_id = ?, title = ?, location = ?, description_html = ?, description_text = ?, absolute_url = ?, seniority_level = ?, skills_csv = ?, posted_at = ?, is_active = ?, last_seen_at = NOW() WHERE id = ?";
        jdbc.update(sql,
                job.getGreenhouseJobId(),
                job.getCompanyId(),
                job.getTitle(),
                job.getLocation(),
                job.getDescriptionHtml(),
                job.getDescriptionText(),
                job.getAbsoluteUrl(),
                job.getSeniorityLevel(),
                job.getSkillsCsv(),
                job.getPostedAt(),
                job.isActive(),
                job.getId());
    }

    @Override
    public void deleteJob(int id) {
       JobDao.deleteJob(id);
    }

    @Override
    public List<Job> searchJob(Search search){
        return JobDao.searchJob(search);
    }

    @Override
    public List<Job> getBoardJobs(String role, String location, String seniority, int page) {
        return JobDao.findActiveJobs(role, location, seniority, page);
    }

    public List<Job> findJobByGreenhouseId(Long greenhouseJobId) {
        if (greenhouseJobId == null) {
            return null;
        }

        List<Job> jobs = JobDao.getAllJobs(greenhouseJobId, 2);
        return jobs.isEmpty() ? null : jobs;
    }

    private Set<String> normalizeSkills(String skillsCsv) {
        if (skillsCsv == null || skillsCsv.isBlank()) {
            return new HashSet<>();
        }
        return Arrays.stream(skillsCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private Set<String> normalizeSkills(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            return new HashSet<>();
        }
        return skills.stream()
                .filter(s -> s != null && !s.isBlank())
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private int calculateMatchPercent(Set<String> jobSkills, Set<String> userSkills) {
        if (jobSkills.isEmpty() || userSkills.isEmpty()) {
            return 0;
        }

        Set<String> intersection = new HashSet<>(jobSkills);
        intersection.retainAll(userSkills);
        if (intersection.isEmpty()) {
            return 0;
        }

        return (int) Math.round(100.0 * intersection.size() / jobSkills.size());
    }

    private Job mapJob(ResultSet rs, int rowNum) throws SQLException {
        Job job = new Job();
        job.setId(rs.getLong("id"));
        job.setGreenhouseJobId(rs.getLong("greenhouse_job_id"));
        job.setCompanyId(rs.getLong("company_id"));
        job.setTitle(rs.getString("title"));
        job.setLocation(rs.getString("location"));
        job.setDescriptionHtml(rs.getString("description_html"));
        job.setDescriptionText(rs.getString("description_text"));
        job.setAbsoluteUrl(rs.getString("absolute_url"));
        job.setSeniorityLevel(rs.getString("seniority_level"));
        job.setSkillsCsv(rs.getString("skills_csv"));
        job.setPostedAt(rs.getTimestamp("posted_at"));
        job.setActive(rs.getBoolean("is_active"));
        job.setLastSeenAt(rs.getTimestamp("last_seen_at"));
        job.setCreatedAt(rs.getTimestamp("created_at"));
        return job;
    }
}
