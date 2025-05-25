import GuestNav from '@/components/navbars/GuestNav';
import UserNav from '@/components/navbars/UserNav';
import { AuthState, useAuth } from '@/context/AuthContext';
import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/search')({
  component: RouteComponent,
});

function RouteComponent() {
  const { state } = useAuth();

  if (state === AuthState.Loading) {
    return null;
  }
  return <>{state === AuthState.LoggedIn ? <UserNav /> : <GuestNav />}</>;
}
