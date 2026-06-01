import { JobCard } from "@/src/components/job-card";

interface JobInfo {
  domain?: string;
  alt?: string;
  company: string;
  role: string;
  YoE: string;
  location: string;
  workType: string;
  salary: string;
  key?: number;
}

const placeholder: JobInfo = {
  domain: "morganstanley.com",
  company: "Morgan Stanley",
  role: "Junior Java Developper",
  YoE: "3 years of experience",
  location: "Toronto, Canada",
  workType: "Hybrid",
  salary: "60k",
};

const placeholders: JobInfo[] = Array(6).fill(placeholder);

let i = 0;
for (const job of placeholders) {
  job.key = i;
  i++;
}

export default function ApplicantJobBoard() {
  return (
    <>
      <main className="flex flex-col grow mx-auto p-6 gap-6">
        <div>
          <h2 className="text-md">Jobs</h2>
          <p>Let your next role find you.</p>
        </div>
        {/* Job cards start here */}
        <div className="grid grid-cols-3 gap-10 ">
          {/* TODO: change the key to be the actual job id KEY */}
          {placeholders.map((placeholder, index) => (
            <JobCard {...placeholder} key={index} />
          ))}
        </div>
        <div className="join justify-center">
          <button className="join-item btn">Previous page</button>
          <button className="join-item btn btn-active">1</button>
          <button className="join-item btn">2</button>
          <button className="join-item btn">3</button>
          <button className="join-item btn">4</button>
          <button className="join-item btn">Next</button>
        </div>
      </main>
    </>
  );
}
