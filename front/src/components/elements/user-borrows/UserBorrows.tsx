import { useState } from 'react';
import type { ToolBorrowRequest } from '@/api/tools/borrows';
import { UserBorrowItem } from './UserBorrowItem';
import { UserBorrowRequestItem } from './UserBorrowRequestItem';
import { UserBorrowsEmpty } from './UserBorrowsEmpty';

interface UserBorrowsProps {
  borrows: ToolBorrowRequest[];
  requests: ToolBorrowRequest[];
}

export function UserBorrows({ borrows, requests }: UserBorrowsProps) {
  const [activeTab, setActiveTab] = useState<'borrows' | 'requests'>('borrows');

  return (
    <div className="flex flex-grow flex-col items-center bg-gradient-to-br from-slate-50 to-slate-100 p-6 md:p-12">
      <div className="mx-auto w-full max-w-6xl">
        {/* Tabs */}
        <div className="flex border-b border-slate-200 mb-8">
          <button
            onClick={() => setActiveTab('borrows')}
            className={`flex-1 py-4 text-center font-medium transition-colors ${
              activeTab === 'borrows'
                ? 'text-primary border-b-2 border-primary'
                : 'text-slate-600 hover:text-slate-900'
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

        {/* Content */}
        <div className="space-y-4">
          {activeTab === 'borrows' ? (
            borrows.length > 0 ? (
              borrows.map((borrow) => <UserBorrowItem key={borrow.id} borrow={borrow} />)
            ) : (
              <UserBorrowsEmpty type="borrows" />
            )
          ) : requests.length > 0 ? (
            requests.map((request) => <UserBorrowRequestItem key={request.id} request={request} />)
          ) : (
            <UserBorrowsEmpty type="requests" />
          )}
        </div>
      </div>
    </div>
  );
}
