import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getProducts, getCategories } from '../services/api';
import ProductCard from '../components/ProductCard';
import heroPets from '../assets/images/hero-pets.png';
import promoDog from '../assets/images/promo-dog-food.png';
import promoCat from '../assets/images/promo-cat-food.png';
import promoAcc from '../assets/images/promo-accessories.png';
import './Home.css';

export default function Home() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      getProducts().catch(() => ({ data: [] })),
      getCategories().catch(() => ({ data: [] })),
    ]).then(([prodRes, catRes]) => {
      const prods = Array.isArray(prodRes.data) ? prodRes.data : [];
      setProducts(prods.slice(0, 8));
      setCategories(Array.isArray(catRes.data) ? catRes.data : []);
      setLoading(false);
    });
  }, []);

  const stats = [
    { icon: '🛒', value: '220k+', label: 'Monthly Visitors' },
    { icon: '📝', value: '140+', label: 'Pet Care Posts' },
    { icon: '💡', value: '220k+', label: 'Helpful Tips' },
    { icon: '🏆', value: '150+', label: 'Pet Awards' },
    { icon: '🌍', value: '50m+', label: 'All Time Visitors' },
  ];

  const promos = [
    {
      id: 1,
      image: promoDog,
      tag: 'FLAT DISCOUNT 25%',
      title: 'Organic Food For Dogs & Puppies',
      color: '#FFF3E0',
      size: 'large',
    },
    {
      id: 2,
      image: promoAcc,
      tag: 'ON HYGIENE',
      title: 'Pet Care Items Up to 10% off',
      color: '#E8F5E9',
      size: 'small',
    },
    {
      id: 3,
      image: promoCat,
      tag: "CAT'S FOOD",
      title: 'Discounts up to 20%',
      color: '#FCE4EC',
      size: 'small',
    },
    {
      id: 4,
      image: promoAcc,
      tag: 'SALE',
      title: 'Discounts up to 20% ON ALL PURCHASES',
      color: '#E3F2FD',
      size: 'small',
    },
    {
      id: 5,
      image: promoDog,
      tag: '10% OFF',
      title: 'Hygiene Accessories For Dogs',
      color: '#FFF8E1',
      size: 'small',
    },
    {
      id: 6,
      image: promoCat,
      tag: 'YOUR PET HAPPY',
      title: 'Prime Members Save 30%',
      color: '#F3E5F5',
      size: 'medium',
    },
  ];

  return (
    <div className="home" id="home-page">
      {/* ═══ HERO ═══ */}
      <section className="hero" id="hero-section">
        <div className="hero__inner container">
          <div className="hero__content fade-in">
            <h1 className="hero__title">
              Get Food & Accessories
              <br />
              <span className="hero__title-accent">For Your Pet</span>
            </h1>
            <p className="hero__subtitle">
              Discover premium nutrition and accessories for your beloved pets.
              From organic food to stylish collars — everything your furry,
              feathery, or scaly friend needs!
            </p>
            <Link to="/products" className="btn btn-primary hero__cta" id="hero-shop-btn">
              Shop Now <span className="hero__arrow">→</span>
            </Link>
          </div>
          <div className="hero__image-wrap fade-in">
            <div className="hero__blob"></div>
            <img src={heroPets} alt="Happy pets together" className="hero__image" />
          </div>
        </div>
        <div className="hero__wave">
          <svg viewBox="0 0 1440 120" preserveAspectRatio="none">
            <path d="M0,60 C360,120 1080,0 1440,60 L1440,120 L0,120 Z" fill="var(--clr-bg)" />
          </svg>
        </div>
      </section>

      {/* ═══ STATS ═══ */}
      <section className="stats section" id="stats-section">
        <div className="stats__inner container">
          {stats.map((stat, i) => (
            <div className="stat-card fade-in" key={i} style={{ animationDelay: `${i * 0.1}s` }}>
              <span className="stat-card__icon">{stat.icon}</span>
              <span className="stat-card__value">{stat.value}</span>
              <span className="stat-card__label">{stat.label}</span>
            </div>
          ))}
        </div>
      </section>

      {/* ═══ PROMOS ═══ */}
      <section className="promos section" id="promos-section">
        <div className="promos__inner container">
          <div className="promos__grid">
            {promos.map(promo => (
              <Link
                to="/products"
                className={`promo-card promo-card--${promo.size}`}
                key={promo.id}
                style={{ backgroundColor: promo.color }}
                id={`promo-card-${promo.id}`}
              >
                <div className="promo-card__content">
                  <span className="promo-card__tag">{promo.tag}</span>
                  <h3 className="promo-card__title">{promo.title}</h3>
                  <span className="promo-card__cta">
                    Shop Now <span>→</span>
                  </span>
                </div>
                <img src={promo.image} alt={promo.title} className="promo-card__image" />
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* ═══ CATEGORIES ═══ */}
      {categories.length > 0 && (
        <section className="categories-section section" id="categories-section">
          <div className="container">
            <div className="section-header">
              <h2 className="section-header__title">Shop by Category</h2>
              <p className="section-header__subtitle">Find exactly what your pet needs</p>
            </div>
            <div className="categories-grid">
              {categories.map((cat, i) => (
                <Link
                  to={`/products?category=${cat.id}`}
                  className="category-card fade-in"
                  key={cat.id}
                  style={{ animationDelay: `${i * 0.1}s` }}
                  id={`category-card-${cat.id}`}
                >
                  <span className="category-card__icon">
                    {cat.name?.toLowerCase().includes('dog') ? '🐕' :
                     cat.name?.toLowerCase().includes('cat') ? '🐈' :
                     cat.name?.toLowerCase().includes('bird') ? '🦜' :
                     cat.name?.toLowerCase().includes('fish') ? '🐠' : '🐾'}
                  </span>
                  <span className="category-card__name">{cat.name}</span>
                </Link>
              ))}
            </div>
          </div>
        </section>
      )}

      {/* ═══ FEATURED PRODUCTS ═══ */}
      <section className="featured section" id="featured-section">
        <div className="container">
          <div className="section-header">
            <h2 className="section-header__title">Featured Products</h2>
            <p className="section-header__subtitle">Top picks for your beloved companions</p>
          </div>
          {loading ? (
            <div className="spinner"></div>
          ) : products.length > 0 ? (
            <div className="products-grid">
              {products.map(product => (
                <ProductCard key={product.id} product={product} />
              ))}
            </div>
          ) : (
            <p className="empty-text">Products coming soon! 🐾</p>
          )}
          <div className="featured__action">
            <Link to="/products" className="btn btn-secondary" id="view-all-products-btn">
              View All Products →
            </Link>
          </div>
        </div>
      </section>

      {/* ═══ NEWSLETTER ═══ */}
      <section className="newsletter section" id="newsletter-section">
        <div className="newsletter__inner container">
          <div className="newsletter__content">
            <h2 className="newsletter__title">Stay Pawsitive! 🐾</h2>
            <p className="newsletter__text">
              Subscribe for exclusive deals, pet care tips, and new arrivals.
            </p>
          </div>
          <form className="newsletter__form" onSubmit={(e) => e.preventDefault()}>
            <input
              type="email"
              placeholder="Enter your email address"
              className="newsletter__input"
              id="newsletter-email"
            />
            <button type="submit" className="btn btn-primary" id="newsletter-submit-btn">
              Subscribe
            </button>
          </form>
        </div>
      </section>
    </div>
  );
}
