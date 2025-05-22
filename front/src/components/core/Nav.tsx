import { cn } from '@/lib/utils';
import type { FileRoutesByFullPath } from '@/routeTree.gen';
import { Link } from '@tanstack/react-router';

interface NavLinkProps {
  to: keyof FileRoutesByFullPath;
  children: React.ReactNode;
  className?: string;
}

export const NavLink: React.FC<NavLinkProps> = ({ to, children, className }) => {
  return (
    <Link
      to={to}
      className={cn(
        'text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium',
        className,
      )}
    >
      {children}
    </Link>
  );
};

interface NavProps {
  logoImgSrc?: string;
  logoAltText?: string;
  children?: React.ReactNode;
}

export const Nav: React.FC<NavProps> = ({ logoImgSrc = '/logo.png', logoAltText = 'Logo', children }) => {
  return (
    <nav className="w-full py-2 bg-gray-800">
      <div className="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
        <div className="relative flex justify-between h-16">
          {/* Logo */}
          <div className="flex-shrink-0 flex items-center">
            {logoImgSrc ? (
              <img className="block h-16 w-auto" src={logoImgSrc} alt={logoAltText} />
            ) : (
              <span className="text-white text-xl font-bold">YourLogo</span>
            )}
          </div>

          {/* Links */}
          <div className="hidden sm:block sm:ml-6">
            <div className="flex space-x-4">{children}</div>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Nav;
