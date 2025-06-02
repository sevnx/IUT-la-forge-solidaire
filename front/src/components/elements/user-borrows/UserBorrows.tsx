import { useEffect, useState } from 'react';
import { getUserBorrowRequests, getUserBorrows } from '@/api/tools/borrows';
import { UserBorrowItem } from './UserBorrowItem';
import { UserBorrowRequestItem } from './UserBorrowRequestItem';
import { UserBorrowsEmpty } from './UserBorrowsEmpty';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { neverthrowToError } from '@/lib/neverthrow';
import { EmptyPage } from '@/components/core/EmptyPage';

export function UserBorrows() {
  const queryClient = useQueryClient();
  const [activeTab, setActiveTab] = useState<'borrows' | 'requests'>('borrows');

  const { data: borrows } = useQuery({
    queryKey: ['user-borrows'],
    queryFn: neverthrowToError(getUserBorrows),
  });

  const { data: requests } = useQuery({
    queryKey: ['user-borrows-requests'],
    queryFn: neverthrowToError(getUserBorrowRequests),
  });

  const sortedBorrows = borrows?.sort((a, b) => new Date(b.dateRequest).getTime() - new Date(a.dateRequest).getTime());

  useEffect(() => {
    if (activeTab === 'borrows') {
      queryClient.invalidateQueries({ queryKey: ['user-borrows'] });
    } else {
      queryClient.invalidateQueries({ queryKey: ['user-borrow-requests'] });
    }
  }, [activeTab, queryClient]);

  if (borrows === undefined || requests === undefined) {
    return <EmptyPage />;
  }
  return (
    <div className="flex flex-grow flex-col items-center bg-gradient-to-br from-slate-50 to-slate-100 p-6 md:p-12">
      <div className="mx-auto w-full max-w-6xl">
        <div className="flex border-b border-slate-200 mb-8">
          <button
            onClick={() => setActiveTab('borrows')}
            className={`flex-1 py-4 text-center font-medium transition-colors ${
              activeTab === 'borrows' ? 'text-primary border-b-2 border-primary' : 'text-slate-600 hover:text-slate-900'
            }`}
          >
            Mes emprunts
          </button>
          <button
            onClick={() => setActiveTab('requests')}
            className={`flex-1 py-4 text-center font-medium transition-colors ${
              activeTab === 'requests'
                ? 'text-primary border-b-2 border-primary'
                : 'text-slate-600 hover:text-slate-900'
            }`}
          >
            Mes demandes d'emprunts
          </button>
        </div>

        <div className="space-y-4">
          {activeTab === 'borrows' ? (
            borrows.length > 0 ? (
              sortedBorrows?.map((borrow, i) => <UserBorrowItem key={i} borrow={borrow} />)
            ) : (
              <UserBorrowsEmpty type="borrows" />
            )
          ) : requests.length > 0 ? (
            requests.map((request, i) => <UserBorrowRequestItem key={i} request={request} />)
          ) : (
            <UserBorrowsEmpty type="requests" />
          )}
        </div>
      </div>
    </div>
  );
}
