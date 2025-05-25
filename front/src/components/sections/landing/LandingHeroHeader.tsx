import { Handshake } from 'lucide-react';

export function LandingHeroHeader() {
  return (
    <section className="relative w-full py-12 md:py-16 flex justify-center">
      <div className="absolute inset-0 flex items-center justify-center">
        <Handshake className="w-50 h-50 text-red-950 opacity-20 pointer-events-none" />
      </div>
      <div className="relative z-10 container text-center space-y-8">
        <h1 className="text-5xl md:text-6xl lg:text-7xl font-bold text-red-700">La Forge Solidaire</h1>
        <p className="text-lg md:text-xl text-muted-foreground max-w-2xl mx-auto">
          La plateforme de partage d'outils basé sur le confiance
        </p>
      </div>
    </section>
  );
}
