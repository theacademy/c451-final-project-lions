"use client";
import { getPreferences } from "../lib/api";
import { ApplicantInfo } from "../types/types";
import { useState, useEffect, ChangeEvent } from "react";

export function ApplicantProfileInformation() {
  const [editInfo, setEditInfo] = useState(false);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [desiredRole, setDesiredRole] = useState("");

  const [techSkill, setTechSkill] = useState("");
  const [allTechSkills, setAllTechSkills] = useState<string[]>([]);

  const [location, setLocation] = useState("");
  const [allLocations, setAllLocations] = useState<string[]>([]);

  const [yearsExperience, setYearsExperience] = useState(0);
  const [jobType, setJobType] = useState("");

  const [remotePreference, setRemotePreference] = useState<string[]>([]);
  const [inPerson, setInPerson] = useState(false);
  const [isHybrid, setHybrid] = useState(false);
  const [isRemote, setRemote] = useState(false);

  // placeholder
  const applicant: ApplicantInfo = {
    applicantID: 1,
    email: "zoedoe@gmail.com",
    firstName: "Zoe",
    lastName: "Doe",
    desiredRole: "Software Engineer",
    skills: ["python", "java"],
    locations: ["Canada", "USA"],
    yearsExperience: 5,
    jobType: "full-time",
    remotePreference: ["remote", "hybrid"],
  };

  useEffect(() => {
    const fetchUserInfo = () => {
      try {
        // TODO: Get user info from database
        // const response = await fetch("");
        // if (!response.ok) throw new Error("There was a problem finding user");
        // const data = await response.json();

        // Set information from the data
        // you can just re-define applicant here after doing the information fetch (erase the placeholder)

        // Assign user info as default state
        setFirstName(applicant.firstName);
        setLastName(applicant.lastName);
        setEmail(applicant.email);
        setDesiredRole(applicant.desiredRole ?? "");
        setYearsExperience(applicant.yearsExperience ?? 0);
        setJobType(applicant.jobType ?? "");

        applicant.skills?.forEach((element) => {
          setAllTechSkills((allTechSkills) => [...allTechSkills, element]);
        });

        applicant.locations?.forEach((element) => {
          setAllLocations((allLocations) => [...allLocations, element]);
        });

        applicant.remotePreference?.forEach((element) => {
          setRemotePreference((remotePreference) => [
            ...remotePreference,
            element,
          ]);
        });
      } catch (err) {
        console.log("Something bad happened :(");
      }
    };

    fetchUserInfo();
  }, []);

  const addTechSkill = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      // Update skills if it's not blank and if it's valid
      if (techSkill != "") {
        setAllTechSkills((allTechSkills) => [...allTechSkills, techSkill]);
        setTechSkill("");
      }
    }
  };

  const deleteFromTechSkills = (item: string) => {
    console.log("trying to delete: ", item);

    setAllTechSkills(allTechSkills.filter((s) => s != item));
    console.log(allTechSkills);
  };

  const addLocation = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      // Update skills if it's not blank and if it's valid
      if (location != "") {
        setAllLocations((allLocations) => [...allLocations, location]);
        setLocation("");
      }
    }
  };

  const deleteFromLocations = (item: string) => {
    console.log("trying to delete: ", item);

    setAllLocations(allLocations.filter((s) => s != item));
    console.log(allLocations);
  };

  const handleCheckboxChange = (
    event: ChangeEvent<HTMLInputElement>,
    item: string,
  ) => {
    // Check if the checkbox was checked
    // if checkbox is check add to preferences
    if (!event.target.checked) {
      // Use filter to create a new array without the target item
      setRemotePreference((remotePreference) =>
        remotePreference.filter((s) => s !== item),
      );
    }

    // if checkbox is unchecked, remove from preferences
    else {
      setRemotePreference((remotePreference) => [...remotePreference, item]);
    }

    console.log(remotePreference);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
  };

  const checkKeyDown = (e: React.KeyboardEvent<HTMLFormElement>) => {
    if (e.key === "Enter" && e.currentTarget.tagName !== "TEXTAREA") {
      e.preventDefault();
      console.log("Prevent form submission");
    }
  };
  return (
    <div className="card bg-base-100 shadow-sm p-2 grow w-lg">
      <div className="card-body flex-col justify-between gap-3">
        <form
          onSubmit={handleSubmit}
          onKeyDown={checkKeyDown}
          className="flex flex-col gap-3"
        >
          <div className="flex flex-row justify-between align-center">
            <h2 className="card-title">User information</h2>
            <button
              className="btn btn-active"
              onClick={() => {
                setEditInfo(!editInfo);
              }}
            >
              Edit
            </button>
          </div>

          {/* first name */}
          <div>
            <label htmlFor="firstName" className="font-bold">
              First name <span className="text-primary">*</span>
            </label>
            <div>
              {!editInfo && firstName} <br></br>
              {editInfo && (
                <input
                  type="text"
                  className="input"
                  id="firstName"
                  name="firstName"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  required
                />
              )}
            </div>
          </div>

          {/* last name */}
          <div>
            <label htmlFor="lastName" className="font-bold">
              Last name <span className="text-primary">*</span>
            </label>
            <div>
              {!editInfo && lastName} <br></br>
              {editInfo && (
                <input
                  type="text"
                  className="input"
                  id="lastName"
                  name="lastName"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  required
                />
              )}
            </div>
          </div>

          {/* Email */}
          <div>
            <label htmlFor="email" className="font-bold">
              Email <span className="text-primary">*</span>
            </label>
            <div>
              {!editInfo && email} <br></br>
              {editInfo && (
                <input
                  type="text"
                  className="input"
                  id="email"
                  name="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              )}
            </div>
          </div>

          {/* Desired role */}
          <div>
            <label htmlFor="desired-role" className="font-bold">
              Desired Role <span className="text-primary">*</span>
            </label>
            <div>
              {!editInfo && desiredRole} <br></br>
              {editInfo && (
                <input
                  type="text"
                  className="input"
                  id="desired-role"
                  name="desired-role"
                  value={desiredRole}
                  onChange={(e) => setDesiredRole(e.target.value)}
                />
              )}
            </div>
          </div>

          {/* User tech preferences */}
          <label htmlFor="tech-skills" className="font-bold">
            Technical skills
          </label>
          <div className="flex gap-3 flex-wrap">
            {allTechSkills.map((item) => (
              <span
                key={item}
                className="border rounded-2xl p-2 px-4 capitalize"
              >
                {item}

                {editInfo && (
                  <span
                    onClick={() => deleteFromTechSkills(item)}
                    className="pl-2"
                  >
                    &times;
                  </span>
                )}
              </span>
            ))}
          </div>

          {editInfo && (
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
          )}

          {/* Preferred location */}
          <label htmlFor="locations" className="font-bold">
            Preferred location(s)
          </label>
          <div className="flex gap-3 flex-wrap">
            {allLocations.map((item) => (
              <span
                key={item}
                className="border rounded-2xl p-2 px-4 capitalize"
              >
                {item}

                {editInfo && (
                  <span
                    onClick={() => deleteFromLocations(item)}
                    className="pl-2"
                  >
                    &times;
                  </span>
                )}
              </span>
            ))}
          </div>

          {editInfo && (
            <input
              type="text"
              placeholder="Add Location"
              className="input"
              id="locations"
              name="locations"
              onKeyDown={addLocation}
              value={location}
              onChange={(e) => setLocation(e.target.value)}
            />
          )}

          {/* Years of Experience */}
          <div>
            <label htmlFor="years-experience" className="font-bold">
              Years of Experience
            </label>
            <div>
              {!editInfo && (
                <span>{yearsExperience} year(s) of experience</span>
              )}
              <br></br>
              {editInfo && (
                <input
                  type="text"
                  className="input"
                  id="years-experience"
                  name="years-experience"
                  value={yearsExperience}
                  onChange={(e) => setYearsExperience(parseInt(e.target.value))}
                />
              )}
            </div>
          </div>

          {/* Job Type */}
          <div>
            <label htmlFor="job-type" className="font-bold">
              Job Type
            </label>
            <div>
              {!editInfo && <span className="capitalize"> {jobType}</span>}
              <br></br>
              {editInfo && (
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
              )}
            </div>
          </div>

          {/* Remote preference */}
          <div>
            <label htmlFor="work-type" className="font-bold">
              Job Type
            </label>
            <div>
              {!editInfo && (
                <span className="capitalize">
                  {remotePreference.join(", ")}
                </span>
              )}
              <br></br>
              {editInfo && (
                <fieldset>
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
                        defaultChecked={remotePreference.includes("in-person")}
                        onChange={(e) =>
                          handleCheckboxChange(e, e.currentTarget.value)
                        }
                      />
                      In-person
                    </label>
                    <label className="label">
                      <input
                        type="checkbox"
                        className="checkbox"
                        name="work-type"
                        value="remote"
                        defaultChecked={remotePreference.includes("remote")}
                        onChange={(e) =>
                          handleCheckboxChange(e, e.currentTarget.value)
                        }
                      />
                      Remote
                    </label>
                    <label className="label">
                      <input
                        type="checkbox"
                        className="checkbox"
                        name="work-type"
                        value="hybrid"
                        defaultChecked={remotePreference.includes("hybrid")}
                        onChange={(e) =>
                          handleCheckboxChange(e, e.currentTarget.value)
                        }
                      />
                      Hybrid
                    </label>
                  </div>
                </fieldset>
              )}
            </div>
          </div>

          <button className="btn btn-primary btn-block" type="submit">
            Update
          </button>
        </form>
      </div>
    </div>
  );
}
