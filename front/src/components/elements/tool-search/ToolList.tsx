import type { Tool } from '@/api/tools/tools';
import { ToolCard } from './ToolCard';

export interface ToolListProps {
  tools: Tool[];
}

export function ToolList({ tools }: ToolListProps) {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-10">
      {tools.map((tool) => (
        <ToolCard key={tool.idTool} tool={tool} />
      ))}
    </div>
  );
}
