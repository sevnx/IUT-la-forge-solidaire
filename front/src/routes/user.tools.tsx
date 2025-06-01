import { getUserTools } from '@/api/tools/tools';
import { UserTools } from '@/components/elements/user-tools/UserTools';
import { neverthrowToError } from '@/lib/neverthrow';
import { useQuery } from '@tanstack/react-query';
import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/user/tools')({
  component: RouteComponent,
});

function RouteComponent() {
  const { data: tools } = useQuery({
    queryKey: ['tools'],
    queryFn: neverthrowToError(getUserTools),
  })

  if (tools === undefined) {
    return <div>Loading...</div>;
  }

  return (
    <UserTools
      tools={tools ?? []}
    />
  );
}
