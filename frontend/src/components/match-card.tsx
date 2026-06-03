"use client";
import { ApplicantMatchInfo } from "@/src/types/types";
import { getProfile, saveMatch } from "../lib/api";
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
  const [allTechSkills, setAllTechSkills] = useState<string[]>([]);
  const [matchedSkills, setMatchedSkills] = useState<string[]>([]);

  // TODO: Track job for applicant when clicking on apply

  // Backend may send these as a CSV string OR a JSON array (skills_csv is a List<String>).
  const toList = (val?: string | string[] | null): string[] => {
    if (!val) return [];
    const arr = Array.isArray(val) ? val : val.split(",");
    return arr.map((s) => s.trim()).filter(Boolean);
  };

  // Get match info from the job ID and the applicant ID on page load
  useEffect(() => {
    const controller = new AbortController();

    const fetchMatchPercent = async () => {
      if (!props.jobID || !props.applicantID) return;
      setLoading(true);
      setError("");
      getJobMatchForApplicant(props.jobID, props.applicantID, controller)
        .then((jobInfo) => {
          setMatch(jobInfo?.matchPercent ?? 0);

          // Convert string to array
          const s: string[] = jobInfo?.skillsCsv?.split(",") ?? [];

          s.forEach((element) => {
            if (element != "") {
              setSkills((skills) => [...skills, element]);
            }
          });

          setSkills((skills) => skills.map((item) => item.toLowerCase()));
        })
        .catch(() => setError("Couldn't load this job."))
        .finally(() => setLoading(false));
    };

    const fetchUserInfo = async () => {
      setLoading(true);
      setError("");
      try {
        const profile = await getProfile();

        const pref = profile.preferences;
        setAllTechSkills(toList(pref?.skills_csv));
        setAllTechSkills((allTechSkills) =>
          allTechSkills.map((item) => item.toLowerCase()),
        );

        console.log("my skills: ", allTechSkills);
      } catch (err) {
        setError("Couldn't load your profile.");
      } finally {
        setLoading(false);
      }
    };

    fetchMatchPercent();
    fetchUserInfo();
    console.log(skills, allTechSkills);

    return () => controller.abort();
  }, []);

  useEffect(() => {
    const findMatchingSkills = async () => {
      skills.forEach((element) => {
        console.log(element, allTechSkills.includes(element));

        if (allTechSkills.includes(element)) {
          setMatchedSkills((matchedSkills) => [...matchedSkills, element]);
        }
      });
    };
    findMatchingSkills();
  }, [skills, allTechSkills]);

  // Redirect when applying
  const applyToJob = async () => {
    if (props.applyURL != null) {
      // signal to backend

      try {
        const res = await saveMatch(props.jobID, props.applicantID);
      } catch {
        setError("Something went wrong. Please try again.");
      }
      console.log("added match to db");
    }
  };

  return (
    <div className="card bg-base-100 shadow-sm p-2 w-full">
      <div className="card-body justify-center gap-2 text-center items-center">
        {match >= 75 && (
          <Image src={CheckIcon} alt="Check Icon" width={32} height={32} />
        )}
        <h2 className="text-lg text-primary font-bold ">{match}% match</h2>
        <p className="flex-none text-primary capitalize text-pretty">
          {match > 0 && <span>Match: </span>}
          {matchedSkills
            .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
            .join(", ")}
        </p>
        <p className="flex-none capitalize text-accent text-pretty">
          {match < 100 && skills.length > 0 && <span> Missing: </span>}
          {skills
            .filter((item) => !matchedSkills.includes(item))
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
            </button>
          </a>
        )}
      </div>
    </div>
  );
}
