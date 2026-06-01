import Image from "next/image";

interface JobInfo {
  domain?: string;
  alt?: string;
  company: string;
  role: string;
  YoE: string;
  location: string;
  workType: string;
  salary: string;
  jobType: string;
  key?: number;
  jobDescription: string;
  skills: string[];
  match: number;
}

const props: JobInfo = {
  domain: "morganstanley.com",
  alt: "Morgan Stanley",
  company: "Morgan Stanley",
  role: "Junior Java Developper",
  YoE: "3 years of experience",
  location: "Toronto, Canada",
  workType: "Hybrid",
  salary: "60k",
  jobType: "Full Time",
  match: 80,
  skills: ["Python", "Java", "HTML"],
  jobDescription: `We're seeking someone to join our Institutional Securities Technology E-trading team as a Principal Java Developer in FICFX to code, deliver software, and work closely with our clients on both sales and trading. The role will include a combination of long-term strategic development and shorter-term business focused development on the fixed-income trading platform. Morgan Stanley traders use this platform to trade Fixed Income Securitized products like Fixed Income Agency Debt, TBAs, Pools, and CMOs.

In the Technology division, we leverage innovation to build the connections and capabilities that power our Firm, enabling our clients and colleagues to redefine markets and shape the future of our communities. This is a Software Engineering position at Vice-President level, which is part of the job family responsible for developing and maintaining software solutions that support business needs.

Since 1935, Morgan Stanley is known as a global leader in financial services, always evolving and innovating to better serve our clients and our communities in more than 40 countries around the world.

Interested in joining a team that’s eager to create, innovate and make an impact on the world? Read on…

What you'll do in the role:
• Deliver increased automation to the trading desk
• Expand electronic trading capabilities by creating and expanding Algos.
• Take full ownership of projects from requirement gathering to roll-out.
• Lead the development of new ideas and/or policies in own area.
• Analyze multiple sets of information to create summaries for various stakeholders.

What you'll bring to the role:
• At least 7+ years of Java working experience in the industry.
• Development experience in object-oriented programming and working with mixture of new and legacy systems.
• Basic understanding of Fixed Income products is required and ability to communicate with traders.
• Good problem-solving, result-focused, can-do attitude, and strong focus to keep the business running and competitive.
• Ability to work in a team.
• Demonstrate some ability to work with latest AI agents.
• Good knowledge of microservices architecture and SQL/Relational Databases.
`,
};

// Get the API publishable key
const KEY = process.env.NEXT_PUBLIC_LOGO_DEV_PUBLISHABLE_KEY;

export default function ApplicantJobPage(jobId: number) {
  // TODO: get information from database

  return (
    <>
      <main className="flex flex-col grow max-w-3/4 mx-auto p-6 gap-6">
        <div className="card bg-base-100 shadow-sm p-2">
          <div className="card-body p-10">
            <div className="flex flex-row justify-between">
              <div>
                <figure className="justify-start p-5">
                  <Image
                    src={`https://img.logo.dev/${props.domain}?token=${KEY}&fallback=404`}
                    alt={`${props.domain} logo`}
                    width={128}
                    height={128}
                  />
                </figure>
                <h3 className="text-md">{props.company}</h3>
                <h2 className="card-title">{props.role}</h2>
                <p>
                  <span className="text-primary">{props.YoE}</span>
                  <br></br>
                  {props.location}-{props.workType}
                  <br></br>
                  {props.salary}
                  <br></br>
                  {props.jobType}
                  <br></br>
                </p>
              </div>
              <div className="card bg-base-100 w-96 shadow-sm p-2">
                <div className="card-body justify-center gap-2 text-center">
                  <h2 className="text-lg text-primary font-bold ">
                    {props.match}% match
                  </h2>
                  <p className="flex-none">
                    {props.skills.map((item, index) => (
                      <span key={index}> {item}, </span>
                    ))}
                  </p>
                  <button className="btn btn-primary btn-block">
                    Apply now
                  </button>
                </div>
              </div>
            </div>
            <div className="pt-6">
              <h3 className="text-lg font-bold">Role Description</h3>
              <p>{props.jobDescription}</p>
            </div>
          </div>
        </div>
      </main>
    </>
  );
}
