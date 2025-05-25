import GuestNav from '@/components/navbars/GuestNav';
import { HowItWorksSection } from '@/components/sections/how-it-works/HowItWorks';

export function LandingComponent() {
  return (
    <>
      <GuestNav />
      <div className="flex flex-col items-center">
        <HowItWorksSection />
      </div>
    </>
  );
}
