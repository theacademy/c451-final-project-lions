import Link from "next/link";

export default function SignUp() {
  return (
    <div className="flex h-screen items-center justify-center">
      <div className="card w-96 bg-base-100 card-md shadow-sm">
        <div className="card-body">
          <Link href="/user-signup-form" className="btn btn-primary">
            I am an applicant
          </Link>

          <Link href="/recruiter-signup-form" className="btn btn-neutral">
            I am a recruiter
          </Link>
        </div>
      </div>
    </div>
  );
}
