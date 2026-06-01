package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class RecruiterServiceImpl implements RecruiterServiceInterface {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(int id) {
        return null;
    }



    @Override
    public void editPassword(String password, int id) {
        if (password == null) {
            return ;
        }
        // Hash the new password before it hits the DB
        userDao.editPassword(passwordEncoder.encode(password), id);
    }

    @Override
    public String login(User user) {
        if (user == null
                || isBlank(user.getEmail_address())
                || isBlank(user.getPassword())) {
            return null;
        }

      //  User userCheck = userDao.findUserByEmail(user.getEmail_address());

//        if (userCheck != null &&
//                userCheck.getPassword() != null &&
//                passwordEncoder.matches(user.getPassword(), userCheck.getPassword())) {
//            // Token subject = the real user's email
//            return jwtUtil.generateToken(userCheck.getEmail_address());
//        }
        return null;
    }
    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

}
