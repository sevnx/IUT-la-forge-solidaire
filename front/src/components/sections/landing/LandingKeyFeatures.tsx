import { Card, type CardProps } from '@/components/elements/Card';
import { PackageCheck, PackageOpen } from 'lucide-react';

export function LandingKeyFeatures() {
  const features: CardProps[] = [
    {
      icon: PackageOpen,
      title: 'Prêter',
      description: "Partager vos outils dont vous n'avez plus besoin.",
    },
    {
      icon: PackageCheck,
      title: 'Emprunter',
      description: 'Trouver les outils dont vous avez besoin.',
    },
  ];
  return (
    <section id="key-features" className="w-full flex justify-center py-6 md:py-12 lg:py-16">
      <div className="container grid gap-16 lg:grid-cols-2">
        {features.map((feature) => (
          <Card key={feature.title} {...feature} />
        ))}
      </div>
    </section>
  );
}
