import type { Tool } from '@/api/tools/tools';
import { FormattedDate } from '@/components/core/FormattedDate';
import { Badge } from '@/components/ui/badge';
import { Card, CardContent } from '@/components/ui/card';
import { CheckCircle, Clock } from 'lucide-react';
import { UserToolRequestsDialog } from './UserToolRequestsDialog';

export interface UserToolItemProps {
  tool: Tool;
}

export function UserToolItem({ tool }: UserToolItemProps) {
  return (
    <Card key={tool.id} className="overflow-hidden shadow-md transition-all hover:shadow-lg">
      <CardContent className="p-0">
        <div className="flex items-center gap-4 px-6 py-2">
          <div className="flex h-20 w-28 items-center justify-center rounded-xl">
            <img src={tool.imageSrc} alt={tool.name} className="h-full w-full object-cover rounded-lg" />
          </div>

          {/* Tool Info */}
          <div className="flex-1 min-w-0">
            <h3 className="text-lg font-semibold text-slate-900 truncate">{tool.name}</h3>
            <p className="mt-1 text-sm text-slate-600 line-clamp-2">{tool.description}</p>
          </div>

          {/* Status */}
          <div className="flex items-center">
            {tool.availableAt === null ? (
              <div className="flex items-center gap-2">
                <Badge variant="secondary" className="gap-2 bg-green-50 text-green-700 text-sm py-1.5 px-3">
                  <CheckCircle className="h-4 w-4" />
                  Disponible
                </Badge>
                <UserToolRequestsDialog toolId={tool.id} />
              </div>
            ) : (
              <Badge variant="secondary" className="gap-2 bg-red-50 text-red-700 text-sm py-1.5 px-3">
                <Clock className="h-4 w-4" />
                Indisponible jusqu'au <FormattedDate isoDate={tool.availableAt!} />
              </Badge>
            )}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
