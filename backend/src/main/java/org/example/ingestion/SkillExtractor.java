package org.example.ingestion;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class SkillExtractor {

    /**
     * Extracts canonical skills from a piece of job text. Input can be either
     * raw HTML content (from Greenhouse) or already-cleaned plain text — the
     * method handles both.
     *
     * @return sorted, comma-separated string of canonical skill names.
     *         Empty string if no skills found.
     */
    public String extract(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        // 1. Decode HTML entities and strip tags (no-op if already plain text)
        String decoded = StringEscapeUtils.unescapeHtml4(text);
        String plainText = Jsoup.parse(decoded).text().toLowerCase();

        // 2. Match every alias against the text
        Set<String> matchedCanonicals = new TreeSet<>();  // sorted, no duplicates
        for (Map.Entry<String, String> entry : CanonicalSkills.ALIAS_TO_CANONICAL.entrySet()) {
            String alias = entry.getKey();
            String canonical = entry.getValue();

            if (containsAsSkill(plainText, alias)) {
                matchedCanonicals.add(canonical);
            }
        }

        // 3. Join into comma-separated string
        return matchedCanonicals.stream().collect(Collectors.joining(","));
    }

    /**
     * Checks if `alias` appears in `text` as a standalone token.
     * Handles regular word characters via word boundaries, and punctuation-heavy
     * skills (c++, c#, .net) via surrounding-character checks.
     */
    private boolean containsAsSkill(String text, String alias) {
        // For aliases with punctuation, regex \b doesn't work. Fall back to a
        // manual check using non-alphanumeric surroundings.
        if (containsRegexSpecialChars(alias)) {
            return matchesWithBoundaries(text, alias);
        }

        // Standard word-boundary regex for normal skills
        String pattern = "\\b" + Pattern.quote(alias) + "\\b";
        return Pattern.compile(pattern).matcher(text).find();
    }

    private boolean containsRegexSpecialChars(String s) {
        return s.matches(".*[^a-z0-9 ].*");  // anything other than alphanumeric/space
    }

    private boolean matchesWithBoundaries(String text, String alias) {
        int idx = 0;
        while ((idx = text.indexOf(alias, idx)) != -1) {
            // Check the character before and after — must not be alphanumeric
            boolean leftOk = idx == 0 || !Character.isLetterOrDigit(text.charAt(idx - 1));
            int endIdx = idx + alias.length();
            boolean rightOk = endIdx == text.length() || !Character.isLetterOrDigit(text.charAt(endIdx));

            if (leftOk && rightOk) {
                return true;
            }
            idx++;
        }
        return false;
    }
}