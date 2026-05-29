package org.example.controller;

import org.example.model.Job;
import org.example.service.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
@CrossOrigin
public class JobController {

    @Autowired
    JobServiceImpl jobService;

    @GetMapping("/Jobs")
    public List<Job> getAllJobs(@RequestBody String Job){

        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable int id){
        Job jod  = jobService.findJobById(id);
        return jod;
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable int id) {
        //YOUR CODE STARTS HERE
        jobService.deleteJob(id);

        //YOUR CODE ENDS HERE
    }

    @DeleteMapping("/refresh")
    public void refreshJob(@PathVariable int id) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }

}
