import { ApplicantInfo } from "@/src/types/types";

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
}) {
  // TODO: Track job for applicant when clicking on apply

  // TODO: Get match info from the job ID and the applicant ID

  console.log(props.jobID, props.applicantID);

  return (
    <div className="card bg-base-100 w-96 shadow-sm p-2">
      <div className="card-body justify-center gap-2 text-center">
        <h2 className="text-lg text-primary font-bold ">
          {matchinfo.match}% match
        </h2>
        <p className="flex-none">
          {matchinfo.skills.map((item, index) => (
            <span key={index}> {item}, </span>
          ))}
        </p>

        {matchinfo.applyButton && (
          <button className="btn btn-primary btn-block">Apply now</button>
        )}
      </div>
    </div>
  );
}
