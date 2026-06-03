interface ApplicantInfo {
  firstName: string;
  lastName: string;
  qualifications: string[];
  match: number;
  skills: string[];
  userID: number;
  email: string;
}

export function ApplicantProfileCard(props: ApplicantInfo) {
  return (
    <div className="card bg-base-100 shadow-sm p-2 grow">
      <div className="card-body">
        <h2 className="card-title">
          {props.firstName} {props.lastName}
        </h2>
        {props.qualifications.map((item, index) => (
          <p key={index}>
            <span>&#10003; {item}</span>
          </p>
        ))}

        <a href={`mailto:${props.email}`}>
          <button className="btn btn-primary btn-block">Reach out</button>
        </a>
      </div>
    </div>
  );
}
