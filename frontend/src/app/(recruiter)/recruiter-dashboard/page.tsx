import { RecruiterJobPost } from "@/src/components/recruiter-job-post";
import Link from "next/link";

interface RecruiterJobInfo {
  role: string;
  posted: string;
  numOfApplicants: number;
  jobID?: number;
}

const placeholder: RecruiterJobInfo = {
  role: "Junior Java Developper",
  posted: "Posted 3 days ago",
  numOfApplicants: 54,
};

const placeholders: RecruiterJobInfo[] = Array(6)
  .fill(null)
  .map((_, i) => ({
    ...placeholder,
    jobID: i,
  }));

export default function RecruiterDashboard() {
  placeholders.map((item) => console.log(item.jobID));

  return (
    <main className="flex flex-col grow mx-auto p-6 gap-6">
      {placeholders.map((item) => (
        <Link href={`/recruiter-dashboard/${item.jobID}`} key={item.jobID}>
          <RecruiterJobPost {...item} />
        </Link>
      ))}
    </main>
  );
}
