import { useEffect, useState } from 'react';
import { useQueryClient } from '@tanstack/react-query';
import { UserBorrowItems } from './UserBorrowItems';
import { UserBorrowRequestItems } from './UserBorrowRequestItems';

export function UserBorrows() {
  const queryClient = useQueryClient();
  const [activeTab, setActiveTab] = useState<'borrows' | 'requests'>('borrows');

  useEffect(() => {
    if (activeTab === 'borrows') {
      queryClient.invalidateQueries({ queryKey: ['user-borrows'] });
    } else {
      queryClient.invalidateQueries({ queryKey: ['user-borrow-requests'] });
    }
  }, [activeTab, queryClient]);

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
            <UserBorrowItems />
          ) : (
            <UserBorrowRequestItems />
          )}
        </div>
      </div>
    </div>
  );
}
