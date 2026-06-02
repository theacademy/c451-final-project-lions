"use client";
import Image from "next/image";
import { JobInfo } from "../types/types";
import { useEffect, useState } from "react";

interface JobInfo {
  domain?: string;
  alt?: string;
  company: string;
  role: string;
  YoE?: string;
  location?: string;
  workType?: string;
  salary?: string;
  key?: number;
}

// Get the API publishable key
const KEY = process.env.NEXT_PUBLIC_LOGO_DEV_PUBLISHABLE_KEY;
const URL = process.env.NEXT_PUBLIC_API_BASE_URL + "/job/Job";

export function JobCard(props: JobInfo) {
  const [company, setCompany] = useState("");

  // Get the company name from the company ID
  useEffect(() => {
    const fetchCompanyIDforJob = async () => {
      try {
        const response = await fetch(URL);
        if (!response.ok) throw new Error("Network response was not ok");

        const jsonData = await response.json();
        console.log("data:", jsonData);
      } catch (err) {
        console.log("There was a problem loading the data");
      }
    };

    fetchCompanyIDforJob();
  }, []);

  return (
    <>
      <div className="card bg-base-100 w-96 shadow-sm p-2 h-full">
        <figure className="justify-start p-5">
          <Image
            src={`https://img.logo.dev/name/${props.companyName}?token=${KEY}&fallback=404`}
            alt={`${props.companyName} logo`}
            width={128}
            height={128}
          />
        </figure>
        <div className="card-body">
          <h3 className="text-md">{company}</h3>
          <h2 className="card-title">{props.title}</h2>
          <p>
            {props.YoE && (
              <>
                <span className="text-primary">{props.YoE}</span>
                <br />
              </>
            )}
            {props.location}
            {props.workType ? ` · ${props.workType}` : ""}
            {props.salary && (
              <>
                <br />
                {props.salary}
              </>
            )}
          </p>
        </div>
      </div>
    </>
  );
}
