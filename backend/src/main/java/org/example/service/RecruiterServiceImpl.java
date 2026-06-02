package org.example.service;

import org.example.dao.*;
import org.example.dao.JobDaoImpl;
import org.example.model.Job;
import org.example.model.Recrutiter;
import org.example.model.TrackedJob;
import org.example.model.User;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class RecruiterServiceImpl implements RecruiterServiceInterface {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RecruiterDaoImpl recruiterDao;
    @Autowired
    private TrackedJobDaoImpl trackedJobDao;
    @Autowired
    private JobDaoImpl jobDao;



    @Override
    public Recrutiter findRecruiterById(int id) {
        return recruiterDao.findRecrutiterById(id);
    }



    @Override
    public void editPassword(String password, int id) {
        if (password == null) {
            return ;
        }
        // Hash the new password before it hits the DB
          recruiterDao.editRecrutiter(passwordEncoder.encode(password), id);
    }

    @Override
    public List<TrackedJob> getUserJobs(Long id) {
        return trackedJobDao.findTrackedJobByjobId(id);
    }

    @Override
    public List<Job> getCompanyJobs(Long id) {
        return jobDao.findJobByCompanyId(id);
    }

    @Override
    public String login(User user) {
        if (user == null
                || isBlank(user.getEmail_address())
                || isBlank(user.getPassword())) {
            return null;
        }

        Recrutiter userCheck = recruiterDao.findRecrutiterById(user.getId());

        if (userCheck != null &&
                userCheck.getPassword() != null &&
                passwordEncoder.matches(user.getPassword(), userCheck.getPassword())) {
            // Token subject = the real user's email
            return jwtUtil.generateToken(userCheck.getEmail_address());
        }
        return null;
    }
    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

}
