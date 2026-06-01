package org.example.dao;

import org.example.model.Recrutiter;
import org.example.model.User;

public interface RecruiterDao {

    Recrutiter findUserById(int id);
    Recrutiter editUser(Recrutiter Recrutiter);

}
