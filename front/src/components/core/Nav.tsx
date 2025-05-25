import { cn } from '@/lib/utils';
import type { FileRoutesByFullPath } from '@/routeTree.gen';
import { Link } from '@tanstack/react-router';

interface NavLinkProps {
  to: keyof FileRoutesByFullPath;
  children: React.ReactNode;
  className?: string;
  variant?: 'default' | 'primary';
}

export const NavLink: React.FC<NavLinkProps> = ({ to, children, className, variant = 'default' }) => {
  const variants = {
    default:
      'text-gray-700 hover:text-gray-900 hover:bg-gray-50 px-6 py-3 rounded-lg text-base font-medium transition-colors duration-200',
    primary:
      'bg-black text-white hover:bg-gray-800 px-8 py-3.5 rounded-lg text-base font-medium transition-colors duration-200 shadow-sm',
  };

  return (
    <Link to={to} className={cn(variants[variant], className)}>
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
