import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/user/tools')({
  component: RouteComponent,
});

function RouteComponent() {
  return <div>Hello "/user/tools"!</div>;
}
