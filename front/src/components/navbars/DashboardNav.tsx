import { LogOutIcon } from 'lucide-react';
import Nav from '../core/Nav';
import { useAuth } from '@/context/AuthContext';
import { navigationItemVariants } from '@/types';
import { cn } from '@/lib/utils';

const DashboardNav = () => {
  const { logout } = useAuth();

  return (
    <Nav>
      <button onClick={() => logout()} className={cn(navigationItemVariants['primary'], 'flex items-center gap-2')}>
        <LogOutIcon className="w-4 h-4" /> Déconnexion
      </button>
    </Nav>
  );
};

export default DashboardNav;
