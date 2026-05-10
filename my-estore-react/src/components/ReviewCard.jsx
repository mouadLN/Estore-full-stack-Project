import './ReviewCard.css';

export default function ReviewCard({ review }) {
  const renderStars = (rating) => {
    return Array.from({ length: 5 }, (_, i) => (
      <span key={i} className={i < rating ? 'star star--filled' : 'star'}>★</span>
    ));
  };

  return (
    <div className="review-card">
      <div className="review-card__header">
        <div className="review-card__avatar">
          {(review.userName || review.userId || 'U').toString().charAt(0).toUpperCase()}
        </div>
        <div className="review-card__meta">
          <span className="review-card__user">{review.userName || `User ${review.userId}`}</span>
          <div className="review-card__stars">{renderStars(review.rating || 0)}</div>
        </div>
        {review.createdAt && (
          <span className="review-card__date">
            {new Date(review.createdAt).toLocaleDateString()}
          </span>
        )}
      </div>
      {review.comment && (
        <p className="review-card__comment">{review.comment}</p>
      )}
    </div>
  );
}
