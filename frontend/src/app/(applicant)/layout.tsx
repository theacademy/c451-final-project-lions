import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import { Header } from "@/src/components/applicant-header";
import { Footer } from "@/src/components/footer";
import "@/src/app/(main)/global.css";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "LionsJobs",
  description: "Job board",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html
      lang="en"
      className={`${geistSans.variable} ${geistMono.variable} h-full antialiased`}
      data-theme="light"
    >
      <body className="h-full flex flex-col ">
        <Header />
        <div className="flex flex-col items-center justify-center">
          {children}
        </div>
        <Footer />
      </body>
    </html>
  );
}
