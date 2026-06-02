import { getToken } from "@/src/lib/auth";

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

// GET /user/preferences — null when none exist yet (HTTP 204) => first login
export async function getAllJobs(): Promise<Preferences | null> {
  const res = await fetch(`${BASE_URL}/job/Job`, {
    headers: { ...authHeaders() },
  });
  if (res.status === 204) return null;
  if (!res.ok) throw new Error("Failed to load preferences");
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
