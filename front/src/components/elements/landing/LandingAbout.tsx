import { Check, ListOrdered, MessageCircleQuestion, Search, Wrench } from 'lucide-react';
import { StepsList, type Step } from './LandingStepsList';

export function LandingAbout() {
  const steps: Step[] = [
    {
      icon: ListOrdered,
      title: 'Inscription',
      description: 'Créez votre compte en quelques minutes avec un identifiant et un mot de passe.',
    },
    {
      icon: Search,
      title: 'Trouver',
      description: 'Recherchez les outils disponibles dans votre région.',
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
    <section className="w-full py-20 bg-gradient-to-br from-gray-50 to-red-50">
      <div className="container mx-auto px-4">
        <div className="grid items-center gap-12 lg:grid-cols-2 max-w-6xl mx-auto">
          <div className="space-y-8">
            <div>
              <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-6">Des étapes simples pour commencer</h2>
              <p className="text-lg text-gray-600 mb-8">
                Rejoignez notre communauté en quelques clics et commencez à partager dès aujourd'hui.
              </p>
            </div>

            <div className="space-y-6">
              <StepsList steps={steps} />
            </div>
          </div>

          <div className="relative">
            <div className="absolute inset-0 bg-gradient-to-r from-red-500 to-orange-500 rounded-3xl transform rotate-3"></div>
            <img
              src="/community-tools-sharing.png"
              alt="Personnes partageant des outils dans une communauté locale"
              width={600}
              height={500}
              className="relative z-10 w-full rounded-3xl shadow-2xl object-cover"
            />
          </div>
        </div>
      </div>
    </section>
  );
}
