package org.example.ingestion.greenhouse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GreenhouseJob {
    private Long id;
    private String title;
    private String content;

    @JsonProperty("absolute_url")
    private String absoluteUrl;

    @JsonProperty("first_published")
    private String firstPublished;

    @JsonProperty("updated_at")
    private String updatedAt;

    private Location location;

    public GreenhouseJob() {}


    // Getter and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public String getFirstPublished() {
        return firstPublished;
    }

    public void setFirstPublished(String firstPublished) {
        this.firstPublished = firstPublished;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // Nested class for the "location" object in the JSON
    public static class Location {
        private String name;

        public Location() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
