import { Card, type CardProps } from '@/components/core/Card';
import { useRouter } from '@tanstack/react-router';
import { Handshake, SearchIcon, WrenchIcon } from 'lucide-react';

export function Dashboard() {
  const router = useRouter();

  const pages: CardProps[] = [
    {
      icon: SearchIcon,
      title: 'Rechercher',
      description: 'Recherchez des outils',
      onClick: () => {
        router.navigate({ to: '/search' });
      },
    },
    {
      icon: WrenchIcon,
      title: 'Mes outils',
      description: 'Gérez vos outils',
      onClick: () => {
        router.navigate({ to: '/user/tools' });
      },
    },
    {
      icon: Handshake,
      title: 'Mes emprunts',
      description: 'Gérez vos emprunts et retours',
      onClick: () => {
        router.navigate({ to: '/user/borrows' });
      },
    },
  ];

  return (
    <div className="flex-grow flex items-center justify-center px-4 md:px-16 lg:px-32 bg-gradient-to-br from-slate-50 to-slate-100">
      <div className="flex flex-row gap-4 justify-center items-center mx-auto w-full max-w-6xl">
        {pages.map((page) => (
          <Card key={page.title} {...page} className="flex-1" />
        ))}
      </div>
    </div>
  );
}
