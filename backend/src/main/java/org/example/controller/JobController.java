package org.example.controller;

import org.example.dao.UserPreferenceDaoImpl;
import org.example.model.Job;
import org.example.model.Search;
import org.example.model.UserPreference;
import org.example.service.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/job")
@CrossOrigin
public class JobController {

    @Autowired
    JobServiceImpl jobService;

    @Autowired
    UserPreferenceDaoImpl userPreferenceDao;

    @GetMapping("/Jobs")
    public ResponseEntity<List<Job>> getAllJobs(@RequestBody Long id){

        return ResponseEntity.ok(jobService.findJobByGreenhouseId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable int id){

        Job jod  = jobService.findJobById(id);
        if(jod==null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Not matching");
        }
        return ResponseEntity.ok(jod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteJob(@PathVariable int id) {

        jobService.deleteJob(id);
        return ResponseEntity.ok("success");
    }

    @PostMapping("")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        if (job == null || job.getCompanyId() == null || job.getGreenhouseJobId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Job created = jobService.createNewJob(job);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(@RequestBody Search search) {
        if (search == null || search.getCompanyId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        List<Job> jobs = jobService.searchJob(search);
        if (jobs == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/search/user/{userId}")
    public ResponseEntity<?> searchJobsByUserPreference(@PathVariable int userId,
                                                        @RequestBody Search search) {
        if (search == null || search.getCompanyId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("companyId is required");
        }

        UserPreference preference = userPreferenceDao.findUserPreferenceById(userId);
        if (preference == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No saved preferences found for userId=" + userId);
        }
        if (preference.getSkills_csv() == null || preference.getSkills_csv().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User preferences exist but no skills are saved for userId=" + userId);
        }

        search.setSkills(preference.getSkills_csv());
        List<Job> jobs = jobService.searchJob(search);
        if (jobs == null || jobs.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(jobs);
    }

}
