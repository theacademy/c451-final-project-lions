"use client";
import { useState } from "react";
import { useRouter } from "next/navigation"; // replace the `redirect` import
import { signup, login, savePreferences } from "@/src/lib/api";
import { setToken } from "@/src/lib/auth";

interface technicalSkill {
  tskill: string;
  key: number;
}

interface locationType {
  loc: string;
  key: number;
}

export default function UserSignUp() {
  // Keeps track of skills
  const [techSkillCount, setTechSkillCount] = useState(0);
  const [techSkill, setTechSkill] = useState("");
  const [allTechSkills, setAllTechSkills] = useState<technicalSkill[]>([]);

  // years experience
  const [yearsExperience, setYearsExperience] = useState("");

  // job type
  const [jobType, setJobType] = useState("");

  // desired role
  const [desiredRole, setDesiredRole] = useState("");

  const [locationCount, setLocationCount] = useState(0);
  const [location, setLocation] = useState("");
  const [allLocations, setAllLocations] = useState<locationType[]>([]);

  const router = useRouter();
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  // Send data to database
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);
    const skillsCsv = allTechSkills.map((s) => s.tskill).join(",");
    const locations = allLocations.map((l) => l.loc).join(",");
    const workType = formData.getAll("work-type").join(",");

    setError("");
    setSubmitting(true);
    try {
      const created = await signup({
        first_name: firstName,
        last_name: lastName,
        email_address: email,
        password,
      });
      if (created === null) {
        setError("That email is already registered.");
        return;
      }

      const token = await login({ email_address: email, password });
      if (!token) {
        setError("Account created, but sign-in failed — please log in.");
        router.push("/login");
        return;
      }
      setToken(token);

      await savePreferences({
        skills_csv: skillsCsv,
        desired_location: locations,
        desired_role: desiredRole || undefined,
        remote_preference: workType,
        years_experience: yearsExperience ? Number(yearsExperience) : 0,
        job_type: jobType || undefined,
      });

      router.push("/applicant-jobs-board");
    } catch {
      setError("Something went wrong. Please try again.");
    } finally {
      setSubmitting(false);
    }
  };

  const addTechSkill = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      // Update skills if it's not blank and if it's valid
      if (techSkill != "") {
        const newSkill: technicalSkill = {
          tskill: techSkill,
          key: techSkillCount,
        };

        setTechSkillCount(techSkillCount + 1);
        setAllTechSkills([...allTechSkills, newSkill]);
        setTechSkill("");
      }
    }
  };

  const addLocation = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      // Update skills if it's not blank and if it's valid
      if (location != "") {
        const newLocation: locationType = {
          loc: location,
          key: locationCount,
        };

        setLocationCount(locationCount + 1);

        setAllLocations([...allLocations, newLocation]);
        setLocation("");
      }
    }
  };

  const checkKeyDown = (e: React.KeyboardEvent<HTMLFormElement>) => {
    if (e.key === "Enter" && e.currentTarget.tagName !== "TEXTAREA") {
      e.preventDefault();
      console.log("Prevent form submission");
    }
  };

  const deleteFromTechSkills = (key: number) => {
    console.log("trying to delete: ", key);

    setAllTechSkills(allTechSkills.filter((s) => s.key != key));
    console.log(allTechSkills);
  };

  const deleteFromLocations = (key: number) => {
    console.log("trying to delete: ", key);

    setAllLocations(allLocations.filter((s) => s.key != key));
    console.log(allLocations);
  };

  return (
    <main className="flex flex-col grow max-w-3/4 mx-auto p-6 gap-6 justify-center items-center">
      <h1 className="text-3xl font-bold">Join us today</h1>
      <div className="flex items-center justify-center">
        <div className="card w-96 bg-base-100 card-md shadow-sm">
          <div className="card-body">
            <form
              onSubmit={handleSubmit}
              onKeyDown={checkKeyDown}
              className="flex flex-col gap-3"
            >
              {/* User basic information */}
              <label htmlFor="firstName">
                First name <span className="text-primary">*</span>
              </label>
              <input
                type="text"
                placeholder="Type here"
                className="input"
                id="firstName"
                name="firstName"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                required
              />

              <label htmlFor="lastName">
                Last name <span className="text-primary">*</span>
              </label>
              <input
                type="text"
                placeholder="Type here"
                className="input"
                id="lastName"
                name="lastName"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                required
              />

              <label htmlFor="email">
                Email <span className="text-primary">*</span>
              </label>
              <input
                type="email"
                placeholder="Type here"
                className="input"
                id="email"
                name="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
              <label htmlFor="password">
                Password <span className="text-primary">*</span>
              </label>
              <input
                type="password"
                placeholder="Type here"
                className="input"
                id="password"
                name="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              {error && <p className="text-error text-sm mt-2">{error}</p>}

              <label htmlFor="desired-role">Desired role</label>
              <input
                type="text"
                placeholder="e.g. Frontend Engineer"
                className="input"
                id="desired-role"
                value={desiredRole}
                onChange={(e) => setDesiredRole(e.target.value)}
              />

              {/* User tech preferences */}
              <label htmlFor="tech-skills">
                Technical skills <span className="text-primary">*</span>
              </label>
              <div className="flex gap-3 flex-wrap">
                {allTechSkills.map((item) => (
                  <span key={item.key} className="border rounded-2xl p-2 px-4">
                    {item.tskill}
                    <span
                      onClick={() => deleteFromTechSkills(item.key)}
                      className="pl-2"
                    >
                      &times;
                    </span>
                  </span>
                ))}
              </div>

              <input
                type="text"
                placeholder="Add skill"
                className="input"
                id="tech-skills"
                name="tech-skills"
                onKeyDown={addTechSkill}
                value={techSkill}
                onChange={(e) => setTechSkill(e.target.value)}
              />

              <label htmlFor="locations">
                Preferred location(s) <span className="text-primary">*</span>
              </label>
              <div className="flex gap-1 flex-wrap">
                {allLocations.map((item) => (
                  <span key={item.key} className="border rounded-2xl p-2 px-4">
                    {item.loc}
                    <span
                      onClick={() => deleteFromLocations(item.key)}
                      className="pl-2"
                    >
                      &times;
                    </span>
                  </span>
                ))}
              </div>

              <input
                type="text"
                placeholder="Add location"
                className="input"
                id="locations"
                name="locations"
                onKeyDown={addLocation}
                value={location}
                onChange={(e) => setLocation(e.target.value)}
              />

              <label htmlFor="years-experience">Years of experience</label>
              <input
                type="number"
                min="0"
                placeholder="0"
                className="input"
                id="years-experience"
                value={yearsExperience}
                onChange={(e) => setYearsExperience(e.target.value)}
              />

              <label htmlFor="job-type">Job type</label>
              <select
                id="job-type"
                className="select"
                value={jobType}
                onChange={(e) => setJobType(e.target.value)}
              >
                <option value="">Select…</option>
                <option value="full-time">Full-time</option>
                <option value="part-time">Part-time</option>
                <option value="internship">Internship</option>
                <option value="contract">Contract</option>
              </select>

              <fieldset>
                {/* <legend className="fieldset-legend">
                  Preferred work style <span className="text-primary">*</span>
                </legend> */}
                <div className="flex flex-col gap-3">
                  <label htmlFor="work-type">
                    Preferred work style(s)
                    <span className="text-primary">*</span>
                  </label>
                  <label className="label">
                    <input
                      type="checkbox"
                      className="checkbox"
                      name="work-type"
                      value="in-person"
                    />
                    In-person
                  </label>
                  <label className="label">
                    <input
                      type="checkbox"
                      className="checkbox"
                      name="work-type"
                      value="remote"
                    />
                    Remote
                  </label>
                  <label className="label">
                    <input
                      type="checkbox"
                      className="checkbox"
                      name="work-type"
                      value="hybrid"
                    />
                    Hybrid
                  </label>
                </div>
              </fieldset>

              <button
                className="btn btn-neutral mt-2"
                type="submit"
                disabled={submitting}
              >
                {submitting ? "Creating..." : "Continue"}
              </button>
            </form>
          </div>
        </div>
      </div>
    </main>
  );
}
