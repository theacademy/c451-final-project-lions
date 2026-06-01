import { RecruiterJobPost } from "@/src/components/recruiter-job-post";

interface RecruiterJobInfo {
  role: string;
  posted: string;
  numOfApplicants: number;
  key?: number;
}

const placeholder: RecruiterJobInfo = {
  role: "Junior Java Developper",
  posted: "Posted 3 days ago",
  numOfApplicants: 54,
};

const placeholders: RecruiterJobInfo[] = Array(6).fill(placeholder);

let i = 0;
for (const job of placeholders) {
  job.key = i;
  i++;
}

export default function RecruiterDashboard() {
  return (
    <main className="flex flex-col grow mx-auto p-6 gap-6">
      {placeholders.map((placeholder, index) => (
        <RecruiterJobPost {...placeholder} key={index} />
      ))}
    </main>
  );
}
