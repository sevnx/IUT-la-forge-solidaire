import { Footer } from '@/components/core/Footer';
import { Toaster } from '@/components/ui/sonner';
import { ApiContext } from '@/context/ApiContext';
import { AuthProvider } from '@/context/AuthContext';
import { createRootRoute, Outlet } from '@tanstack/react-router';
import { TanStackRouterDevtools } from '@tanstack/react-router-devtools';

export const Route = createRootRoute({
  component: () => (
    <AuthProvider>
      <ApiContext>
        <div className="flex flex-col min-h-screen">
          <Outlet />
          <TanStackRouterDevtools />
          <Footer />
        </div>
      </ApiContext>
      <Toaster />
    </AuthProvider>
  ),
});
