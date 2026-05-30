"use client";
import { useState } from "react";

interface loginInfo {
  email: string;
  password: string;
}

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (e: any) => {
    e.preventDefault();

    console.log("Clicked on login");
  };

  return (
    <div className="flex h-screen items-center justify-center">
      <div className="card w-96 bg-base-100 card-md shadow-sm">
        <div className="card-body">
          <form onSubmit={handleSubmit} className="flex flex-col">
            <label htmlFor="email">Email</label>
            <input
              type="text"
              placeholder="Type here"
              className="input"
              id="email"
            />

            <label htmlFor="password">Password</label>
            <input
              type="text"
              placeholder="Type here"
              className="input"
              id="password"
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
