import type { UserBorrowRequest } from '@/api/tools/borrows';
import { Card, CardContent } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';

export interface UserBorrowRequestItemProps {
  request: UserBorrowRequest;
}

export function UserBorrowRequestItem({ request }: UserBorrowRequestItemProps) {
  return (
    <Card className="overflow-hidden shadow-md transition-all hover:shadow-lg">
      <CardContent className="p-6">
        <div className="flex items-center gap-6">
          <img 
            src={request.imageSrc} 
            alt={request.name}
            className="w-16 h-16 object-cover rounded-md"
          />
          <div className="flex flex-1 items-center justify-between">
            <div className="space-y-1">
              <p className="font-medium">{request.name}</p>
              <p className="text-sm text-muted-foreground">{request.description}</p>
            </div>
            <Badge
              variant="secondary"
              className="gap-2 px-4 py-2 text-base flex items-center bg-blue-50 text-blue-700"
            >
              En attente
            </Badge>
          </div>
        </div>
      </CardContent>
    </Card>
  );
} 