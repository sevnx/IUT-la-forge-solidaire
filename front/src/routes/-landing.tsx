import GuestNav from '@/components/navbars/GuestNav';
import { LandingHeroHeader } from '@/components/elements/landing/LandingHeroHeader';
import { LandingKeyFeatures } from '@/components/elements/landing/LandingKeyFeatures';
import { LandingAbout } from '@/components/elements/landing/LandingAbout';
import { LandingCTA } from '@/components/elements/landing/LandingCTA';

export function LandingComponent() {
  return (
    <>
      <GuestNav />
      <div className="min-h-screen bg-gradient-to-br from-red-50 via-white to-orange-50">
        <div className="flex flex-col items-center">
          <LandingHeroHeader />
          <LandingKeyFeatures />
          <LandingAbout />
          <LandingCTA />
        </div>
      </div>
    </>
  );
}
