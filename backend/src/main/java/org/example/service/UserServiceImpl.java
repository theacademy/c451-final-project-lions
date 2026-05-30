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

    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public User createNewUser(User user) {

        if (user == null ||
                user.getEmail_address() == null ||
                user.getPassword() == null) {
            return null;
        }

        if (findUserByEmail(user.getEmail_address()) != null) {
            return null;
        }

        User newUser = new User();
        newUser.setId(idGenerator.getAndIncrement());

        newUser.setFirst_name(user.getFirst_name());
        newUser.setLast_name(user.getLast_name());
        newUser.setEmail_address(user.getEmail_address());
        newUser.setPassword(user.getPassword());

        newUser.setSkills(
                user.getSkills() == null
                        ? new ArrayList<>()
                        : new ArrayList<>(user.getSkills())
        );

        usersByEmail.put(newUser.getEmail_address(), newUser);

        return newUser;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(usersByEmail.values());
    }

    @Override
    public User findUserById(int id) {
        for (User user : usersByEmail.values()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void editUser(User user) {

        User existing = findUserById(user.getId());

        if (existing != null) {

            String oldEmail = existing.getEmail_address();
            String newEmail = user.getEmail_address();

            existing.setFirst_name(user.getFirst_name());
            existing.setLast_name(user.getLast_name());
            existing.setPassword(user.getPassword());

            if (user.getSkills() != null) {
                existing.setSkills(user.getSkills());
            }

            if (newEmail != null && !newEmail.equals(oldEmail)) {

                usersByEmail.remove(oldEmail);
                existing.setEmail_address(newEmail);
                usersByEmail.put(newEmail, existing);

            } else {
                existing.setEmail_address(oldEmail);
            }
        }
    }

    @Override
    public void editPassword(String password, int id) {

        User user = findUserById(id);

        if (user != null) {
            user.setPassword(password);
        }
    }

    @Override
    public User addSkills(List<String> skills, int id) {

        User user = findUserById(id);

        if (user != null && skills != null) {

            if (user.getSkills() == null) {
                user.setSkills(new ArrayList<>());
            }

            user.getSkills().addAll(skills);
        }

        return user;
    }

    @Override
    public void addJob(Job job, int id) {
        System.out.println("addJob not implemented");
    }

    @Override
    public void updateJobstatus(int id, String status) {
        System.out.println("updateJobstatus not implemented");
    }

    @Override
    public void deleteUser(int id) {

        User user = findUserById(id);

        if (user != null) {
            usersByEmail.remove(user.getEmail_address());
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    @Override
    public User findUserByUsername(String name) {
        return usersByEmail.get(name);
    }

    @Override
    public String login(User user) {

        if (user == null || user.getEmail_address() == null || user.getPassword() == null) {
            return null;
        }

        User userCheck = findUserByEmail(user.getEmail_address());

        if (userCheck != null &&
                userCheck.getPassword() != null &&
                userCheck.getPassword().equals(user.getPassword())) {

            return jwtUtil.generateToken(user.getEmail_address());
        }

        return null;
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