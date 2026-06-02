import { ApplicantInfo } from "@/src/types/types";
import CheckIcon from "@/public/check.svg";
import Image from "next/image";
import { redirect } from "next/navigation";

interface MatchProps {
  match: number;
  skills: string[];
  applyButton: boolean;
}

const matchinfo = {
  match: 80,
  skills: ["Python", "Java"],
  applyButton: true,
};

export function ApplicantMatchCard(props: {
  jobID: number;
  applicantID: number;
  applyButton: boolean;
  applyURL?: string;
}) {
  // TODO: Track job for applicant when clicking on apply

  // TODO: Get match info from the job ID and the applicant ID

  console.log(props.jobID, props.applicantID);

  // Redirect when applying
  const applyToJob = () => {
    if (props.applyURL != null) {
      redirect(props.applyURL);
    }
  };

  return (
    <div className="card bg-base-100 shadow-sm p-2 w-full">
      <div className="card-body justify-center gap-2 text-center items-center">
        {matchinfo.match >= 75 && (
          <Image src={CheckIcon} alt="Check Icon" width={32} height={32} />
        )}
        <h2 className="text-lg text-primary font-bold ">
          {matchinfo.match}% match
        </h2>
        <p className="flex-none">{matchinfo.skills.join(", ")}</p>

        {props.applyButton && (
          <button className="btn btn-primary btn-block" onClick={applyToJob}>
            Apply now
          </button>
        )}
      </div>
    </div>
  );
}
