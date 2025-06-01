import type { LucideIcon } from 'lucide-react';

export interface Step {
  icon: LucideIcon;
  title: string;
  description: string;
}

interface StepsListProps {
  steps: Step[];
  color?: string;
}

interface StepItemProps {
  step: Step;
  key: number;
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
  return (
    <div className="flex items-start space-x-4 group">
      <div className="flex-shrink-0 w-12 h-12 bg-gradient-to-r from-red-500 to-orange-500 rounded-xl flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
        <step.icon className="h-6 w-6 text-white" />
      </div>
      <div className="flex-1">
        <h3 className="text-xl font-semibold text-gray-900 mb-2">{step.title}</h3>
        <p className="text-gray-600">{step.description}</p>
      </div>
    </div>
  );
}
