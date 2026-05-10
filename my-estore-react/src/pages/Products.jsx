import { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { getProducts, getCategories, searchProducts, getProductsByCategory } from '../services/api';
import ProductCard from '../components/ProductCard';
import './Products.css';

export default function Products() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [activeCategory, setActiveCategory] = useState(null);
  const [searchParams] = useSearchParams();

  useEffect(() => {
    getCategories().then(r => setCategories(Array.isArray(r.data) ? r.data : [])).catch(() => {});
  }, []);

  useEffect(() => {
    const catParam = searchParams.get('category');
    if (catParam) {
      setActiveCategory(Number(catParam));
      fetchByCategory(Number(catParam));
    } else {
      fetchAll();
    }
  }, [searchParams]);

  const fetchAll = async () => {
    setLoading(true);
    try {
      const res = await getProducts();
      setProducts(Array.isArray(res.data) ? res.data : []);
    } catch { setProducts([]); }
    setLoading(false);
  };

  const fetchByCategory = async (catId) => {
    setLoading(true);
    try {
      const res = await getProductsByCategory(catId);
      setProducts(Array.isArray(res.data) ? res.data : []);
    } catch { setProducts([]); }
    setLoading(false);
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!search.trim()) { fetchAll(); return; }
    setLoading(true);
    setActiveCategory(null);
    try {
      const res = await searchProducts(search);
      setProducts(Array.isArray(res.data) ? res.data : []);
    } catch { setProducts([]); }
    setLoading(false);
  };

  const handleCategoryClick = (catId) => {
    if (activeCategory === catId) {
      setActiveCategory(null);
      fetchAll();
    } else {
      setActiveCategory(catId);
      fetchByCategory(catId);
    }
    setSearch('');
  };

  return (
    <div className="shop-page" id="products-page">
      <div className="shop-page__header">
        <div className="container">
          <h1 className="shop-page__title">Our Shop</h1>
          <p className="shop-page__subtitle">Discover premium products for your beloved pets</p>
        </div>
      </div>

      <div className="shop-page__body container">
        {/* Sidebar */}
        <aside className="shop-sidebar" id="shop-sidebar">
          <form onSubmit={handleSearch} className="shop-search">
            <input type="text" placeholder="Search products..." value={search} onChange={e => setSearch(e.target.value)} className="shop-search__input" id="search-input" />
            <button type="submit" className="shop-search__btn" id="search-btn">🔍</button>
          </form>

          {categories.length > 0 && (
            <div className="shop-categories">
              <h3 className="shop-categories__title">Categories</h3>
              <ul className="shop-categories__list">
                <li>
                  <button className={`shop-categories__item ${!activeCategory ? 'shop-categories__item--active' : ''}`} onClick={() => { setActiveCategory(null); fetchAll(); }}>
                    All Products
                  </button>
                </li>
                {categories.map(cat => (
                  <li key={cat.id}>
                    <button className={`shop-categories__item ${activeCategory === cat.id ? 'shop-categories__item--active' : ''}`} onClick={() => handleCategoryClick(cat.id)}>
                      {cat.name}
                    </button>
                  </li>
                ))}
              </ul>
            </div>
          )}
        </aside>

        {/* Products Grid */}
        <main className="shop-content">
          <div className="shop-content__top">
            <span className="shop-content__count">{products.length} products found</span>
          </div>
          {loading ? (
            <div className="spinner"></div>
          ) : products.length > 0 ? (
            <div className="products-grid">
              {products.map(p => <ProductCard key={p.id} product={p} />)}
            </div>
          ) : (
            <div className="shop-empty">
              <span className="shop-empty__icon">🔍</span>
              <p>No products found</p>
            </div>
          )}
        </main>
      </div>
    </div>
  );
}
