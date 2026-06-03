import { ApplicantMatchCard } from "@/src/components/match-card";

interface ApplicantInfo {
  firstName: string;
  lastName: string;
  qualifications: string[];
  match: number;
  skills: string[];
  userID: number;
  jobID: number;
  email: string;
}

export function ApplicantProfileCard(props: ApplicantInfo) {
  // Pass jobID and applicantID
  const applicantID = props.userID;
  const jobID = props.jobID;

  return (
    <div className="card bg-base-100 shadow-sm p-2 grow w-lg">
      <div className="card-body flex-row justify-between gap-3">
        <div className="flex flex-col gap-3">
          <h2 className="card-title">
            {props.firstName} {props.lastName}
          </h2>

          <ul className="flex flex-col gap-2">
            {props.qualifications.map((item, index) => (
              <li key={index}>&#10003; {item}</li>
            ))}
          </ul>
        </div>
        <div className="min-w-1/2">
          <ApplicantMatchCard
            jobID={jobID}
            applicantID={applicantID}
            applyButton={false}
          />
        </div>
      </div>
      <a href={`mailto:${props.email}`}>
        <button className="btn btn-primary btn-block">Reach out</button>
      </a>{" "}
    </div>
  );
}
