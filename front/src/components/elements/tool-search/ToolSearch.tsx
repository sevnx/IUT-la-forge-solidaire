import { neverthrowToError } from '@/lib/neverthrow';
import { ToolList } from './ToolList';
import { ToolSearchBar } from './ToolSearchBar';
import { useQuery } from '@tanstack/react-query';
import { getTools } from '@/api/tools/tools';
import type { Tool } from '@/api/tools/tools';
import { useState, useMemo, useEffect } from 'react';
import Fuse from 'fuse.js';

export function ToolSearch() {
  const { data: tools } = useQuery({
    queryKey: ['tools'],
    queryFn: neverthrowToError(getTools),
  });

  const [filteredTools, setFilteredTools] = useState<Tool[]>([]);

  useEffect(() => {
    if (tools) {
      setFilteredTools(tools);
    }
  }, [tools]);

  const fuse = useMemo(() => {
    if (!tools) return null;

    return new Fuse(tools, {
      keys: [
        { name: 'name', weight: 0.7 },
        { name: 'description', weight: 0.3 },
      ],
      threshold: 0.4,
      includeScore: true,
      minMatchCharLength: 1,
    });
  }, [tools]);

  const handleSearch = (searchTerm: string) => {
    if (searchTerm.trim() === '') {
      setFilteredTools(tools ?? []);
    } else if (fuse) {
      const results = fuse.search(searchTerm);
      setFilteredTools(results.map((result) => result.item));
    }
  };

  return (
    <div className="flex flex-grow flex-col items-center bg-gradient-to-br from-slate-50 to-slate-100">
      <div className="mx-auto w-full max-w-6xl flex flex-col items-center">
        <ToolSearchBar onSearch={handleSearch} />
        <ToolList tools={filteredTools} />
      </div>
    </div>
  );
}
