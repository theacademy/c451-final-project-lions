"use client";
import { ApplicantProfileCard } from "@/src/components/applicant-profile-card";
import { useParams } from "next/navigation";

interface ApplicantInfo {
  firstName: string;
  lastName: string;
  qualifications: string[];
  match: number;
  skills: string[];
  userID: number;
  email: string;
}

const placeholder1: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  email: "johndoe@mail.com",
  userID: 0,
};

const placeholder2: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  email: "johndoe@mail.com",
  userID: 1,
};

const placeholder3: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  email: "johndoe@mail.com",
  userID: 2,
};

const placeholder4: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  email: "johndoe@mail.com",
  userID: 3,
};

const placeholder5: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  email: "johndoe@mail.com",
  userID: 4,
};

const placeholder6: ApplicantInfo = {
  firstName: "John",
  lastName: "Doe",
  qualifications: ["Bachelors of Science", "5 years of experience"],
  match: 80,
  skills: ["Python", "Java"],
  email: "johndoe@mail.com",
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
  const params = useParams();
  const jobID: number = Number(params.jobID!);

  return (
    <main className="flex flex-col grow max-w-3/4 mx-auto p-6 gap-6">
      {placeholders.map((item) => (
        <ApplicantProfileCard
          {...item}
          key={`applicant-${item.userID}`}
          jobID={jobID}
        />
      ))}
    </main>
  );
}
