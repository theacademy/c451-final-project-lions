package org.example.service;

import org.example.model.Job;
import org.example.model.User;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User createNewUser(User user) {
        if (findUserByUsername(user.getName())!= null){
            user.setName("Username already in use");
            return user;
        }

        return null;
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
    public User findUserByUsername(String name) {
        return null;
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

    private boolean validatePassword(String password){
        String passwordReqex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordReqex);

        if (!passwordPattern.matcher(password).matches()) {

            return false;
        }
        return true;
    }
    //temp
    private int skillMatch(String parg){//temp
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
    }//temp
}
