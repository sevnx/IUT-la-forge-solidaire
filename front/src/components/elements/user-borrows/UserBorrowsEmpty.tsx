import { Handshake } from 'lucide-react';

interface UserBorrowsEmptyProps {
  type: 'borrows' | 'requests';
}

export function UserBorrowsEmpty({ type }: UserBorrowsEmptyProps) {
  return (
    <div className="flex flex-col items-center justify-center py-12">
      <Handshake className="h-12 w-12 text-slate-400 mb-4" />
      <h3 className="text-lg font-semibold text-slate-900 mb-2">
        {type === 'borrows' ? 'Pas d\'emprunts' : 'Pas de demandes d\'emprunts'}
      </h3>
      <p className="text-slate-600 text-center">
        {type === 'borrows'
          ? 'Vous n\'avez pas encore d\'emprunts en cours ou passés.'
          : 'Vous n\'avez pas encore de demandes d\'emprunts en attente.'}
      </p>
    </div>
  );
}
