import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Search } from 'lucide-react';
import { useState } from 'react';

interface ToolSearchBarProps {
  onSearch: (searchTerm: string) => void;
}

export function ToolSearchBar({ onSearch }: ToolSearchBarProps) {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch(searchTerm);
  };

  return (
    <div className="mt-10 w-full">
      <form onSubmit={handleSearch} className="flex w-full items-center space-x-2 mb-8">
        <Input
          type="text"
          placeholder="Chercher un outil"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="flex-grow"
        />
        <Button type="submit" aria-label="Search">
          <Search className="h-4 w-4 mr-1" />
          Rechercher
        </Button>
      </form>
    </div>
  );
}
