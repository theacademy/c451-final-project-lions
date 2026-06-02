"use client";
import { JobCard } from "@/src/components/job-card";
import Link from "next/link";
import React, { useState, useEffect } from "react";
import { JobInfo } from "@/src/types/types";

const URL = process.env.NEXT_PUBLIC_API_BASE_URL + "/job/Job";

export default function ApplicantJobBoard() {
  const [page, setPage] = useState(1);
  const [jobs, setJobs] = useState<JobInfo[]>([]);

  useEffect(() => {
    const fetchPageData = async () => {
      try {
        const response = await fetch(URL);
        if (!response.ok) throw new Error("Network response was not ok");

        const jsonData = await response.json();
        setJobs(jsonData);
        jobs.map((item) => console.log(item.id));
      } catch (err) {
        console.log("There was a problem loading the data");
      }
    };

    fetchPageData();
  }, []);

  return (
    <main className="flex flex-col grow  mx-auto p-6 gap-6">
      <div>
        <h2 className="text-md">Jobs</h2>
        <p>Let your next role find you.</p>
      </div>
      {/* Job cards start here */}
      <div className="grid grid-cols-3 gap-10 auto-rows-fr">
        {/* TODO: change the key to be the actual job id KEY */}
        {jobs.map((item) => (
          <Link href={`/applicant-jobs-board/job-${item.id}`} key={item.id}>
            <JobCard {...item} />
          </Link>
        ))}
      </div>
      <div className="join justify-center">
        <button className="join-item btn">Previous page</button>
        <button className="join-item btn btn-active">1</button>
        <button className="join-item btn">2</button>
        <button className="join-item btn">3</button>
        <button className="join-item btn">4</button>
        <button className="join-item btn">Next</button>
      </div>
    </main>
  );
}
