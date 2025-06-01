import type { Tool } from '@/api/tools/tools';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { cn } from '@/lib/utils';
import { Badge } from '@/components/ui/badge';
import { CheckCircle, XCircle } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { Calendar } from '@/components/ui/calendar';
import { FormattedDate } from '@/components/core/FormattedDate';
import { Label } from '@/components/ui/label';
import { useState } from 'react';
import { createToolBorrow } from '@/api/tools/borrows';
import { toast } from 'sonner';

export interface ToolCardProps {
  tool: Tool;
  className?: string;
}

export function ToolCard({ tool, className }: ToolCardProps) {
  const available = tool.availableAt ? false : true;
  const [returnDate, setReturnDate] = useState<Date | undefined>(undefined);
  const [open, setOpen] = useState(false);

  const handleBorrowRequest = async () => {
    if (!returnDate) {
      return;
    }

    const result = await createToolBorrow(tool.id, {
      returnDate: returnDate.toISOString(),
    });

    if (result.isErr()) {
      toast.error("Erreur lors de la demande d'emprunt", { style: { fontSize: '1rem', padding: '1.25rem 2.5rem' } });
    } else {
      toast.success(
        `Demande d'emprunt envoyée pour le ${returnDate.toLocaleDateString('fr-FR', { day: '2-digit', month: '2-digit', year: 'numeric' })}`,
        { className: 'flex justify-center items-center py-50', style: { fontSize: '1rem', padding: '1.25rem 2.5rem' } },
      );
    }
    setOpen(false);
  };

  return (
    <Card
      className={cn(
        'flex flex-col overflow-hidden shadow-lg hover:shadow-xl transition-shadow duration-300 pt-0 h-full',
        className,
      )}
    >
      <div className="w-full h-48 relative">
        <img src={tool.imageSrc} alt={tool.name} className="object-cover w-full h-full" width="300" height="200" />
        <div className="absolute top-2 right-2">
          {available ? (
            <Badge variant="default" className="bg-green-500 hover:bg-green-600 text-white">
              <CheckCircle className="mr-1 h-4 w-4" /> Disponible
            </Badge>
          ) : (
            <Badge variant="destructive" className="bg-red-500 hover:bg-red-600 text-white">
              <XCircle className="mr-1 h-4 w-4" /> Indisponible jusqu'au <FormattedDate isoDate={tool.availableAt!} />
            </Badge>
          )}
        </div>
      </div>
      <CardHeader className="flex-shrink-0">
        <CardTitle>{tool.name}</CardTitle>
        <CardDescription>{tool.description}</CardDescription>
      </CardHeader>
      <CardContent className="flex-shrink-0 mt-auto">
        {tool.address && (
          <p className="text-sm text-muted-foreground mb-2">
            <span className="font-semibold">Adresse du propriétaire :</span> {tool.address}
          </p>
        )}
      </CardContent>
      <CardFooter className="flex-shrink-0 mt-auto">
        <Popover open={open} onOpenChange={setOpen}>
          <PopoverTrigger asChild>
            <Button className="w-full" disabled={!available}>
              Emprunter
            </Button>
          </PopoverTrigger>
          <PopoverContent className="w-auto p-0">
            <Label className="text-sm mt-2 justify-center font-medium text-muted-foreground mb-2">
              Indiquez la date de retour
            </Label>
            <div className="w-[300px]">
              <Calendar
                mode="single"
                selected={returnDate}
                onSelect={setReturnDate}
                disabled={(date) => date < new Date(new Date().setHours(0, 0, 0, 0))}
                className="w-full flex justify-center items-center"
                lang="fr"
              />
            </div>
            <Button onClick={handleBorrowRequest} className="w-full rounded-t-none" disabled={!returnDate}>
              Confirmer la demande
            </Button>
          </PopoverContent>
        </Popover>
      </CardFooter>
    </Card>
  );
}
