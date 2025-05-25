import { AuthState } from '@/context/AuthContext';
import { AuthRedirector } from '@/context/AuthRedirector';
import { createFileRoute, Outlet } from '@tanstack/react-router';

export const Route = createFileRoute('/auth')({
  component: RouteComponent,
});

function RouteComponent() {
  return (
    <AuthRedirector expectedState={AuthState.LoggedOut} fallbackRoute="/">
      <div className="flex flex-col items-center justify-center w-full h-screen bg-muted">
        <div className="bg-white rounded-lg shadow-lg p-8 w-full max-w-lg">
          <div className="flex justify-center mb-6">
            <img src="/logo-icon.png" alt="logo" className="w-12 h-12" />
          </div>
          <Outlet />
        </div>
      </div>
    </AuthRedirector>
  );
}
