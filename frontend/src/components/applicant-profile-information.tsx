"use client";
import { getProfile, updateProfileName, savePreferences } from "../lib/api";
import { useState, useEffect, ChangeEvent } from "react";

export function ApplicantProfileInformation() {
  const [editInfo, setEditInfo] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [saving, setSaving] = useState(false);
  const [message, setMessage] = useState("");

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

  // Backend may send these as a CSV string OR a JSON array (skills_csv is a List<String>).
  const toList = (val?: string | string[] | null): string[] => {
    if (!val) return [];
    const arr = Array.isArray(val) ? val : val.split(",");
    return arr.map((s) => s.trim()).filter(Boolean);
  };

  useEffect(() => {
    const fetchUserInfo = async () => {
      setLoading(true);
      setError("");
      try {
        const profile = await getProfile();
        setFirstName(profile.first_name ?? "");
        setLastName(profile.last_name ?? "");
        setEmail(profile.email_address ?? "");

        const pref = profile.preferences;
        setDesiredRole(pref?.desired_role ?? "");
        setYearsExperience(pref?.years_experience ?? 0);
        setJobType(pref?.job_type ?? "");
        setAllTechSkills(toList(pref?.skills_csv));
        setAllLocations(toList(pref?.desired_location));
        setRemotePreference(toList(pref?.remote_preference));
      } catch (err) {
        setError("Couldn't load your profile.");
      } finally {
        setLoading(false);
      }
    };

    fetchUserInfo();
  }, []);

  const addTechSkill = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      if (techSkill != "") {
        setAllTechSkills((allTechSkills) => [...allTechSkills, techSkill]);
        setTechSkill("");
      }
    }
  };

  const deleteFromTechSkills = (item: string) => {
    setAllTechSkills(allTechSkills.filter((s) => s != item));
  };

  const addLocation = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      if (location != "") {
        setAllLocations((allLocations) => [...allLocations, location]);
        setLocation("");
      }
    }
  };

  const deleteFromLocations = (item: string) => {
    setAllLocations(allLocations.filter((s) => s != item));
  };

  const handleCheckboxChange = (
    event: ChangeEvent<HTMLInputElement>,
    item: string,
  ) => {
    if (!event.target.checked) {
      setRemotePreference((remotePreference) =>
        remotePreference.filter((s) => s !== item),
      );
    } else {
      setRemotePreference((remotePreference) => [...remotePreference, item]);
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setMessage("");
    setSaving(true);
    try {
      await updateProfileName({ first_name: firstName, last_name: lastName });
      await savePreferences({
        skills_csv: allTechSkills.join(","),
        desired_location: allLocations.join(","),
        remote_preference: remotePreference.join(","),
        desired_role: desiredRole || undefined,
        job_type: jobType || undefined,
        years_experience: yearsExperience,
      });
      setEditInfo(false);
      setMessage("Profile updated.");
    } catch (err) {
      setMessage("Couldn't save your changes. Please try again.");
    } finally {
      setSaving(false);
    }
  };

  const checkKeyDown = (e: React.KeyboardEvent<HTMLFormElement>) => {
    if (e.key === "Enter" && e.currentTarget.tagName !== "TEXTAREA") {
      e.preventDefault();
    }
  };

  if (loading) return <div className="p-6">Loading…</div>;
  if (error) return <div className="p-6 text-error">{error}</div>;

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
              type="button"
              className="btn btn-active"
              onClick={() => setEditInfo(!editInfo)}
            >
              {editInfo ? "Cancel" : "Edit"}
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

          {/* Email — read-only */}
          <div>
            <label htmlFor="email" className="font-bold">
              Email
            </label>
            <div>{email}</div>
          </div>

          {/* Desired role */}
          <div>
            <label htmlFor="desired-role" className="font-bold">
              Desired Role
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

          {/* Technical skills */}
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
                    className="pl-2 cursor-pointer"
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
                    className="pl-2 cursor-pointer"
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
                  type="number"
                  min="0"
                  className="input"
                  id="years-experience"
                  name="years-experience"
                  value={yearsExperience}
                  onChange={(e) =>
                    setYearsExperience(parseInt(e.target.value) || 0)
                  }
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

          {/* Work style */}
          <div>
            <label htmlFor="work-type" className="font-bold">
              Work style
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

          {message && <p className="text-sm">{message}</p>}

          {editInfo && (
            <button
              className="btn btn-primary btn-block"
              type="submit"
              disabled={saving}
            >
              {saving ? "Saving…" : "Update"}
            </button>
          )}
        </form>
      </div>
    </div>
  );
}
