"use client";
import React from "react";

import Link from "next/link";
import { useState } from "react";

interface loginInfo {
  email: string;
  password: string;
}

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();

    // TODO: Redirect user to applicant dashboard or recruiter dashboard based on their account type
  };

  return (
    <div className="flex h-screen items-center justify-center">
      <div className="card w-96 bg-base-100 card-md shadow-sm">
        <div className="card-body">
          <form onSubmit={handleSubmit} className="flex flex-col">
            <label htmlFor="email">Email</label>
            <input
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
                console.log(email);
              }}
              type="text"
              placeholder="Type here"
              className="input"
              id="email"
              required
            />

            <label htmlFor="password">Password</label>
            <input
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              type="password"
              placeholder="Type here"
              className="input"
              id="password"
              required
            />
            <button className="btn btn-neutral" type="submit">
              Login
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
