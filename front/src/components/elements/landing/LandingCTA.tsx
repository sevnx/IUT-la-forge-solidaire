import { Button } from '@/components/ui/button';
import { useNavigate } from '@tanstack/react-router';

export function LandingCTA() {
  const navigate = useNavigate();

  return (
    <section className="w-full py-12 bg-gradient-to-br from-red-600 to-orange-600">
      <div className="container mx-auto px-4 text-center">
        <h2 className="text-3xl font-bold text-white mb-4">Prêt à rejoindre la communauté ?</h2>
        <p className="text-lg text-red-100 mb-6 max-w-xl mx-auto">
          Commencez dès aujourd'hui à partager et emprunter des outils.
        </p>
        <Button
          size="lg"
          onClick={() => navigate({ to: '/auth/register' })}
          className="bg-white text-red-600 hover:bg-gray-100"
        >
          Créer mon compte gratuitement
        </Button>
      </div>
    </section>
  );
}
