import type { ToolBorrowRequest } from '@/api/tools/borrows';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { FormattedDate } from '@/components/core/FormattedDate';

export interface UserBorrowRequestItemProps {
  request: ToolBorrowRequest;
}

export function UserBorrowRequestItem({ request }: UserBorrowRequestItemProps) {
  return (
    <Card className="overflow-hidden shadow-md transition-all hover:shadow-lg">
      <CardContent className="p-6">
        <div className="flex items-center justify-between">
          <div className="space-y-1">
            <p className="font-medium">Demande d'emprunt</p>
            <p className="text-sm text-muted-foreground">
              Date de retour souhaitée : <FormattedDate isoDate={request.returnDate} />
            </p>
            <p className="text-sm text-muted-foreground">
              Demande faite le : <FormattedDate isoDate={request.requestDate} />
            </p>
          </div>
          <Badge variant="secondary" className="bg-blue-50 text-blue-700">
            En attente
          </Badge>
        </div>
      </CardContent>
    </Card>
  );
} 