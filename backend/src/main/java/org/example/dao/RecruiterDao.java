package org.example.dao;

import org.example.model.Recrutiter;

public interface RecruiterDao {

    Recrutiter findRecrutiterById(int id);
    void editRecrutiter(String passwordHash, int id);



}
