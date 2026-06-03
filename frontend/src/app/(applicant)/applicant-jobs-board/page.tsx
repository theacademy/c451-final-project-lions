"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { JobCard } from "@/src/components/job-card";
import { getBoardJobs, BoardJob } from "@/src/lib/api";

export default function ApplicantJobBoard() {
  const [jobs, setJobs] = useState<BoardJob[]>([]);
  const [role, setRole] = useState("");
  const [location, setLocation] = useState("");
  const [seniority, setSeniority] = useState("");
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    let active = true;
    setLoading(true);
    setError("");
    getBoardJobs({
      role: role || undefined,
      location: location || undefined,
      seniority: seniority || undefined,
      page,
    })
      .then((data) => {
        if (active) setJobs(data);
      })
      .catch(() => {
        if (active) setError("Couldn't load jobs. Please try again.");
      })
      .finally(() => {
        if (active) setLoading(false);
      });
    return () => {
      active = false;
    };
  }, [role, location, seniority, page]);

  return (
    <main className="flex flex-col grow mx-auto p-6 gap-6 w-full max-w-6xl">
      <div>
        <h2 className="text-md">Jobs</h2>
        <p>Let your next role find you.</p>
      </div>

      {/* Search + filters */}
      <div className="flex gap-4 flex-wrap">
        <input
          type="text"
          placeholder="Search by role or title"
          className="input grow"
          value={role}
          onChange={(e) => {
            setPage(0);
            setRole(e.target.value);
          }}
        />
        <input
          type="text"
          placeholder="Filter by location"
          className="input"
          value={location}
          onChange={(e) => {
            setPage(0);
            setLocation(e.target.value);
          }}
        />
        <select
          className="select"
          value={seniority}
          onChange={(e) => {
            setPage(0);
            setSeniority(e.target.value);
          }}
        >
          <option value="">All levels</option>
          <option value="junior">Junior</option>
          <option value="mid">Mid</option>
          <option value="senior">Senior</option>
        </select>
      </div>

      {error && <p className="text-error text-sm">{error}</p>}
      {loading ? (
        <p className="text-center text-lg">Loading…</p>
      ) : jobs.length === 0 ? (
        <p>No jobs match your filters.</p>
      ) : (
        <div className="grid grid-cols-3 justify-center gap-6 items-stretch">
          {jobs.map((job) => (
            <Link href={`/applicant-jobs-board/${job.id}`} key={job.id}>
              <JobCard
                company={job.companyName ?? "Company"}
                role={job.title}
                location={job.location ?? ""}
                YoE={job.seniorityLevel ?? ""}
                domain={
                  job.companyName
                    ? job.companyName.toLowerCase().replace(/\s+/g, "") + ".com"
                    : undefined
                }
              />
            </Link>
          ))}
        </div>
      )}

      <div className="join justify-center">
        <button
          className="join-item btn"
          disabled={page === 0}
          onClick={() => setPage((p) => Math.max(0, p - 1))}
        >
          Previous page
        </button>
        <button className="join-item btn btn-active">{page + 1}</button>
        <button
          className="join-item btn"
          disabled={jobs.length < 20}
          onClick={() => setPage((p) => p + 1)}
        >
          Next
        </button>
      </div>
    </main>
  );
}
