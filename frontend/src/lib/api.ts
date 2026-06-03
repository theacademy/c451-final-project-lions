import { getToken } from "@/src/lib/auth";
import { ApplicantMatchInfo } from "@/src/types/types";

const BASE_URL =
  process.env.NEXT_PUBLIC_API_BASE_URL ?? "http://localhost:8080";

export interface SignupPayload {
  first_name: string;
  last_name: string;
  email_address: string;
  password: string;
}

export interface LoginPayload {
  email_address: string;
  password: string;
}

export interface Preferences {
  user_id?: number;
  skills_csv?: string;
  desired_location?: string;
  desired_role?: string;
  remote_preference?: string;
  job_type?: string;
  years_experience?: number;
}

function authHeaders(): Record<string, string> {
  const token = getToken();
  return token ? { Authorization: `Bearer ${token}` } : {};
}

// POST /user/add — returns the created user, or null if the email is taken
export async function signup(payload: SignupPayload): Promise<unknown | null> {
  const res = await fetch(`${BASE_URL}/user/add`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  if (res.status === 204) return null; // duplicate email, rejected
  if (!res.ok) throw new Error("Signup failed");
  return res.json();
}

// POST /user/login — backend returns a BARE JWT STRING (not JSON), or empty on bad creds
export async function login(payload: LoginPayload): Promise<string | null> {
  const res = await fetch(`${BASE_URL}/user/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  if (res.status === 204) return null; // invalid credentials
  if (!res.ok) throw new Error("Login request failed");
  const token = await res.text();
  return token || null;
}

// GET /user/preferences — null when none exist yet (HTTP 204) => first login
export async function getPreferences(): Promise<Preferences | null> {
  const res = await fetch(`${BASE_URL}/user/preferences`, {
    headers: { ...authHeaders() },
  });
  if (res.status === 204) return null;
  if (!res.ok) throw new Error("Failed to load preferences");
  return res.json();
}

// GET /{jobId}/applicant/{userId} — match for job and use; null when not found (HTTP 204)
export async function getJobMatchForApplicant(
  jobID: number,
  applicantID: number,
  controller: AbortController,
): Promise<ApplicantMatchInfo | null> {
  const res = await fetch(`${BASE_URL}/job/${jobID}/applicant/${applicantID}`, {
    signal: controller.signal,
  });
  if (res.status === 204) return null;
  if (!res.ok) throw new Error("Failed to load job");
  return res.json();
}

// POST /user/preferences
export async function savePreferences(prefs: Preferences): Promise<void> {
  const res = await fetch(`${BASE_URL}/user/preferences`, {
    method: "POST",
    headers: { "Content-Type": "application/json", ...authHeaders() },
    body: JSON.stringify(prefs),
  });
  if (!res.ok) throw new Error("Failed to save preferences");
}

export interface BoardJob {
  id: number;
  title: string;
  companyName?: string;
  location?: string;
  seniorityLevel?: string;
  skillsCsv?: string;
  absoluteUrl?: string;
}

export interface BoardFilters {
  role?: string;
  location?: string;
  seniority?: string;
  page?: number;
}

// GET /job/board — active jobs, optional role/location/seniority filters (public)
export async function getBoardJobs(
  filters: BoardFilters = {},
): Promise<BoardJob[]> {
  const q = new URLSearchParams();
  if (filters.role) q.set("role", filters.role);
  if (filters.location) q.set("location", filters.location);
  if (filters.seniority) q.set("seniority", filters.seniority);
  if (filters.page != null) q.set("page", String(filters.page));
  const res = await fetch(`${BASE_URL}/job/board?${q.toString()}`);
  if (!res.ok) throw new Error("Failed to load jobs");
  return res.json();
}

export interface JobDetail extends BoardJob {
  descriptionText?: string;
  descriptionHtml?: string;
}

// GET /job/{id} — single job; null when not found (HTTP 204)
export async function getJobById(
  id: number | string,
): Promise<JobDetail | null> {
  const res = await fetch(`${BASE_URL}/job/${id}`);
  if (res.status === 204) return null;
  if (!res.ok) throw new Error("Failed to load job");
  return res.json();
}

export interface UserProfile {
  id: number;
  first_name: string;
  last_name: string;
  email_address: string;
  preferences: Preferences | null;
}

// GET /user/profile — current user's info + preferences (token)
export async function getProfile(): Promise<UserProfile> {
  const res = await fetch(`${BASE_URL}/user/profile`, {
    headers: { ...authHeaders() },
  });
  if (!res.ok) throw new Error("Failed to load profile");
  return res.json();
}

// PUT /user/profile — update name only (token)
export async function updateProfileName(name: {
  first_name: string;
  last_name: string;
}): Promise<UserProfile> {
  const res = await fetch(`${BASE_URL}/user/profile`, {
    method: "PUT",
    headers: { "Content-Type": "application/json", ...authHeaders() },
    body: JSON.stringify(name),
  });
  if (!res.ok) throw new Error("Failed to update profile");
  return res.json();
}

// POST /user/preferences
export async function saveMatch(
  jobID: number,
  applicantID: number,
): Promise<void> {
  const res = await fetch(
    `${BASE_URL}/job/match/${jobID}/applicant/${applicantID}`,
    {
      method: "POST",
      headers: { "Content-Type": "application/json", ...authHeaders() },
    },
  );
  if (!res.ok) throw new Error("Failed to save preferences");
}
