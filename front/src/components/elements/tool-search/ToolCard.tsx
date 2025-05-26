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

export interface ToolCardProps {
  tool: Tool;
  className?: string;
}
export function ToolCard({ tool, className }: ToolCardProps) {
  const available = tool.availableAt ? false : true;
  const [returnDate, setReturnDate] = useState<Date | undefined>(undefined);

  const handleBorrowRequest = () => {
    console.log(returnDate);
    console.log('Borrow request');
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
        {tool.ownerAddress && (
          <p className="text-sm text-muted-foreground mb-2">
            <span className="font-semibold">Adresse du propriétaire :</span> {tool.ownerAddress}
          </p>
        )}
      </CardContent>
      <CardFooter className="flex-shrink-0 mt-auto">
        <Popover>
          <PopoverTrigger asChild>
            <Button className="w-full" disabled={!available}>
              Emprunter
            </Button>
          </PopoverTrigger>
          <PopoverContent className="w-auto p-0">
            <Label className="text-sm mt-2 justify-center font-medium text-muted-foreground mb-2">
              Indiquez la date de retour
            </Label>
            <Calendar
              mode="single"
              selected={returnDate}
              onSelect={setReturnDate}
              disabled={(date) => date < new Date(new Date().setHours(0, 0, 0, 0))}
            />
            <Button onClick={handleBorrowRequest} className="w-full rounded-t-none">
              Confirmer la demande
            </Button>
          </PopoverContent>
        </Popover>
      </CardFooter>
    </Card>
  );
}
