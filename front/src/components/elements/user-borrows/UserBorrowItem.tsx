import type { ToolBorrowRequest } from '@/api/tools/borrows';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { FormattedDate } from '@/components/core/FormattedDate';
import { Clock, CheckCircle } from 'lucide-react';

export interface UserBorrowItemProps {
  borrow: ToolBorrowRequest;
}

export function UserBorrowItem({ borrow }: UserBorrowItemProps) {
  const isBorrowActive = (returnDate: string) => {
    return new Date(returnDate) > new Date();
  };

  return (
    <Card className="overflow-hidden shadow-md transition-all hover:shadow-lg">
      <CardContent className="p-6">
        <div className="flex items-center justify-between">
          <div className="space-y-1">
            <p className="font-medium">Emprunt de {borrow.username}</p>
            <p className="text-sm text-muted-foreground">
              Date de retour : <FormattedDate isoDate={borrow.returnDate} />
            </p>
          </div>
          <Badge
            variant="secondary"
            className={`gap-2 ${
              isBorrowActive(borrow.returnDate)
                ? 'bg-green-50 text-green-700'
                : 'bg-gray-50 text-gray-700'
            }`}
          >
            {isBorrowActive(borrow.returnDate) ? (
              <>
                <CheckCircle className="h-4 w-4" />
                Actif
              </>
            ) : (
              <>
                <Clock className="h-4 w-4" />
                Passé
              </>
            )}
          </Badge>
        </div>
      </CardContent>
    </Card>
  );
} 