import { UserTools } from '@/components/elements/user-tools/UserTools';
import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/user/tools')({
  component: RouteComponent,
});

function RouteComponent() {
  return (
    <UserTools
      tools={[
        {
          id: '1',
          name: 'Scie circulaire',
          description: 'Scie circulaire portative 1200W avec guide laser',
          imageSrc: 'https://placehold.co/600x400',
          ownerAddress: '45 Avenue des Champs, Lyon',
          availableAt: '2025-01-01T00:00:00.000Z',
        },
      ]}
    />
  );
}
