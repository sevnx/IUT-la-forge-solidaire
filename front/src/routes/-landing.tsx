import GuestNav from '@/components/navbars/GuestNav';
import { LandingHeroHeader } from '@/components/sections/landing/LandingHeroHeader';
import { LandingAbout } from '@/components/sections/landing/LandingAbout';
import { LandingKeyFeatures } from '@/components/sections/landing/LandingKeyFeatures';

export function LandingComponent() {
  return (
    <>
      <GuestNav />
      <div className="flex flex-col items-center px-50">
        <LandingHeroHeader />
        <LandingKeyFeatures />
        <LandingAbout />
      </div>
    </>
  );
}
