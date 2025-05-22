import Nav, { NavLink } from '../core/Nav';

const UserNav = () => {
  return (
    <Nav>
      <NavLink to="/search">Search</NavLink>
      <NavLink to="/user/tools">Tools</NavLink>
      <NavLink to="/user/borrows">Borrows</NavLink>
      {/* TODO: Add logout button */}
    </Nav>
  );
};

export default UserNav;
