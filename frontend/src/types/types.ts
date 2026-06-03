export interface JobInfo {
  jobID: number;
  companyID: string;
  roleTitle: string;
  location?: string;
  descriptionHTML?: string;
  description?: string;
  url?: string;
  seniorityLevel: string;
  skills?: string[];
  posted?: Date;
  isActive: boolean;
  lastSeen: Date;
  createdAt: Date;

  // Needs to be added to database
  YoE: string;
  workLocationType: string;
  salary?: string;
  jobType: string;

  //  This information is to retrieve the logo for the company
  domain?: string;
  alt?: string;
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

export interface ApplicantMatchInfo {
  applicantID: number;
  jobID: number;
  matchPercent: number;
  skillsCsv?: string;
}
