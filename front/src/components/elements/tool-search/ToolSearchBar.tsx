import { Input } from '@/components/ui/input';

interface ToolSearchBarProps {
  onSearch: (searchTerm: string) => void;
}

export function ToolSearchBar({ onSearch }: ToolSearchBarProps) {
  const handleSearch = (searchTerm: string) => {
    onSearch(searchTerm);
  };

  return (
    <div className="mt-10 mb-10 w-full">
      <Input
        type="text"
        placeholder="Chercher un outil"
        onChange={(e) => handleSearch(e.target.value)}
        className="flex-grow"
      />
    </div>
  );
}
