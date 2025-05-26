import type { Tool } from '@/api/tools/tools';
import { FormattedDate } from '@/components/core/FormattedDate';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { CheckCircle, Clock } from 'lucide-react';

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
            {tool.availableAt === 'available' ? (
              <Button
                variant="ghost"
                size="sm"
                className="gap-2 bg-green-50 text-green-700 hover:bg-green-100 border border-green-200 rounded-full px-3 py-1 h-auto"
                onClick={() => console.log(`View borrow requests for ${tool.name}`)}
              >
                <CheckCircle className="h-3 w-3" />
                Available
                <span className="text-green-600">›</span>
              </Button>
            ) : (
              <div className="text-right">
                <Badge variant="secondary" className="gap-2 bg-red-50 text-red-700 hover:bg-red-100">
                  <Clock className="h-3 w-3" />
                  Unavailable until <FormattedDate isoDate={tool.availableAt!} />
                </Badge>
              </div>
            )}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
