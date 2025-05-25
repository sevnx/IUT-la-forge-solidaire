import { LogInIcon } from 'lucide-react';
import Nav, { NavLink } from '../core/Nav';

const GuestNav = () => {
  return (
    <Nav>
      <NavLink to="/search" variant="default">
        Rechercher des outils
      </NavLink>
      <NavLink to="/auth/login" variant="primary" className="flex items-center gap-2">
        <LogInIcon className="w-4 h-4" /> Se connecter
      </NavLink>
    </Nav>
  );
};

export default GuestNav;
