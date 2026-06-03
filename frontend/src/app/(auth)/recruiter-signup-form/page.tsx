"use client";

import { useState } from "react";
import Link from "next/link";
import { redirect } from "next/navigation";

const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
  e.preventDefault();
};

export default function RecruiterSignUp() {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();

    const form = e.currentTarget;
    const formData = new FormData(form);

    console.log(Object.fromEntries(formData));
  };

  const checkKeyDown = (e: React.KeyboardEvent<HTMLFormElement>) => {
    if (e.key === "Enter" && e.currentTarget.tagName !== "TEXTAREA") {
      e.preventDefault();
      console.log("Prevent form submission");
    }
  };

  return (
    <main className="flex flex-col grow max-w-3/4 mx-auto p-6 gap-6 justify-center items-center">
      <h1 className="text-2xl font-bold">
        Before getting started, tell us a bit about yourself.
      </h1>
      <div className="flex items-center justify-center">
        <div className="card w-96 bg-base-100 card-md shadow-sm">
          <div className="card-body">
            <form
              onSubmit={handleSubmit}
              onKeyDown={checkKeyDown}
              className="flex flex-col gap-3"
            >
              <label htmlFor="firstName">
                First name <span className="text-primary">*</span>
              </label>
              <input
                type="text"
                placeholder="Type here"
                className="input"
                id="firstName"
                name="firstName"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                required
              />

              <label htmlFor="lastName">
                Last name <span className="text-primary">*</span>
              </label>
              <input
                type="text"
                placeholder="Type here"
                className="input"
                id="lastName"
                name="lastName"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                required
              />

              <label htmlFor="email">
                Email <span className="text-primary">*</span>
              </label>
              <input
                type="email"
                placeholder="Type here"
                className="input"
                id="email"
                name="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
              <label htmlFor="password">
                Password <span className="text-primary">*</span>
              </label>
              <input
                type="password"
                placeholder="Type here"
                className="input"
                id="password"
                name="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              {error && <p className="text-error text-sm mt-2">{error}</p>}

              <label htmlFor="company">Company</label>
              <input
                type="text"
                placeholder="Add Company"
                className="input"
                id="company"
              />

              <Link href="/recruiter-dashboard">
                <button className="btn btn-neutral" type="submit">
                  Continue
                </button>{" "}
              </Link>
            </form>
          </div>
        </div>
      </div>
    </main>
  );
}
