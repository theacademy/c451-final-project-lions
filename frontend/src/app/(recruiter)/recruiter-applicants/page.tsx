import { ApplicantProfileCard } from "@/src/components/applicant-profile-card";

interface ApplicantInfo {
  firstName: string;
  lastName: string;
  qualifications: string[];
  match: number;
  skills: string[];
  userID: number;
}

const placeholder: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  userID: 0,
};

const placeholders: ApplicantInfo[] = Array(6).fill(placeholder);

let i = 0;
for (const person of placeholders) {
  person.userID = i;
  i++;
}

export default function RecruiterApplicants(jobID: number) {
  // TODO: Get from the database the applicants related to the job ID

  return (
    <main className="flex flex-col grow mx-auto p-6 gap-6">
      {placeholders.map((placeholder, index) => (
        <ApplicantProfileCard {...placeholder} key={index} />
      ))}
    </main>
  );
}
