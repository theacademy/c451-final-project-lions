"use client";
import Link from "next/link";

const handleSubmit = async (e: any) => {
  e.preventDefault();
};

export default function RecruiterSignUp() {
  return (
    <>
      <h1 className="text-4xl font-bold">
        Before getting started, tell us a bit about yourself.
      </h1>
      <div className="flex h-screen items-center justify-center">
        <div className="card w-96 bg-base-100 card-md shadow-sm">
          <div className="card-body">
            <form onSubmit={handleSubmit} className="flex flex-col">
              <label htmlFor="company">Company</label>
              <input
                type="text"
                placeholder="Add Company"
                className="input"
                id="company"
              />

              <button className="btn btn-neutral" type="submit">
                Continue
              </button>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
