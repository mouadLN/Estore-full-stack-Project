import { useState } from 'react';
import { Link } from 'react-router-dom';
import { addToCart } from '../services/api';
import './ProductCard.css';

export default function ProductCard({ product }) {
  const [adding, setAdding] = useState(false);
  const [added, setAdded] = useState(false);
  const userId = localStorage.getItem('userId');

  const handleAddToCart = async (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (!userId) {
      window.location.href = '/login';
      return;
    }
    setAdding(true);
    try {
      await addToCart(userId, { productId: product.id, quantity: 1 });
      setAdded(true);
      setTimeout(() => setAdded(false), 2000);
    } catch (err) {
      console.error('Failed to add to cart', err);
    } finally {
      setAdding(false);
    }
  };

  const price = product.price || 0;
  const imageUrl = product.imageUrl || product.image || 'https://via.placeholder.com/300x300?text=🐾';

  return (
    <Link to={`/products/${product.id}`} className="product-card" id={`product-card-${product.id}`}>
      <div className="product-card__image-wrap">
        <img src={imageUrl} alt={product.name} className="product-card__image" loading="lazy" />
        <div className="product-card__overlay">
          <span className="product-card__view">View Details</span>
        </div>
      </div>

      <div className="product-card__body">
        <h3 className="product-card__name">{product.name}</h3>
        {product.category && (
          <span className="product-card__category">{product.category.name || product.category}</span>
        )}
        <div className="product-card__footer">
          <span className="product-card__price">{price.toFixed(2)} DH</span>
          <button
            className={`product-card__btn ${added ? 'product-card__btn--added' : ''}`}
            onClick={handleAddToCart}
            disabled={adding}
            id={`add-to-cart-${product.id}`}
          >
            {added ? '✓ Added' : adding ? '...' : '+ Cart'}
          </button>
        </div>
      </div>
    </Link>
  );
}
