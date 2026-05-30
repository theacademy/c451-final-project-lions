package org.example.service;

import org.example.model.Job;
import org.example.model.User;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    private JwtUtil jwtUtil;
    // Stores users using username as the key
    private final Map<String, User> usersByName = new ConcurrentHashMap<>();
    // Generates unique IDs for each new user
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public User createNewUser(User user) { // Creates a new user and saves them
        // Check if user data is missing (invalid request)
        if (user == null || user.getName() == null || user.getPassword() == null) {
            return null;
        }
        // Check if a user with this username already exists
        if (findUserByUsername(user.getName()) != null) {
            return null;
        }
        User newUser = new User();  // Create a new User object
        newUser.setId(idGenerator.getAndIncrement()); // Assign a unique ID to the user
        // Set username and password
        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        // Set skills (empty list if none provided)
        newUser.setSkills(user.getSkills() == null ? new ArrayList<>() : user.getSkills());
        // Save user in the map (username -> user)
        usersByName.put(newUser.getName(), newUser);

        return newUser;
    }



    @Override
    public List<User> getAllJobs() {
        return List.of();
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public void editUser(User user) {

    }

    @Override
    public void editPassword(String password, int id) {

    }

    @Override
    public User addSkills(List<String> skills, int id) {
        return null;
    }

    @Override
    public void addJob(Job job, int id) {

    }

    @Override
    public void updateJobstatus(int id, String status) {

    }


    @Override
    public void deleteUser(int id) {

    }

    @Override
    // Finds and returns a user by their username
    public User findUserByUsername(String name) {
        return usersByName.get(name);
    }

    @Override
    public String login(User user) {
        User userCheck = findUserByUsername(user.getName());
        if(userCheck!=null &&
                userCheck.getName().equals(user.getName())
                &&userCheck.getPassword().equals(user.getPassword()))
        {
            String jwtToken = jwtUtil.generateToken(user.getName());
            return jwtToken;
        }
        return "Error";
    }

    private int skillsMatch(int id, int jobid){
        String jobDesc = "We are looking for Java, Spring Boot and SQL experience";

        List<String> skills = Arrays.asList(
                "java",
                "spring boot",
                "sql",
                "python"
        );

        String normalized = jobDesc.toLowerCase();

        Set<String> matched = new HashSet<>();

        for (String skill : skills) {
            if (normalized.contains(skill.toLowerCase())) {
                matched.add(skill);
            }
        }


        return matched.size();
    }

}
