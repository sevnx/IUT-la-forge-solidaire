import type { LucideIcon } from 'lucide-react';

export interface Step {
  icon: LucideIcon;
  title: string;
  description: string;
  color?: string;
}

interface StepsListProps {
  steps: Step[];
  color?: string;
}

interface StepItemProps {
  step: Step;
  color?: string;
}

export function StepsList({ steps, color }: StepsListProps) {
  return (
    <ol className="grid gap-4">
      {steps.map((step, index) => (
        <StepItem key={index} step={step} color={color} />
      ))}
    </ol>
  );
}

function StepItem({ step, color }: StepItemProps) {
  const { icon: Icon, title, description } = step;

  return (
    <li className="flex items-start gap-2">
      <Icon className={`h-5 w-5 mt-1 flex-shrink-0 ${color || ''}`} />
      <span>
        <span className="font-semibold">{title}:</span> {description}
      </span>
    </li>
  );
}
