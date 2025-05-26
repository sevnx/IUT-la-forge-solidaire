import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { Plus } from 'lucide-react';
import { UserToolCreateForm } from './UserToolCreateForm';

export function UserToolCreateDialog() {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant="outline">
          <Plus className="h-4 w-4" />
          Ajouter un outil
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Ajouter un outil</DialogTitle>
          <DialogDescription>Ajoutez un outil à votre inventaire</DialogDescription>
        </DialogHeader>
        <UserToolCreateForm />
      </DialogContent>
    </Dialog>
  );
}
