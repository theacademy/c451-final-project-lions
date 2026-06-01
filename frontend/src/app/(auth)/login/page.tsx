"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { login, getPreferences } from "@/src/lib/api";
import { setToken } from "@/src/lib/auth";

export default function Login() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setSubmitting(true);
    try {
      const token = await login({ email_address: email, password });
      if (!token) {
        setError("Invalid email or password.");
        return;
      }
      setToken(token);

      // First login (no prefs yet) => onboarding; otherwise straight to jobs.
      const prefs = await getPreferences();
      router.push(prefs ? "/applicant-jobs-board" : "/preference-form");
    } catch {
      setError("Something went wrong. Please try again.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="flex h-screen items-center justify-center">
      <div className="card w-96 bg-base-100 card-md shadow-sm">
        <div className="card-body">
          <form onSubmit={handleSubmit} className="flex flex-col">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              placeholder="Type here"
              className="input"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />

            <label htmlFor="password">Password</label>
            <input
              type="password"
              placeholder="Type here"
              className="input"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />

            {error && <p className="text-error text-sm mt-2">{error}</p>}

            <button
              className="btn btn-neutral mt-2"
              type="submit"
              disabled={submitting}
            >
              {submitting ? "Signing in..." : "Login"}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
