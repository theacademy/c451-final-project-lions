package org.example.ingestion;

import org.example.ingestion.greenhouse.GreenhouseJob;
import org.example.ingestion.greenhouse.GreenhouseJobResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class GreenhouseClient {
    private static final String BASE_URL = "https://boards-api.greenhouse.io/v1/boards/%s/jobs?content=true";

    private final RestTemplate restTemplate = new RestTemplate();

    // Fetch all active jobs for a given company token
    // Return empty list if the company has no jobs or request fails
    public List<GreenhouseJob> fetchJobs(String companyToken) {
        String url = String.format(BASE_URL, companyToken);

        try {
            GreenhouseJobResponse response = restTemplate.getForObject(url, GreenhouseJobResponse.class);

            if (response == null || response.getJobs() == null) {
                return Collections.emptyList();
            }

            return response.getJobs();
        } catch (Exception e) {
            System.err.println("Failed to fetch jobs for company token '" + companyToken + "': " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
