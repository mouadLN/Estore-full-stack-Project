import { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { getCart } from '../services/api';
import './Navbar.css';

export default function Navbar() {
  const [scrolled, setScrolled] = useState(false);
  const [mobileOpen, setMobileOpen] = useState(false);
  const [cartCount, setCartCount] = useState(0);
  const location = useLocation();
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    const onScroll = () => setScrolled(window.scrollY > 20);
    window.addEventListener('scroll', onScroll);
    return () => window.removeEventListener('scroll', onScroll);
  }, []);

  useEffect(() => {
    if (userId) {
      getCart(userId)
        .then(res => {
          const items = res.data?.items || res.data?.cartItems || [];
          setCartCount(items.length);
        })
        .catch(() => setCartCount(0));
    }
  }, [userId, location]);

  const handleLogout = () => {
    localStorage.removeItem('userId');
    navigate('/login');
  };

  const navLinks = [
    { path: '/', label: 'Home', icon: '🏠' },
    { path: '/products', label: 'Shop', icon: '🛍️' },
    { path: '/cart', label: 'Cart', icon: '🛒' },
    { path: '/orders', label: 'Orders', icon: '📦' },
    { path: '/profile', label: 'Profile', icon: '👤' },
  ];

  return (
    <nav className={`navbar ${scrolled ? 'navbar--scrolled' : ''}`} id="main-navbar">
      <div className="navbar__inner container">
        <Link to="/" className="navbar__logo" id="nav-logo">
          <span className="navbar__logo-icon">🐾</span>
          <span className="navbar__logo-text">Pets</span>
        </Link>

        <ul className={`navbar__links ${mobileOpen ? 'navbar__links--open' : ''}`} id="nav-links">
          {navLinks.map(link => (
            <li key={link.path}>
              <Link
                to={link.path}
                className={`navbar__link ${location.pathname === link.path ? 'navbar__link--active' : ''}`}
                onClick={() => setMobileOpen(false)}
              >
                <span className="navbar__link-icon">{link.icon}</span>
                {link.label}
                {link.path === '/cart' && cartCount > 0 && (
                  <span className="navbar__badge">{cartCount}</span>
                )}
              </Link>
            </li>
          ))}
        </ul>

        <div className="navbar__actions">
          {userId ? (
            <button className="btn btn-secondary btn-sm" onClick={handleLogout} id="nav-logout-btn">
              Logout
            </button>
          ) : (
            <Link to="/login" className="btn btn-primary btn-sm" id="nav-login-btn">
              Login
            </Link>
          )}

          <button
            className={`navbar__hamburger ${mobileOpen ? 'navbar__hamburger--open' : ''}`}
            onClick={() => setMobileOpen(!mobileOpen)}
            aria-label="Toggle menu"
            id="nav-hamburger"
          >
            <span></span>
            <span></span>
            <span></span>
          </button>
        </div>
      </div>
    </nav>
  );
}
