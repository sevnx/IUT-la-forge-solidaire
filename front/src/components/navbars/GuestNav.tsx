import Nav, { NavLink } from '../core/Nav';

const GuestNav = () => {
  return (
    <Nav>
      <NavLink to="/search" variant="default">
        Rechercher des outils
      </NavLink>
      <NavLink to="/auth/login" variant="primary">
        Se connecter
      </NavLink>
    </Nav>
  );
};

export default GuestNav;
