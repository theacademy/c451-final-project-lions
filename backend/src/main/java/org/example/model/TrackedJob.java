package org.example.model;

import java.sql.Timestamp;

public class TrackedJob {

    private int id ;
    private int user_id ;
    private int job_id ;
    private String status;
    private String notes;

    public int getMatchedPercent() {
        return matchedPercent;
    }

    public void setMatchedPercent(int matchedPercent) {
        this.matchedPercent = matchedPercent;
    }

    private int matchedPercent ;

    private Timestamp applied_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getApplied_at() {
        return applied_at;
    }

    public void setApplied_at(Timestamp applied_at) {
        this.applied_at = applied_at;
    }
}
