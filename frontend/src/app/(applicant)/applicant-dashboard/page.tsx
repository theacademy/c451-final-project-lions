import { ApplicantProfileInformation } from "@/src/components/applicant-profile-information";

export default function ApplicantDashboard() {
  return (
    <main className="flex flex-col grow mx-auto p-6 gap-6 w-full max-w-6xl justify-center items-center">
      <ApplicantProfileInformation />
    </main>
  );
}
