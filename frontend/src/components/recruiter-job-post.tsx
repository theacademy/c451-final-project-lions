interface RecruiterJobInfo {
  role: string;
  posted: string;
  numOfApplicants: number;
}

export function RecruiterJobPost(props: RecruiterJobInfo) {
  return (
    <>
      <div className="card bg-base-100 w-96 shadow-sm p-2">
        <div className="card-body">
          <h2 className="card-title">{props.role}</h2>
          <p>
            {props.posted}
            <br></br>
            <span className="text-primary">
              {props.numOfApplicants} applicants
            </span>
            <br></br>
          </p>
        </div>
      </div>
    </>
  );
}
