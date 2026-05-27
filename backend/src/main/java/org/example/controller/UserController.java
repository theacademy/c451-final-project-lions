package org.example.controller;

import org.example.model.Job;
import org.example.model.User;
import org.example.service.JobServiceImpl;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/Job")
    public List<User> getUserByName (@RequestBody String name){
        return null;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return null;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        //YOUR CODE STARTS HERE

        return null;

        //YOUR CODE ENDS HERE
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        //YOUR CODE STARTS HERE

        return null;

        //YOUR CODE ENDS HERE
    }

    @PutMapping("/updatePass/{id}")
    public User updatePass(@PathVariable int id, @RequestBody String password) {
        //YOUR CODE STARTS HERE

        return null;

        //YOUR CODE ENDS HERE
    }

    @PutMapping("/addSkills/{id}")
    public User addSkills(@PathVariable int id, @RequestBody List<String> Skills) {
        //YOUR CODE STARTS HERE

        return null;

        //YOUR CODE ENDS HERE
    }

    @PutMapping("/updatestatus/{id}")
    public User updateStatus(@PathVariable int id, @RequestBody String status) {
        //YOUR CODE STARTS HERE

        return null;

        //YOUR CODE ENDS HERE
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }

    @DeleteMapping("/{id}")
    public void deleteUserJob(@PathVariable Job job) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }





}
