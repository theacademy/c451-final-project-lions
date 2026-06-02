"use client";
import { ApplicantMatchCard } from "@/src/components/match-card";
import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import Image from "next/image";
import { JobInfo } from "@/src/types/types";

// Get the API publishable key
import { getJobById, JobDetail } from "@/src/lib/api";

const KEY = process.env.NEXT_PUBLIC_LOGO_DEV_PUBLISHABLE_KEY;
const URL = process.env.NEXT_PUBLIC_API_BASE_URL;

// TODO: update with actual userID
const placeholderApplicantID = 1;

export default function ApplicantJobPage() {
  const params = useParams();
  const id = Array.isArray(params.id) ? params.id[0] : params.id;

  const [job, setJob] = useState<JobDetail | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!id) return;
    setLoading(true);
    setError("");
    getJobById(id)
      .then((j) => setJob(j))
      .catch(() => setError("Couldn't load this job."))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <main className="grow p-6">Loading…</main>;
  if (error || !job)
    return <main className="grow p-6">{error || "Job not found."}</main>;

  const domain = job.companyName
    ? job.companyName.toLowerCase().replace(/\s+/g, "") + ".com"
    : undefined;

  return (
    <main className="flex flex-col grow max-w-3/4 mx-auto p-6 gap-6">
      <div className="card bg-base-100 shadow-sm p-2">
        <div className="card-body p-10">
          <div className="flex flex-row justify-between gap-6">
            <div>
              {domain && (
                <figure className="justify-start p-5">
                  <Image
                    src={`https://img.logo.dev/${domain}?token=${KEY}&fallback=404`}
                    alt={`${job.companyName} logo`}
                    width={128}
                    height={128}
                  />
                </figure>
              )}
              <h3 className="text-md">{job.companyName ?? "Company"}</h3>
              <h2 className="card-title">{job.title}</h2>
              <p>
                {job.seniorityLevel && (
                  <>
                    <span className="text-primary">{job.seniorityLevel}</span>
                    <br />
                  </>
                )}
                {job.location}
              </p>
            </div>

            <div className="card bg-base-100 w-96 shadow-sm p-2 h-fit">
              <div className="card-body justify-center gap-2 text-center">
                {job.absoluteUrl ? (
                  <a
                    href={job.absoluteUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="btn btn-primary btn-block"
                  >
                    Apply now
                  </a>
                ) : (
                  <button className="btn btn-primary btn-block" disabled>
                    Apply now
                  </button>
                )}
              </div>
            </div>
          </div>

          {job.descriptionHtml && (
            <div className="pt-6">
              <h3 className="text-lg font-bold">Role Description</h3>
              <div
                className="max-w-none [&_ul]:list-disc [&_ul]:pl-6 [&_ol]:list-decimal [&_ol]:pl-6 [&_p]:mb-3 [&_li]:mb-1 [&_strong]:font-bold [&_a]:text-primary [&_a]:underline"
                dangerouslySetInnerHTML={{ __html: job.descriptionHtml }}
              />
            </div>
          )}
        </div>
      </div>
    </main>
  );
}
