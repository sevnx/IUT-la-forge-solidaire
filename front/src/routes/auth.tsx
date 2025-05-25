import { AuthState } from '@/context/AuthContext';
import { AuthRedirector } from '@/context/AuthRedirector';
import { createFileRoute, Outlet } from '@tanstack/react-router';

export const Route = createFileRoute('/auth')({
  component: RouteComponent,
});

function RouteComponent() {
  return (
    <AuthRedirector expectedState={AuthState.LoggedOut} fallbackRoute="/">
      <Outlet />
    </AuthRedirector>
  );
}
