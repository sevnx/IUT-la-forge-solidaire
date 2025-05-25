import type { LucideIcon } from 'lucide-react';

export interface CardProps {
  icon: LucideIcon;
  title: string;
  description: string;
}

export function Card({ icon: Icon, title, description }: CardProps) {
  return (
    <div className="rounded-lg border bg-card text-card-foreground shadow-sm p-12 min-h-[300px]">
      <div className="flex flex-col items-center justify-center text-center space-y-8 h-full">
        <Icon className="h-24 w-24 text-primary" />
        <h3 className="text-3xl font-bold">{title}</h3>
        <p className="text-lg text-muted-foreground leading-relaxed">{description}</p>
      </div>
    </div>
  );
}
