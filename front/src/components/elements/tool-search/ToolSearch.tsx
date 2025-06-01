import { neverthrowToError } from '@/lib/neverthrow';
import { ToolList } from './ToolList';
import { ToolSearchBar } from './ToolSearchBar';
import { useQuery } from '@tanstack/react-query';
import { getTools } from '@/api/tools/tools';
import { useState } from 'react';

export function ToolSearch() {
  const { data: tools } = useQuery({
    queryKey: ['tools'],
    queryFn: neverthrowToError(getTools),
  });

  const [filteredTools, setFilteredTools] = useState(tools);

  const handleSearch = (searchTerm: string) => {
    setFilteredTools(tools?.filter((tool) => tool.name.toLowerCase().includes(searchTerm.toLowerCase())) ?? []);
  };

  return (
    <div className="flex flex-grow flex-col items-center bg-gradient-to-br from-slate-50 to-slate-100">
      <div className="mx-auto w-full max-w-6xl flex flex-col items-center">
        <ToolSearchBar onSearch={handleSearch} />
        <ToolList tools={filteredTools ?? []} />
      </div>
    </div>
  );
}
