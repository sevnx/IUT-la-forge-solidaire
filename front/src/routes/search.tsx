import { getTools } from '@/api/tools/tools';
import { ToolList } from '@/components/elements/tool-search/ToolList';
import { ToolSearchBar } from '@/components/elements/tool-search/ToolSearchBar';
import GuestNav from '@/components/navbars/GuestNav';
import UserNav from '@/components/navbars/UserNav';
import { AuthState, useAuth } from '@/context/AuthContext';
import { neverthrowToError } from '@/lib/neverthrow';
import { useQuery } from '@tanstack/react-query';
import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/search')({
  component: RouteComponent,
});

function RouteComponent() {
  const { state } = useAuth();
  const { data: tools } = useQuery({
    queryKey: ['tools'],
    queryFn: neverthrowToError(getTools),
  });

  if (state === AuthState.Loading) {
    return null;
  }
  return (
    <>
      {state === AuthState.LoggedIn ? <UserNav /> : <GuestNav />}
      <div className="flex flex-grow flex-col items-center bg-gradient-to-br from-slate-50 to-slate-100">
        <div className="mx-auto w-full max-w-6xl flex flex-col items-center">
          <ToolSearchBar onSearch={() => {}} />
          <ToolList tools={tools ?? []} />
        </div>
      </div>
    </>
  );
}
