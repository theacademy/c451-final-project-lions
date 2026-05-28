package org.example.model;

import java.sql.Timestamp;

public class Company {
    private Long id;
    private String greenhouseToken;
    private String name;
    private Timestamp lastSyncedAt;


    public Company() {
    }

    public Company(Long id, String greenhouseToken, String name, Timestamp lastSyncedAt) {
        this.id = id;
        this.greenhouseToken = greenhouseToken;
        this.name = name;
        this.lastSyncedAt = lastSyncedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGreenhouseToken() {
        return greenhouseToken;
    }

    public void setGreenhouseToken(String greenhouseToken) {
        this.greenhouseToken = greenhouseToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastSyncedAt() {
        return lastSyncedAt;
    }

    public void setLastSyncedAt(Timestamp lastSyncedAt) {
        this.lastSyncedAt = lastSyncedAt;
    }
}
