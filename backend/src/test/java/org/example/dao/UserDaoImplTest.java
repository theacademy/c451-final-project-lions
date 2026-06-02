package org.example.dao;

import org.example.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    void createNewUser() {
        User user = new User();
        user.setId(1);
        user.setFirst_name("John");
        user.setLast_name("Doe");
        user.setEmail_address("john.doe@example.com");
        user.setPassword("securePassword123");

        userDao.createNewUser(user);

        List<User> newList = userDao.getAllUsers();
        assertNotNull(newList);

        int i = 0;
        for (User usr : newList) {
            if (usr.getEmail_address().contains("john.doe@example.com")) {
                i++;
            }
        }

        assertTrue(i != 0);
    }

    @Test
    @DisplayName("Find A User By ID: 1")
    public void findAUserById1Test() {
        User user = userDao.findUserById(1);
        assertNotNull(user);
        assertEquals(1, user.getId());
    }


    @Test
    @DisplayName("Find A User By ID: 1")
    public void findAUserByEmail() {
        User user = userDao.findUserByEmail("john.doe@example.com");
        assertNotNull(user);
        assertEquals("john.doe@example.com", user.getEmail_address());
    }

    @Test
    @DisplayName("Delete a User")
    public void deleteAUserTest() {
        userDao.deleteUser(1);
        assertNotNull(userDao.getAllUsers());
        assertEquals(0, userDao.getAllUsers().size());
    }


}