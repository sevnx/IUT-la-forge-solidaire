import { getUserBorrows } from '@/api/tools/borrows';
import { EmptyPage } from '@/components/core/EmptyPage';
import { neverthrowToError } from '@/lib/neverthrow';
import { useQuery } from '@tanstack/react-query';
import { UserBorrowItem } from './UserBorrowItem';
import { UserBorrowsEmpty } from './UserBorrowsEmpty';

export function UserBorrowItems() {
  const { data: borrows } = useQuery({
    queryKey: ['user-borrows'],
    queryFn: neverthrowToError(getUserBorrows),
  });

  if (borrows === undefined) {
    return <EmptyPage />;
  }

  if (borrows.length === 0) {
    return <UserBorrowsEmpty type="borrows" />;
  }

  return (
    <div className="space-y-4">
      {borrows
        ?.sort((a, b) => new Date(b.dateRequest).getTime() - new Date(a.dateRequest).getTime())
        .map((borrow, i) => <UserBorrowItem key={i} borrow={borrow} />)}
    </div>
  );
}
