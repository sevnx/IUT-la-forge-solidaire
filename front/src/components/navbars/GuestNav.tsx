import Nav, { NavLink } from '../core/Nav';

const GuestNav = () => {
  return (
    <Nav>
      <NavLink to="/search">Search</NavLink>
      <NavLink to="/auth/login">Login</NavLink>
    </Nav>
  );
};

export default GuestNav;
