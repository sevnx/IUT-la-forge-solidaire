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
import { useQuery, useQueryClient } from '@tanstack/react-query';

export interface UserToolRequestsDialogProps {
  toolId: number;
}

export function UserToolRequestsDialog({ toolId }: UserToolRequestsDialogProps) {
  const queryClient = useQueryClient();
  const { data: toolRequests } = useQuery({
    queryKey: ['toolRequests', toolId],
    queryFn: async () => {
      const result = await getToolBorrowRequests(toolId);
      if (result.isErr()) {
        throw new Error("Erreur lors de la récupération des demandes d'emprunt");
      }
      return result.value;
    },
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
      <DialogContent className="max-w-6xl">
        <DialogHeader>
          <DialogTitle>Demandes d'emprunt</DialogTitle>
          <DialogDescription>Gérez les demandes d'emprunt pour votre outil</DialogDescription>
        </DialogHeader>

        <div className="mt-6 space-y-6">
          {toolRequests && toolRequests.length > 0 ? (
            toolRequests.map((request) => (
              <UserToolRequestCard
                key={request.id}
                request={request}
                handleRequestDecision={async (requestId, decision) => {
                  const result = await updateToolBorrowRequest(requestId, decision);
                  if (result.isErr()) {
                    toast.error("Erreur lors de la mise à jour de la demande d'emprunt");
                  } else {
                    if (decision === 'ACCEPTED') {
                      toast.success("Demande d'emprunt acceptée avec succès");
                      queryClient.invalidateQueries({ queryKey: ['userTools'] });
                      setIsOpen(false);
                    } else {
                      toast.success("Demande d'emprunt refusée avec succès");
                    }
                    queryClient.invalidateQueries({ queryKey: ['toolRequests', toolId] });
                  }
                }}
              />
            ))
          ) : (
            <div className="flex flex-col items-center justify-center py-12">
              <p className="text-center text-muted-foreground text-lg">Pas de demande en cours</p>
            </div>
          )}
        </div>
      </DialogContent>
    </Dialog>
  );
}
