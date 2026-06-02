import Image from "next/image";

interface JobInfo {
  domain?: string;
  alt?: string;
  company: string;
  role: string;
  YoE?: string;
  location?: string;
  workType?: string;
  salary?: string;
  key?: number;
}

// Get the API publishable key
const KEY = process.env.NEXT_PUBLIC_LOGO_DEV_PUBLISHABLE_KEY;

export function JobCard(props: JobInfo) {
  return (
    <>
      <div className="card bg-base-100 w-96 shadow-sm p-2">
        <figure className="justify-start p-5">
          <Image
            src={`https://img.logo.dev/${props.domain}?token=${KEY}&fallback=404`}
            alt={`${props.domain} logo`}
            width={128}
            height={128}
          />
        </figure>
        <div className="card-body">
          <h3 className="text-md">{props.company}</h3>
          <h2 className="card-title">{props.role}</h2>
          <p>
            {props.YoE && (
              <>
                <span className="text-primary">{props.YoE}</span>
                <br />
              </>
            )}
            {props.location}
            {props.workType ? ` · ${props.workType}` : ""}
            {props.salary && (
              <>
                <br />
                {props.salary}
              </>
            )}
          </p>
        </div>
      </div>
    </>
  );
}
