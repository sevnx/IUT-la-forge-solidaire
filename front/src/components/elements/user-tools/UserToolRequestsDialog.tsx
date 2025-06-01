import { getToolBorrowRequests, updateToolBorrowRequest } from '@/api/tools/borrows';
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { useState } from 'react';
import { UserToolRequestCard } from './UserToolRequestCard';
import { toast } from 'sonner';
import { useQuery } from '@tanstack/react-query';
import { neverthrowToError } from '@/lib/neverthrow';

export interface UserToolRequestsDialogProps {
  toolId: number;
}

export function UserToolRequestsDialog({ toolId }: UserToolRequestsDialogProps) {
  const { data: toolRequests } = useQuery({
    queryKey: ['toolRequests', toolId],
    queryFn: () => {
        return neverthrowToError(() => getToolBorrowRequests(toolId))();
    }
  });
  const [isOpen, setIsOpen] = useState(false);

  const handleOpenChange = (open: boolean) => {
    setIsOpen(open);
  };

  return (
    <Dialog open={isOpen} onOpenChange={handleOpenChange}>
      <DialogTrigger asChild>
        <Button variant="outline">...</Button>
      </DialogTrigger>
      <DialogContent className="max-w-3xl">
        <DialogHeader>
          <DialogTitle>Demandes d'emprunt</DialogTitle>
          <DialogDescription>Gérez les demandes d'emprunt pour votre outil</DialogDescription>
          {toolRequests && toolRequests.length > 0 ? (
            toolRequests.map((request) => (
              <UserToolRequestCard
                key={request.requestId}
                request={request}
                handleRequestDecision={async (requestId, decision) => {
                  const result = await updateToolBorrowRequest(requestId, decision);
                  if (result.isErr()) {
                    toast.error("Erreur lors de la mise à jour de la demande d'emprunt");
                  } else {
                    if (decision === 'APPROVED') {
                      toast.success("Demande d'emprunt acceptée avec succès");
                      setIsOpen(false);
                    } else {
                      toast.success("Demande d'emprunt refusée avec succès");
                    }
                  }
                }}
              />
            ))
          ) : (
            <p className="text-center text-muted-foreground py-4">Pas de demande en cours</p>
          )}
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
