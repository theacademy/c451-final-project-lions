import Link from "next/link";
import Image from "next/image";
import Logo from "@/public/lion-logo.png";
import "@/src/app/(main)/global.css";

export function Header() {
  return (
    <>
      <div className="navbar bg-base-100 shadow-sm">
        <div className="flex-1">
          <Image src={Logo} alt="Lion logo" width={32} height={32} />
        </div>
        <div className="flex-none">
          <ul className="menu menu-horizontal px-1">
            <li>
              <Link href="/applicant-jobs-board">Jobs Board</Link>
            </li>
            <li>
              <Link href="/applicant-recommended">Recommended</Link>
            </li>
            <li>
              <Link href="/applicant-dashboard">Dashboard</Link>
            </li>
            <li>
              <Link href="/">Log out</Link>
            </li>
          </ul>
        </div>
      </div>
    </>
  );
}
