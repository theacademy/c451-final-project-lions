interface ApplicantInfo {
  firstName: string;
  lastName: string;
  qualifications: string[];
  match: number;
  skills: string[];
  userID: number;
}

export function ApplicantProfileCard(props: ApplicantInfo) {
  return (
    <>
      <div className="card bg-base-100 w-96 shadow-sm p-2">
        <div className="card-body">
          <h2 className="card-title">
            {props.firstName} {props.lastName}
          </h2>
          {props.qualifications.map((item) => (
            <p key={props.userID}>
              <span>&#10003; {item}</span>
            </p>
          ))}

          <button className="btn btn-primary btn-block">Reach out</button>
        </div>
      </div>
    </>
  );
}
