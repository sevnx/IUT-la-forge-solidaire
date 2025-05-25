import { Check, ListOrdered, MessageCircleQuestion, Search, Wrench } from 'lucide-react';
import { StepsList, type Step } from './StepsList';

export function HowItWorksSection() {
  const steps: Step[] = [
    {
      icon: ListOrdered,
      title: 'Inscription',
      description: 'Créez votre compte en quelques minutes.',
    },
    {
      icon: Search,
      title: 'Trouver',
      description: 'Recherchez ce dont vous avez besoin.',
    },
    {
      icon: MessageCircleQuestion,
      title: 'Demander',
      description: "Réalisez des demandes d'emprunt pour les outils qui vous intéressent.",
    },
    {
      icon: Wrench,
      title: 'Prêter',
      description: 'Ajoutez vos outils pour les mettre à disposition de la communauté.',
    },
    {
      icon: Check,
      title: 'Emprunter',
      description: "Consultez et acceptez les demandes d'emprunt pour vos outils.",
    },
  ];

  return (
    <section className="w-full py-12 md:py-24 lg:py-32 bg-muted flex justify-center">
      <div className="container grid items-center gap-6 lg:grid-cols-2">
        <div className="space-y-4">
          <div className="inline-block rounded-lg bg-muted text-base">Comment ça marche ?</div>
          <h2 className="text-3xl font-bold tracking-tighter md:text-4xl/tight">Des étapes simples pour commencer</h2>
          <StepsList steps={steps} />
        </div>
        <img
          src="https://img.freepik.com/free-photo/closeup-shot-business-handshake-cropped-shot-two-people-wearing-formal-suits-shaking-hands-business-handshake-concept_1262-21017.jpg?semt=ais_hybrid&w=740"
          alt="Communauté partageant des outils"
          width={600}
          height={400}
          className="mx-auto aspect-video overflow-hidden rounded-xl object-cover object-center sm:w-full lg:order-last"
        />
      </div>
    </section>
  );
}
