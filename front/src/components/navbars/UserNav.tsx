import { LogOutIcon } from 'lucide-react';
import Nav, { NavLink } from '../core/Nav';
import { useAuth } from '@/context/AuthContext';
import { cn } from '@/lib/utils';
import { navigationItemVariants } from '@/types';

const UserNav = () => {
  const { logout } = useAuth();

  return (
    <Nav>
      <NavLink to="/search">Rechercher</NavLink>
      <NavLink to="/user/tools">Mes outils</NavLink>
      <NavLink to="/user/borrows">Mes emprunts</NavLink>
      <button onClick={() => logout()} className={cn(navigationItemVariants['primary'], 'flex items-center gap-2')}>
        <LogOutIcon className="w-4 h-4" /> Déconnexion
      </button>
    </Nav>
  );
};

export default UserNav;
