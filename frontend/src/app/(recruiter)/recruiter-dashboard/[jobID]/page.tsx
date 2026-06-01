import { ApplicantProfileCard } from "@/src/components/applicant-profile-card";

interface ApplicantInfo {
  firstName: string;
  lastName: string;
  qualifications: string[];
  match: number;
  skills: string[];
  userID: number;
}

const placeholder1: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  userID: 0,
};

const placeholder2: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  userID: 1,
};

const placeholder3: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  userID: 2,
};

const placeholder4: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  userID: 3,
};

const placeholder5: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  userID: 4,
};

const placeholder6: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  userID: 5,
};
const placeholders: ApplicantInfo[] = [
  placeholder1,
  placeholder2,
  placeholder3,
  placeholder4,
  placeholder5,
  placeholder6,
];

export default function RecruiterApplicants() {
  // TODO: Get from the database the applicants related to the job ID
  return (
    <main className="flex flex-col grow mx-auto p-6 gap-6">
      {placeholders.map((item) => (
        <ApplicantProfileCard {...item} key={`applicant-${item.userID}`} />
      ))}
    </main>
  );
}
