import type { Tool } from '@/api/tools/tools';
import { ToolCard } from './ToolCard';

export function ToolList() {
  const tools: Tool[] = [
    {
      id: '1',
      name: 'Perceuse électrique',
      description: 'Perceuse sans fil 18V avec mandrin auto-serrant',
      imageSrc: 'https://placehold.co/600x400',
      availableAt: '2025-01-15T00:00:00.000Z',
      ownerAddress: '23 Rue de la Paix, Paris',
    },
    {
      id: '2',
      name: 'Scie circulaire',
      description: 'Scie circulaire portative 1200W avec guide laser',
      imageSrc: 'https://placehold.co/600x400',
      ownerAddress: '45 Avenue des Champs, Lyon',
    },
    {
      id: '3',
      name: 'Marteau-piqueur',
      description: 'Marteau-piqueur électrique 1500W pour démolition',
      imageSrc: 'https://placehold.co/600x400',
      availableAt: '2025-02-10T00:00:00.000Z',
      ownerAddress: '12 Boulevard Saint-Michel, Marseille',
    },
    {
      id: '4',
      name: 'Ponceuse orbitale',
      description: "Ponceuse orbitale 350W avec système d'aspiration",
      imageSrc: 'https://placehold.co/600x400',
      ownerAddress: '78 Rue de Rivoli, Toulouse',
    },
  ];

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-10">
      {tools.map((tool) => (
        <ToolCard key={tool.id} tool={tool} />
      ))}
    </div>
  );
}
