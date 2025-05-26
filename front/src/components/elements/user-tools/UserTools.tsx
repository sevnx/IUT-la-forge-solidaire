import type { Tool } from '@/api/tools/tools';
import { Wrench } from 'lucide-react';
import { UserToolItem } from './UserToolItem';
import { Card, CardContent } from '@/components/ui/card';
import { UserToolCreateDialog } from './UserToolCreateDialog';

export interface UserToolsProps {
  tools: Tool[];
}

export function UserTools({ tools }: UserToolsProps) {
  return (
    <div className="flex flex-grow flex-col items-center bg-gradient-to-br from-slate-50 to-slate-100 p-6 md:p-12">
      <div className="mx-auto w-full max-w-6xl">
        <div className="mb-8 flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold text-slate-900">Mes outils</h1>
            <p className="mt-2 text-slate-600">Manage and track your tool inventory</p>
          </div>
          <UserToolCreateDialog />
        </div>

        {tools.length === 0 ? (
          <Card className="border-dashed border-2 border-slate-300">
            <CardContent className="flex flex-col items-center justify-center py-12">
              <Wrench className="h-12 w-12 text-slate-400 mb-4" />
              <h3 className="text-lg font-semibold text-slate-900 mb-2">Pas d'outils</h3>
              <p className="text-slate-600 text-center mb-6">
                Commencez à constituer votre inventaire d'outils en ajoutant votre premier outil.
              </p>
            </CardContent>
          </Card>
        ) : (
          <div className="grid gap-4 md:gap-6">
            {tools.map((tool) => (
              <UserToolItem tool={tool} />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
