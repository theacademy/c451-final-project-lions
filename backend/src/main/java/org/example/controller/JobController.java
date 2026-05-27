package org.example.controller;

import org.server.model.Job;
import org.server.service.JobServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
@CrossOrigin
public class JobController {

   // @Autowired
    JobServiceImpl jobService;

    @GetMapping("/Jobs")
    public List<Job> getAllJobs(@RequestBody String Job){
        return null;
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable int id){
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable int id) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }

    @DeleteMapping("/{id}")
    public void refrefJob(@PathVariable int id) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }

}
