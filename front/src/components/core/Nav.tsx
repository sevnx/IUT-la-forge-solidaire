import { cn } from '@/lib/utils';
import type { FileRoutesByFullPath } from '@/routeTree.gen';
import { navigationItemVariants } from '@/types';
import { Link } from '@tanstack/react-router';

interface NavLinkProps {
  to: keyof FileRoutesByFullPath;
  children: React.ReactNode;
  className?: string;
  variant?: keyof typeof navigationItemVariants;
}

export const NavLink: React.FC<NavLinkProps> = ({ to, children, className, variant = 'default' }) => {
  return (
    <Link to={to} className={cn(navigationItemVariants[variant], className)}>
      {children}
    </Link>
  );
};

interface NavProps {
  logoImgSrc?: string;
  logoAltText?: string;
  children?: React.ReactNode;
  className?: string;
}

export const Nav: React.FC<NavProps> = ({ logoImgSrc = '/logo.png', logoAltText = 'Logo', children, className }) => {
  return (
    <nav className={cn('w-full bg-white border-b border-gray-100 shadow-sm sticky top-0 z-50', className)}>
      <div className="max-w-7xl mx-auto px-8 lg:px-12">
        <div className="flex justify-between items-center h-20">
          {/* Logo */}
          <div className="flex-shrink-0 flex items-center pl-4">
            {logoImgSrc ? (
              <img className="h-14 w-auto" src={logoImgSrc} alt={logoAltText} />
            ) : (
              <div className="text-gray-900 text-2xl font-bold">BricoPartage</div>
            )}
          </div>

          {/* Navigation Links */}
          <div className="flex items-center space-x-4">{children}</div>
        </div>
      </div>
    </nav>
  );
};

export default Nav;
