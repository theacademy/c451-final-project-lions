"use client";
import { ApplicantMatchInfo } from "@/src/types/types";
import CheckIcon from "@/public/check.svg";
import Image from "next/image";
import { getJobMatchForApplicant } from "@/src/lib/api";
import { useEffect, useState } from "react";

export function ApplicantMatchCard(props: {
  jobID: number;
  applicantID: number;
  applyButton: boolean;
  applyURL?: string;
}) {
  const [match, setMatch] = useState(0);
  const [skills, setSkills] = useState<string[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  // TODO: Track job for applicant when clicking on apply

  // Get match info from the job ID and the applicant ID on page load
  useEffect(() => {
    const controller = new AbortController();

    const fetchMatch = async () => {
      if (!props.jobID || !props.applicantID) return;
      setLoading(true);
      setError("");
      getJobMatchForApplicant(props.jobID, props.applicantID)
        .then((jobInfo) => {
          setMatch(jobInfo?.matchPercent ?? 0);

          // Convert string to array
          const s: string[] = jobInfo?.skillsCsv?.split(",") ?? [];

          s.forEach((element) => {
            setSkills((skills) => [...skills, element]);
          });
        })
        .catch(() => setError("Couldn't load this job."))
        .finally(() => setLoading(false));
    };

    fetchMatch();
    return () => controller.abort();
  }, []);

  // Redirect when applying
  const applyToJob = () => {
    if (props.applyURL != null) {
      // signal to backend
    }
  };

  return (
    <div className="card bg-base-100 shadow-sm p-2 w-full">
      <div className="card-body justify-center gap-2 text-center items-center">
        {match >= 75 && (
          <Image src={CheckIcon} alt="Check Icon" width={32} height={32} />
        )}
        <h2 className="text-lg text-primary font-bold ">{match}% match</h2>

        <p className="flex-none capitalize">
          {skills
            .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
            .join(", ")}
        </p>

        {props.applyButton && (
          <a
            href={props.applyURL}
            target="_blank"
            rel="noopener noreferrer"
            className="btn btn-primary btn-block"
          >
            <button className="btn btn-primary btn-block" onClick={applyToJob}>
              Apply now
            </button>{" "}
          </a>
        )}
      </div>
    </div>
  );
}
