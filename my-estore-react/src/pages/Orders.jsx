import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUserOrders } from '../services/api';
import './Orders.css';

export default function Orders() {
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!userId) { navigate('/login'); return; }
    getUserOrders(userId)
      .then(res => setOrders(Array.isArray(res.data) ? res.data : []))
      .catch(() => setOrders([]))
      .finally(() => setLoading(false));
  }, [userId]);

  const getStatusClass = (status) => {
    const s = (status || '').toLowerCase();
    if (s.includes('deliver')) return 'badge-delivered';
    if (s.includes('ship')) return 'badge-shipped';
    if (s.includes('cancel')) return 'badge-cancelled';
    return 'badge-pending';
  };

  if (loading) return <div className="spinner" style={{ marginTop: '120px' }}></div>;

  return (
    <div className="orders-page" id="orders-page">
      <div className="container">
        <h1 className="orders-page__title">My Orders 📦</h1>

        {orders.length === 0 ? (
          <div className="orders-empty fade-in">
            <span className="orders-empty__icon">📦</span>
            <h2>No orders yet</h2>
            <p>Start shopping to see your orders here!</p>
          </div>
        ) : (
          <div className="orders-list fade-in">
            {orders.map((order, i) => (
              <div className="order-card" key={order.id || i} id={`order-card-${order.id || i}`}>
                <div className="order-card__header">
                  <div>
                    <span className="order-card__id">Order #{order.id}</span>
                    <span className="order-card__date">
                      {order.createdAt ? new Date(order.createdAt).toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' }) : 'N/A'}
                    </span>
                  </div>
                  <span className={`badge ${getStatusClass(order.status)}`}>
                    {order.status || 'PENDING'}
                  </span>
                </div>
                {(order.items || order.orderItems || []).length > 0 && (
                  <div className="order-card__items">
                    {(order.items || order.orderItems || []).map((item, j) => (
                      <div className="order-card__item" key={j}>
                        <span className="order-card__item-name">{item.productName || item.name || 'Product'}</span>
                        <span className="order-card__item-qty">×{item.quantity || 1}</span>
                        <span className="order-card__item-price">{(item.price || 0).toFixed(2)} DH</span>
                      </div>
                    ))}
                  </div>
                )}
                <div className="order-card__footer">
                  <span className="order-card__total">Total: <strong>{(order.totalAmount || order.total || 0).toFixed(2)} DH</strong></span>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
