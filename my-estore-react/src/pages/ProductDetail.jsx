import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getProduct, addToCart, checkInStock, getProductReviews, getAverageRating, createReview } from '../services/api';
import ReviewCard from '../components/ReviewCard';
import './ProductDetail.css';

export default function ProductDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [inStock, setInStock] = useState(true);
  const [reviews, setReviews] = useState([]);
  const [avgRating, setAvgRating] = useState(0);
  const [loading, setLoading] = useState(true);
  const [adding, setAdding] = useState(false);
  const [added, setAdded] = useState(false);
  const [reviewForm, setReviewForm] = useState({ rating: 5, comment: '' });
  const [reviewMsg, setReviewMsg] = useState('');

  useEffect(() => {
    setLoading(true);
    Promise.all([
      getProduct(id),
      checkInStock(id).catch(() => ({ data: true })),
      getProductReviews(id).catch(() => ({ data: [] })),
      getAverageRating(id).catch(() => ({ data: 0 })),
    ]).then(([prodRes, stockRes, revRes, ratingRes]) => {
      setProduct(prodRes.data);
      setInStock(stockRes.data);
      setReviews(Array.isArray(revRes.data) ? revRes.data : []);
      setAvgRating(typeof ratingRes.data === 'number' ? ratingRes.data : ratingRes.data?.averageRating || 0);
      setLoading(false);
    }).catch(() => setLoading(false));
  }, [id]);

  const handleAdd = async () => {
    if (!userId) { navigate('/login'); return; }
    setAdding(true);
    try {
      await addToCart(userId, { productId: product.id, quantity });
      setAdded(true);
      setTimeout(() => setAdded(false), 2000);
    } catch (err) { console.error(err); }
    setAdding(false);
  };

  const handleReview = async (e) => {
    e.preventDefault();
    if (!userId) { navigate('/login'); return; }
    try {
      await createReview(userId, { productId: Number(id), ...reviewForm });
      setReviewMsg('Review submitted!');
      setReviewForm({ rating: 5, comment: '' });
      const r = await getProductReviews(id);
      setReviews(Array.isArray(r.data) ? r.data : []);
    } catch { setReviewMsg('Failed to submit review.'); }
  };

  if (loading) return <div className="spinner" style={{ marginTop: '120px' }}></div>;
  if (!product) return <div className="detail-empty container">Product not found.</div>;

  const imageUrl = product.imageUrl || product.image || 'https://via.placeholder.com/500x500?text=🐾';

  return (
    <div className="detail-page" id="product-detail-page">
      <div className="container">
        <div className="detail-grid fade-in">
          <div className="detail-image-wrap">
            <img src={imageUrl} alt={product.name} className="detail-image" />
          </div>
          <div className="detail-info">
            <h1 className="detail-name">{product.name}</h1>
            <div className="detail-rating">
              <span className="stars">{'★'.repeat(Math.round(avgRating))}{'☆'.repeat(5 - Math.round(avgRating))}</span>
              <span className="detail-rating-text">{avgRating.toFixed(1)} ({reviews.length} reviews)</span>
            </div>
            <p className="detail-price">{(product.price || 0).toFixed(2)} DH</p>
            <p className="detail-desc">{product.description}</p>
            <div className={`detail-stock ${inStock ? 'detail-stock--in' : 'detail-stock--out'}`}>
              {inStock ? '✓ In Stock' : '✗ Out of Stock'}
            </div>
            <div className="detail-actions">
              <div className="detail-qty">
                <button className="detail-qty__btn" onClick={() => setQuantity(Math.max(1, quantity - 1))}>−</button>
                <span className="detail-qty__val">{quantity}</span>
                <button className="detail-qty__btn" onClick={() => setQuantity(quantity + 1)}>+</button>
              </div>
              <button className={`btn btn-primary ${added ? 'btn--added' : ''}`} onClick={handleAdd} disabled={adding || !inStock} id="detail-add-cart">
                {added ? '✓ Added to Cart' : adding ? 'Adding...' : '🛒 Add to Cart'}
              </button>
            </div>
          </div>
        </div>

        {/* Reviews */}
        <section className="detail-reviews" id="reviews-section">
          <h2 className="detail-reviews__title">Customer Reviews</h2>
          {userId && (
            <form onSubmit={handleReview} className="review-form">
              <div className="review-form__row">
                <label>Rating:</label>
                <select value={reviewForm.rating} onChange={e => setReviewForm({ ...reviewForm, rating: Number(e.target.value) })}>
                  {[5, 4, 3, 2, 1].map(n => <option key={n} value={n}>{n} ★</option>)}
                </select>
              </div>
              <textarea placeholder="Write your review..." value={reviewForm.comment} onChange={e => setReviewForm({ ...reviewForm, comment: e.target.value })} rows={3} />
              <button type="submit" className="btn btn-primary btn-sm">Submit Review</button>
              {reviewMsg && <span className="review-form__msg">{reviewMsg}</span>}
            </form>
          )}
          {reviews.length > 0 ? (
            <div className="reviews-list">
              {reviews.map((r, i) => <ReviewCard key={r.id || i} review={r} />)}
            </div>
          ) : (
            <p className="empty-text">No reviews yet. Be the first! 🐾</p>
          )}
        </section>
      </div>
    </div>
  );
}
