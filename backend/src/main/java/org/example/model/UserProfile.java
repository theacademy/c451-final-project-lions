package org.example.model;

/**
 * Read model for the profile page: the user's basic info plus their preferences,
 * without exposing the password hash.
 */
public class UserProfile {
    private int id;
    private String first_name;
    private String last_name;
    private String email_address;
    private UserPreference preferences;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }

    public String getEmail_address() { return email_address; }
    public void setEmail_address(String email_address) { this.email_address = email_address; }

    public UserPreference getPreferences() { return preferences; }
    public void setPreferences(UserPreference preferences) { this.preferences = preferences; }
}
