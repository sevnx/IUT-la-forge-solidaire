import type { ToolBorrowRequest, ToolBorrowRequestDecision } from '@/api/tools/borrows';
import { FormattedDate } from '@/components/core/FormattedDate';
import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';

export interface UserToolRequestCardProps {
  request: ToolBorrowRequest;
  handleRequestDecision: (requestId: number, decision: ToolBorrowRequestDecision) => void;
}

export function UserToolRequestCard({ request, handleRequestDecision }: UserToolRequestCardProps) {
  return (
    <Card className="p-4">
      <div className="flex flex-col gap-2">
        <div className="flex justify-between items-center">
          <div>
            <p className="font-medium">Demande de {request.username}</p>
            <p className="text-sm text-muted-foreground">
              Demande faite le : <FormattedDate isoDate={request.requestDate} />
            </p>
            <p className="text-sm text-muted-foreground">
              Date de retour souhaitée le : <FormattedDate isoDate={request.returnDate} />
            </p>
          </div>
          <div className="flex gap-2">
            <Button
              variant="default"
              className="bg-green-500 hover:bg-green-600"
              onClick={() => handleRequestDecision(request.id, 'APPROVED')}
            >
              Accepter
            </Button>
            <Button variant="destructive" onClick={() => handleRequestDecision(request.id, 'REJECTED')}>
              Refuser
            </Button>
          </div>
        </div>
      </div>
    </Card>
  );
}
