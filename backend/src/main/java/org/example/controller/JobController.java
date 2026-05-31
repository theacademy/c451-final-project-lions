package org.example.controller;

import org.example.model.Job;
import org.example.service.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
@CrossOrigin
public class JobController {

    @Autowired
    JobServiceImpl jobService;

    @GetMapping("/Jobs")
    public ResponseEntity<List<Job>> getAllJobs(){

        return ResponseEntity.ok(jobService.getAllJobs());
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

    @DeleteMapping("/refresh")
    public void refreshJob(@PathVariable int id) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }

}
