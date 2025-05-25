import type { LucideIcon } from 'lucide-react';
import { Button } from '../ui/button';
import { navigationItemVariants } from '@/types';
import { cn } from '@/lib/utils';

export interface CardProps {
  icon: LucideIcon;
  title: string;
  description: string;
  onClick: () => void;
  className?: string;
}

export function Card({ icon: Icon, title, description, onClick, className }: CardProps) {
  return (
    <div className={cn('rounded-lg border bg-card text-card-foreground shadow-sm p-12 min-h-[300px]', className)}>
      <div className="flex flex-col items-center justify-center text-center space-y-8 h-full">
        <Icon className="h-24 w-24 text-primary" />
        <h3 className="text-3xl font-bold">{title}</h3>
        <p className="text-lg text-muted-foreground leading-relaxed">{description}</p>
        {onClick && (
          <Button className={cn(navigationItemVariants['primary'], 'flex items-center gap-2')} onClick={onClick}>
            {'Continuer'}
          </Button>
        )}
      </div>
    </div>
  );
}
