package org.example.ingestion;

import org.apache.commons.text.StringEscapeUtils;
import org.example.dao.CompanyDaoDb;
import org.example.dao.JobDaoDb;
import org.example.ingestion.greenhouse.GreenhouseJob;
import org.example.model.Company;
import org.example.model.Job;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class IngestService {

    @Autowired private CompanyDaoDb companyDaoDb;
    @Autowired private JobDaoDb jobDaoDb;
    @Autowired private GreenhouseClient greenhouseClient;
    @Autowired private SkillExtractor skillExtractor;
    @Autowired private TitleParser titleParser;

    @Autowired private JdbcTemplate jdbc;

    /**
     * Scheduled sync — runs every 3 hours.
     * Pulls all jobs from each seeded company, upserts them into the jobs table,
     * then deactivates any jobs that didn't appear in this sync.
     */
    @Scheduled(fixedRate = 3 * 60 * 60 * 1000)  // 3 hours in milliseconds
    public void syncAllCompanies() {
        Timestamp syncStart = jdbc.queryForObject("SELECT NOW()", Timestamp.class);
        System.out.println("=== Starting ingestion sync at " + syncStart + " ===");

        List<Company> companies = companyDaoDb.findAll();
        int totalJobsProcessed = 0;

        for (Company company : companies) {
            int jobsForThisCompany = syncCompany(company);
            totalJobsProcessed += jobsForThisCompany;
        }

        int deactivated = jobDaoDb.deactivateStaleJobs(syncStart);

        System.out.println("=== Sync complete: " + totalJobsProcessed
                + " jobs processed across " + companies.size() + " companies, "
                + deactivated + " stale jobs deactivated ===");
    }

    /**
     * Syncs jobs for a single company. Returns the number of jobs processed.
     */
    private int syncCompany(Company company) {
        try {
            List<GreenhouseJob> ghJobs = greenhouseClient.fetchJobs(company.getGreenhouseToken());

            for (GreenhouseJob ghJob : ghJobs) {
                Job job = transform(ghJob, company.getId());
                jobDaoDb.save(job);
            }

            companyDaoDb.updateLastSyncedAt(
                    company.getId(),
                    new Timestamp(System.currentTimeMillis())
            );

            System.out.println("  Synced " + ghJobs.size() + " jobs from " + company.getName());
            return ghJobs.size();

        } catch (Exception e) {
            System.err.println("  Failed to sync " + company.getName() + ": " + e.getMessage());
            return 0;
        }
    }

    /**
     * Transforms a GreenhouseJob (external API shape) into a Job (our DB shape).
     * Handles HTML decoding, skill extraction, seniority parsing, timestamp parsing.
     */
    private Job transform(GreenhouseJob ghJob, Long companyId) {
        // Decode HTML entities (&lt; → <)
        String descriptionHtml = ghJob.getContent() != null
                ? StringEscapeUtils.unescapeHtml4(ghJob.getContent())
                : null;

        // Strip HTML to get plain text
        String descriptionText = descriptionHtml != null
                ? Jsoup.parse(descriptionHtml).text()
                : null;

        // Extract skills from title + description combined
        String combinedText = (ghJob.getTitle() != null ? ghJob.getTitle() : "")
                + " " + (descriptionText != null ? descriptionText : "");
        String skillsCsv = skillExtractor.extract(combinedText);

        // Parse seniority from title
        String seniority = titleParser.parseSeniority(ghJob.getTitle());

        // Location is nested in the Greenhouse JSON
        String location = ghJob.getLocation() != null
                ? ghJob.getLocation().getName()
                : null;

        // Parse the ISO timestamp
        Timestamp postedAt = parseTimestamp(ghJob.getFirstPublished());

        // Build the Job
        Job job = new Job();
        job.setGreenhouseJobId(ghJob.getId());
        job.setCompanyId(companyId);
        job.setTitle(ghJob.getTitle());
        job.setLocation(location);
        job.setDescriptionHtml(descriptionHtml);
        job.setDescriptionText(descriptionText);
        job.setAbsoluteUrl(ghJob.getAbsoluteUrl());
        job.setSeniorityLevel(seniority);
        job.setSkillsCsv(skillsCsv);
        job.setPostedAt(postedAt);
        // isActive, lastSeenAt, createdAt are set by JobDao.save() via SQL

        return job;
    }

    /**
     * Parses an ISO 8601 timestamp string (e.g. "2026-05-15T14:15:03-04:00")
     * into a java.sql.Timestamp. Returns null on parse failure.
     */
    private Timestamp parseTimestamp(String isoString) {
        if (isoString == null || isoString.isBlank()) {
            return null;
        }
        try {
            OffsetDateTime odt = OffsetDateTime.parse(isoString);
            return Timestamp.from(odt.toInstant());
        } catch (DateTimeParseException e) {
            System.err.println("Could not parse timestamp: " + isoString);
            return null;
        }
    }
}