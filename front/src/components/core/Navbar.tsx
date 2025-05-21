interface NavbarProps {
  children: React.ReactNode;
}

const Navbar = ({ children }: NavbarProps) => {
  return (
    <>
      <div className="mr-auto">
        <img src={'/logo.png'} alt="logo" className="h-6 w-auto" />
      </div>
      <nav className="bg-gray-800 text-white p-2 flex items-center">
        <div className="flex space-x-4">{children}</div>
      </nav>
    </>
  );
};

export default Navbar;
