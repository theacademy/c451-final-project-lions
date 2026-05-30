"use client";

import Link from "next/link";
const handleSubmit = async (e: any) => {
  e.preventDefault();
};

export default function UserSignUp() {
  return (
    <>
      <h1 className="text-4xl font-bold">
        Before getting started, tell us a bit about yourself.
      </h1>
      <div className="flex h-screen items-center justify-center">
        <div className="card w-96 bg-base-100 card-md shadow-sm">
          <div className="card-body">
            <form onSubmit={handleSubmit} className="flex flex-col">
              <label htmlFor="tech-skills">Company</label>
              <input
                type="text"
                placeholder="Add skill"
                className="input"
                id="tech-skills"
              />

              <label htmlFor="soft-skills">Company</label>
              <input
                type="text"
                placeholder="Add skill"
                className="input"
                id="soft-skills"
              />

              <label htmlFor="locations">Company</label>
              <input
                type="text"
                placeholder="Add location"
                className="input"
                id="locations"
              />

              <legend className="fieldset-legend">Preferred work style</legend>
              <label className="label">
                <input type="checkbox" className="checkbox" />
                In-person
              </label>

              <label className="label">
                <input type="checkbox" className="checkbox" />
                Remote
              </label>

              <label className="label">
                <input type="checkbox" className="checkbox" />
                Hybrid
              </label>

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
