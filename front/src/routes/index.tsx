import { AuthState, useAuth } from '@/context/AuthContext';
import { createFileRoute } from '@tanstack/react-router';
import { DashboardComponent } from './-dashboard';
import { LandingComponent } from './-landing';

export const Route = createFileRoute('/')({
  component: Index,
});

function Index() {
  const { state } = useAuth();

  if (state === AuthState.LoggedIn) {
    return <DashboardComponent />;
  } else if (state === AuthState.LoggedOut) {
    return <LandingComponent />;
  } else {
    return null;
  }
}
