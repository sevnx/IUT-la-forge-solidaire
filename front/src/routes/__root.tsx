import { Footer } from '@/components/core/Footer';
import { Toaster } from '@/components/ui/sonner';
import { ApiContext } from '@/context/ApiContext';
import { AuthProvider } from '@/context/AuthContext';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { createRootRoute, Outlet } from '@tanstack/react-router';
import { TanStackRouterDevtools } from '@tanstack/react-router-devtools';

const queryClient = new QueryClient();

export const Route = createRootRoute({
  component: () => (
    <AuthProvider>
      <ApiContext>
        <QueryClientProvider client={queryClient}>
          <div className="flex flex-col min-h-screen">
            <Outlet />
            <TanStackRouterDevtools />
            <Footer />
          </div>
        </QueryClientProvider>
      </ApiContext>
      <Toaster />
    </AuthProvider>
  ),
});
