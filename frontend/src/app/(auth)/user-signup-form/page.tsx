"use client";
import { useState } from "react";
import { redirect } from "next/navigation";
import { signup } from "@/src/lib/api";

interface technicalSkill {
  tskill: string;
  key: number;
}

interface softSkillType {
  sskill: string;
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

  const [softSkillCount, setSoftSkillCount] = useState(0);
  const [softSkill, setSoftSkill] = useState("");
  const [allSoftSkills, setAllSoftSkills] = useState<softSkillType[]>([]);

  const [locationCount, setLocationCount] = useState(0);
  const [location, setLocation] = useState("");
  const [allLocations, setAllLocations] = useState<locationType[]>([]);

  // const router = useRouter();
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  // Send data to database
  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);

    // Transforming each input into csv
    const techSkills = allTechSkills.map((item) => item.tskill).join(",");
    const softSkills = allSoftSkills.map((item) => item.sskill).join(",");
    const locations = allLocations.map((item) => item.loc).join(",");
    const workType = formData.getAll("work-type").join(",");

    console.log("sending form!");
    console.log("technical skills: ", techSkills);
    console.log("soft skills: ", softSkills);
    console.log("locations:", locations);
    console.log("preferred work type:", workType);

    // TODO: send to database
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
        // empty response body => duplicate email (or rejected input)
        setError("That email is already registered.");
        return;
      }
      redirect("/login");
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

  const addSoftSkill = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      // Update skills if it's not blank and if it's valid
      if (softSkill != "") {
        const newSkill: softSkillType = {
          sskill: softSkill,
          key: softSkillCount,
        };

        setSoftSkillCount(softSkillCount + 1);

        setAllSoftSkills([...allSoftSkills, newSkill]);
        setSoftSkill("");
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

  const deleteFromSoftSkills = (key: number) => {
    console.log("trying to delete: ", key);

    setAllSoftSkills(allSoftSkills.filter((s) => s.key != key));
    console.log(allSoftSkills);
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

              <label htmlFor="soft-skills">
                Soft skills <span className="text-primary">*</span>
              </label>
              <div className="flex gap-3 flex-wrap">
                {allSoftSkills.map((item) => (
                  <span key={item.key} className="border rounded-2xl p-2 px-4">
                    {item.sskill}
                    <span
                      onClick={() => deleteFromSoftSkills(item.key)}
                      className="pl-2"
                    >
                      &times;
                    </span>
                  </span>
                ))}
              </div>
              <input
                type="text"
                placeholder="Type here"
                className="input"
                id="soft-skills"
                name="soft-skills"
                onKeyDown={addSoftSkill}
                value={softSkill}
                onChange={(e) => setSoftSkill(e.target.value)}
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
              <fieldset>
                {/* <legend className="fieldset-legend">
                  Preferred work style <span className="text-primary">*</span>
                </legend> */}
                <div className="flex flex-col gap-3">
                  <label htmlFor="work-type">
                    Preferred work style(s)
                    <span className="text-primary">*</span>
                  </label>{" "}
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
