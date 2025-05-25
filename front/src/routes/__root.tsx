import { Footer } from '@/components/core/Footer';
import { ApiContext } from '@/context/ApiContext';
import { AuthProvider } from '@/context/AuthContext';
import { createRootRoute, Outlet } from '@tanstack/react-router';
import { TanStackRouterDevtools } from '@tanstack/react-router-devtools';

export const Route = createRootRoute({
  component: () => (
    <AuthProvider>
      <ApiContext>
        <Outlet />
        <TanStackRouterDevtools />
        <Footer />
      </ApiContext>
    </AuthProvider>
  ),
});
