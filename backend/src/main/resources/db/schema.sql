-- schema.sql
-- Database schema for job_tracker
-- Instructions: open in MySQL Workbench and run the entire file.

CREATE DATABASE IF NOT EXISTS job_tracker
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE job_tracker;

CREATE TABLE users (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email_address VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_preferences (
    user_id BIGINT PRIMARY KEY,
    years_experience INT,
    desired_location VARCHAR(255),
    remote_preference VARCHAR(20),
    job_type VARCHAR(20),
    skills_csv VARCHAR(500),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_prefs_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE companies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    greenhouse_token VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    last_synced_at TIMESTAMP NULL
);

CREATE TABLE recruiters (
     id INT AUTO_INCREMENT PRIMARY KEY,
     first_name VARCHAR(100) NOT NULL,
     last_name VARCHAR(100) NOT NULL,
     email_address VARCHAR(255) NOT NULL UNIQUE,
     password VARCHAR(255) NOT NULL,
     company_id BIGINT,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     CONSTRAINT fk_users_company FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
 );

CREATE TABLE jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    greenhouse_job_id BIGINT NOT NULL UNIQUE,
    company_id BIGINT NOT NULL,
    title VARCHAR(500) NOT NULL,
    location VARCHAR(1000),
    description_html MEDIUMTEXT,
    description_text MEDIUMTEXT,
    absolute_url VARCHAR(1000),
    seniority_level VARCHAR(20),
    skills_csv VARCHAR(500),
    posted_at TIMESTAMP NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    last_seen_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_jobs_company FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE tracked_jobs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'wishlist',
    match_score INT,
    notes TEXT,
    Match_score INT,
    applied_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_job (user_id, job_id),
    CONSTRAINT fk_tracked_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_tracked_job FOREIGN KEY (job_id) REFERENCES jobs(id)
)

