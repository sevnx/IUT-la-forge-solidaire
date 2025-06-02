import { createFileRoute } from '@tanstack/react-router';
import { UserBorrows } from '@/components/elements/user-borrows/UserBorrows';

export const Route = createFileRoute('/user/borrows')({
  component: RouteComponent,
});

function RouteComponent() {
  return <UserBorrows />;
}
