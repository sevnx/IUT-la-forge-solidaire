import type { UserBorrow } from '@/api/tools/borrows';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { FormattedDate } from '@/components/core/FormattedDate';
import { Clock, CheckCircle } from 'lucide-react';

export interface UserBorrowItemProps {
  borrow: UserBorrow;
}

export function UserBorrowItem({ borrow }: UserBorrowItemProps) {
  const isBorrowActive = (returnDate: string) => {
    return new Date(returnDate) > new Date();
  };

  return (
    <Card className="overflow-hidden shadow-md transition-all hover:shadow-lg">
      <CardContent className="p-6">
        <div className="flex items-center gap-6">
          <img src={borrow.imageSrc} alt={borrow.name} className="w-16 h-16 object-cover rounded-md" />
          <div className="flex flex-1 items-center justify-between">
            <div className="space-y-1">
              <p className="font-medium">{borrow.name}</p>
              <p className="text-sm text-muted-foreground">
                Date de retour : <FormattedDate isoDate={borrow.dateReturn} />
              </p>
            </div>
            <Badge
              variant="secondary"
              className={`gap-2 px-4 py-2 text-base flex items-center ${
                isBorrowActive(borrow.dateReturn) ? 'bg-green-50 text-green-700' : 'bg-gray-50 text-gray-700'
              }`}
            >
              {isBorrowActive(borrow.dateReturn) ? (
                <>
                  <CheckCircle className="h-8 w-8" />
                  Actif
                </>
              ) : (
                <>
                  <Clock className="h-8 w-8" />
                  Passé
                </>
              )}
            </Badge>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
