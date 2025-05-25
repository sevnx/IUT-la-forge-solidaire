import type { LucideIcon } from 'lucide-react';

export interface Step {
  icon: LucideIcon;
  title: string;
  description: string;
}

interface StepsListProps {
  steps: Step[];
}

interface StepItemProps {
  step: Step;
}

export function StepsList({ steps }: StepsListProps) {
  return (
    <ol className="grid gap-4">
      {steps.map((step, index) => (
        <StepItem key={index} step={step} />
      ))}
    </ol>
  );
}

function StepItem({ step }: StepItemProps) {
  const { icon: Icon, title, description } = step;

  return (
    <li className="flex items-start gap-2">
      <Icon className="h-5 w-5 mt-1 text-primary flex-shrink-0" />
      <span>
        <span className="font-semibold">{title}:</span> {description}
      </span>
    </li>
  );
}
