import UserNav from '@/components/navbars/UserNav';
import { AuthState } from '@/context/AuthContext';
import { AuthRedirector } from '@/context/AuthRedirector';
import { createFileRoute, Outlet } from '@tanstack/react-router';

export const Route = createFileRoute('/user')({
  component: RouteComponent,
});

function RouteComponent() {
  return (
    <AuthRedirector expectedState={AuthState.LoggedIn} fallbackRoute="/">
      <UserNav />
      <Outlet />
    </AuthRedirector>
  );
}
