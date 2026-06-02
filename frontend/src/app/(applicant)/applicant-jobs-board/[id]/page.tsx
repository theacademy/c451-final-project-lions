"use client";
import { ApplicantMatchCard } from "@/src/components/match-card";
import Image from "next/image";
import { JobInfo } from "@/src/types/types";
import { useEffect, useState } from "react";
import { useParams } from "next/navigation";

// Get the API publishable key
const KEY = process.env.NEXT_PUBLIC_LOGO_DEV_PUBLISHABLE_KEY;
const URL = process.env.NEXT_PUBLIC_API_BASE_URL;

// TODO: update with actual userID
const placeholderApplicantID = 1;

export default function ApplicantJobPage() {
  // Get job id from route
  const params = useParams<{ id: string }>();
  const jobID = params.id.replace(/\D/g, "");

  console.log("job id is: ", jobID);
  const [job, setJob] = useState<JobInfo>({
    id: 0,
    absoluteUrl: "",
    active: false,
    companyId: 0,
    createdAt: null,
    descriptionText: "",
    greenhouseJobId: 0,
    lastSeenAt: null,
    location: "",
    postedAt: null,
    seniorityLevel: "",
    skillsCsv: "",
    title: "",

    // Needs to be added to database
    YoE: "",
    workLocationType: "",
    salary: "",
    jobType: "",

    // This information is to retrieve the logo for the company
    companyName: "",
  });

  // Get Job information from Job ID
  useEffect(() => {
    const fetchPageData = async () => {
      try {
        const response = await fetch(`${URL}/job/${jobID}`);
        if (!response.ok) throw new Error("Network response was not ok");

        const jsonData = await response.json();
        setJob(jsonData);

        console.log(job);
      } catch (err) {
        console.log("There was a problem loading the data");
      }
    };

    fetchPageData();
  }, []);

  return (
    <main className="flex flex-col grow max-w-3/4 mx-auto p-6 gap-6">
      <div className="card bg-base-100 shadow-sm p-2">
        <div className="card-body p-10">
          <div className="flex flex-row justify-between">
            <div>
              <figure className="justify-start p-5">
                <Image
                  src={`https://img.logo.dev/name/${job.companyName}?token=${KEY}&fallback=404`}
                  alt={`${job.companyName} logo`}
                  width={128}
                  height={128}
                />
              </figure>
              <h3 className="text-md">{job.companyName}</h3>
              <h2 className="card-title">{job.title}</h2>
              <p>
                <span className="text-primary">{job.YoE}</span>
                <br></br>
                {job.location}
                {job.workLocationType != null && (
                  <span> - {job.workLocationType}</span>
                )}
                <br></br>
                {job.salary}
                <br></br>
                {job.jobType}
                <br></br>
              </p>
            </div>
            <ApplicantMatchCard
              jobID={job.id}
              applicantID={placeholderApplicantID}
              applyButton={true}
              applyURL={job.absoluteUrl}
            />
          </div>
          <div className="pt-6">
            <h3 className="text-lg font-bold">Role Description</h3>
            <p>{job.descriptionText}</p>
          </div>
        </div>
      </div>
    </main>
  );
}
