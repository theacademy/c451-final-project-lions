package org.example.ingestion.greenhouse;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GreenhouseJobResponse {

    private List<GreenhouseJob> jobs;

    public GreenhouseJobResponse() {}


    public List<GreenhouseJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<GreenhouseJob> jobs) {
        this.jobs = jobs;
    }
}
