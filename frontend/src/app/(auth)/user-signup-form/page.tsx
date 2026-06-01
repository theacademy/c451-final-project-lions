"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { signup } from "@/src/lib/api";

export default function UserSignUp() {
  const router = useRouter();
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setSubmitting(true);
    try {
      const created = await signup({
        first_name: firstName,
        last_name: lastName,
        email_address: email,
        password,
      });
      if (created === null) {
        // empty response body => duplicate email (or rejected input)
        setError("That email is already registered.");
        return;
      }
      router.push("/login");
    } catch {
      setError("Something went wrong. Please try again.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <>
      <h1 className="text-4xl font-bold">
        Join us today
      </h1>
      <div className="flex h-screen items-center justify-center">
        <div className="card w-96 bg-base-100 card-md shadow-sm">
          <div className="card-body">
            <form onSubmit={handleSubmit} className="flex flex-col">
              <label htmlFor="firstName">First name</label>
              <input
                type="text"
                placeholder="Type here"
                className="input"
                id="firstName"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                required
              />

              <label htmlFor="lastName">Last name</label>
              <input
                type="text"
                placeholder="Type here"
                className="input"
                id="lastName"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                required
              />

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
                {submitting ? "Creating..." : "Continue"}
              </button>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
