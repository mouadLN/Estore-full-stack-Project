import { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { getCart, updateCartItem, removeFromCart, clearCart, checkout } from '../services/api';
import './Cart.css';

export default function Cart() {
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');
  const [cart, setCart] = useState(null);
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [checkingOut, setCheckingOut] = useState(false);
  const [msg, setMsg] = useState('');

  useEffect(() => {
    if (!userId) { navigate('/login'); return; }
    fetchCart();
  }, [userId]);

  const fetchCart = async () => {
    setLoading(true);
    try {
      const res = await getCart(userId);
      setCart(res.data);
      setItems(res.data?.items || res.data?.cartItems || []);
    } catch { setItems([]); }
    setLoading(false);
  };

  const handleUpdate = async (itemId, newQty) => {
    if (newQty < 1) return;
    try {
      await updateCartItem(userId, itemId, newQty);
      fetchCart();
    } catch (err) { console.error(err); }
  };

  const handleRemove = async (itemId) => {
    try {
      await removeFromCart(userId, itemId);
      fetchCart();
    } catch (err) { console.error(err); }
  };

  const handleClear = async () => {
    try {
      await clearCart(userId);
      fetchCart();
    } catch (err) { console.error(err); }
  };

  const handleCheckout = async () => {
    setCheckingOut(true);
    try {
      await checkout(userId);
      setMsg('Order placed successfully! 🎉');
      setItems([]);
      setTimeout(() => navigate('/orders'), 2000);
    } catch { setMsg('Checkout failed. Try again.'); }
    setCheckingOut(false);
  };

  const total = items.reduce((sum, item) => {
    const price = item.productPrice || item.price || item.product?.price || 0;
    return sum + price * (item.quantity || 1);
  }, 0);

  if (loading) return <div className="spinner" style={{ marginTop: '120px' }}></div>;

  return (
    <div className="cart-page" id="cart-page">
      <div className="container">
        <h1 className="cart-page__title">Shopping Cart 🛒</h1>

        {msg && <div className="alert alert-success">{msg}</div>}

        {items.length === 0 ? (
          <div className="cart-empty fade-in">
            <span className="cart-empty__icon">🛒</span>
            <h2>Your cart is empty</h2>
            <p>Looks like you haven't added anything yet.</p>
            <Link to="/products" className="btn btn-primary">Start Shopping</Link>
          </div>
        ) : (
          <div className="cart-layout fade-in">
            <div className="cart-items">
              {items.map((item, i) => {
                const name = item.productName || item.product?.name || item.name || 'Product';
                const price = item.productPrice || item.price || item.product?.price || 0;
                const img = item.imageUrl || item.product?.imageUrl || 'https://via.placeholder.com/80x80?text=🐾';
                const itemId = item.id || item.itemId || i;
                return (
                  <div className="cart-item" key={itemId} id={`cart-item-${itemId}`}>
                    <img src={img} alt={name} className="cart-item__img" />
                    <div className="cart-item__info">
                      <h3 className="cart-item__name">{name}</h3>
                      <span className="cart-item__price">{price.toFixed(2)} DH</span>
                    </div>
                    <div className="cart-item__qty">
                      <button className="cart-item__qty-btn" onClick={() => handleUpdate(itemId, (item.quantity || 1) - 1)}>−</button>
                      <span>{item.quantity || 1}</span>
                      <button className="cart-item__qty-btn" onClick={() => handleUpdate(itemId, (item.quantity || 1) + 1)}>+</button>
                    </div>
                    <span className="cart-item__subtotal">{(price * (item.quantity || 1)).toFixed(2)} DH</span>
                    <button className="cart-item__remove" onClick={() => handleRemove(itemId)} title="Remove">✕</button>
                  </div>
                );
              })}
            </div>

            <div className="cart-summary">
              <h3 className="cart-summary__title">Order Summary</h3>
              <div className="cart-summary__row">
                <span>Items ({items.length})</span>
                <span>${total.toFixed(2)}</span>
              </div>
              <div className="cart-summary__row">
                <span>Shipping</span>
                <span className="cart-summary__free">Free</span>
              </div>
              <div className="cart-summary__divider"></div>
              <div className="cart-summary__row cart-summary__total">
                <span>Total</span>
                <span>${total.toFixed(2)}</span>
              </div>
              <button className="btn btn-primary cart-summary__checkout" onClick={handleCheckout} disabled={checkingOut} id="checkout-btn">
                {checkingOut ? 'Processing...' : 'Checkout →'}
              </button>
              <button className="btn btn-secondary btn-sm" onClick={handleClear} style={{ width: '100%' }} id="clear-cart-btn">
                Clear Cart
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
