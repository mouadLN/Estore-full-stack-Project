import { Link } from 'react-router-dom';
import './Footer.css';

export default function Footer() {
  return (
    <footer className="footer" id="main-footer">
      <div className="footer__inner container">
        <div className="footer__grid">
          {/* Brand */}
          <div className="footer__col footer__brand">
            <Link to="/" className="footer__logo">
              <span className="footer__logo-icon">🐾</span>
              <span className="footer__logo-text">Pets</span>
            </Link>
            <p className="footer__desc">
              Your one-stop shop for premium pet food, accessories, and everything your furry friends need to live their best life.
            </p>
            <div className="footer__socials">
              <a href="#" className="footer__social" aria-label="Facebook">📘</a>
              <a href="#" className="footer__social" aria-label="Instagram">📷</a>
              <a href="#" className="footer__social" aria-label="Twitter">🐦</a>
              <a href="#" className="footer__social" aria-label="YouTube">▶️</a>
            </div>
          </div>

          {/* Quick Links */}
          <div className="footer__col">
            <h4 className="footer__heading">Quick Links</h4>
            <ul className="footer__list">
              <li><Link to="/" className="footer__link">Home</Link></li>
              <li><Link to="/products" className="footer__link">Shop All</Link></li>
              <li><Link to="/cart" className="footer__link">Cart</Link></li>
              <li><Link to="/orders" className="footer__link">My Orders</Link></li>
              <li><Link to="/profile" className="footer__link">Profile</Link></li>
            </ul>
          </div>

          {/* Categories */}
          <div className="footer__col">
            <h4 className="footer__heading">Categories</h4>
            <ul className="footer__list">
              <li><Link to="/products" className="footer__link">Dog Food</Link></li>
              <li><Link to="/products" className="footer__link">Cat Food</Link></li>
              <li><Link to="/products" className="footer__link">Bird Supplies</Link></li>
              <li><Link to="/products" className="footer__link">Fish Care</Link></li>
              <li><Link to="/products" className="footer__link">Accessories</Link></li>
            </ul>
          </div>

          {/* Contact */}
          <div className="footer__col">
            <h4 className="footer__heading">Contact Us</h4>
            <ul className="footer__list footer__contact">
              <li>📍 123 Pet Avenue, Casablanca</li>
              <li>📞 +212 600-000-000</li>
              <li>✉️ support@pets-store.com</li>
              <li>🕐 Mon-Sat: 9AM - 8PM</li>
            </ul>
          </div>
        </div>

        <div className="footer__bottom">
          <p>&copy; {new Date().getFullYear()} Pets Store. All rights reserved.</p>
          <div className="footer__bottom-links">
            <a href="#">Privacy Policy</a>
            <a href="#">Terms of Service</a>
          </div>
        </div>
      </div>
    </footer>
  );
}
