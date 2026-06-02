export interface JobInfo {
  id: number;
  absoluteUrl: string;
  active: boolean;
  companyId: number;
  createdAt: Date | null;
  descriptionText: string;
  greenhouseJobId: number;
  lastSeenAt: Date | null;
  location: string;
  postedAt: Date | null;
  seniorityLevel: string;
  skillsCsv: string;
  title: string;

  // Needs to be added to database
  YoE?: string;
  workLocationType?: string;
  salary?: string;
  jobType?: string; // full-time, part-time, etc

  // This information is to retrieve the logo for the company
  companyName?: string;
}

export interface ApplicantInfo {
  applicantID: number;
  email: string;
  firstName: string;
  lastName: string;
}

export interface ApplicantPreferences {
  yearsExperience?: number;
  skills?: string[];
  locations?: string[];
  remotePreference?: string[];
  jobType?: string;
  updated: Date;
}
