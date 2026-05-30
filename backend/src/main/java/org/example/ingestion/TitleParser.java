package org.example.ingestion;

import org.springframework.stereotype.Component;

@Component
public class TitleParser {


    // Parses seniority level from a job title.
    // Returns one of: "junior", "mid", "senior", "lead", or null if title is null/blank.
    // Defaults to "mid" for titles with no recognizable seniority keyword.
    public String parseSeniority(String title) {
        if (title == null || title.isBlank()) {
            return null;
        }

        String lower = title.toLowerCase();

        // Order matters: check more specific/senior keywords first
        if (containsAny(lower, "junior", "jr.", "jr ", "graduate", "entry", "intern")) {
            return "junior";
        }
        if (containsAny(lower, "principal", "staff", "lead", "head of", "manager", "director")) {
            return "lead";
        }
        if (containsAny(lower, "senior", "sr.", "sr ")) {
            return "senior";
        }

        return "mid";
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}