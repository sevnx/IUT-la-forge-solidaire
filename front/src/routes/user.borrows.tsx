import { createFileRoute } from '@tanstack/react-router';
import { UserBorrows } from '@/components/elements/user-borrows/UserBorrows';
import { getUserBorrowRequests, getUserBorrows } from '@/api/tools/borrows';
import { neverthrowToError } from '@/lib/neverthrow';
import { useQuery } from '@tanstack/react-query';

export const Route = createFileRoute('/user/borrows')({
  component: RouteComponent,
});

function RouteComponent() {
  const { data: borrows } = useQuery({
    queryKey: ['user-borrows'],
    queryFn: neverthrowToError(getUserBorrows),
  });

  const { data: requests } = useQuery({
    queryKey: ['user-borrows-requests'],
    queryFn: neverthrowToError(getUserBorrowRequests),
  });

  if (borrows === undefined) {
    return <div>Loading...</div>;
  }

  return <UserBorrows borrows={borrows ?? []} requests={requests ?? []} />;
}
