import Image from "next/image";
import Link from "next/link";
import "@/src/app/(main)/global.css";

export default function Home() {
  return (
    <div>
      <main>
        <div className="hero bg-base-200 min-h-screen">
          <div className="hero-content text-center">
            <div className="max-w-md">
              <h1 className="text-5xl font-bold">LionsJobs</h1>
              <p className="py-6">Let jobs find you</p>
              <Link href="/signup" className="btn btn-primary">
                Get Started
              </Link>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
