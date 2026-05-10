import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// ─── AUTH ────────────────────────────────────────────
export const register = (data) =>
  API.post('/auth/register', data);

export const login = (data) =>
  API.post('/auth/login', data);

// ─── PROFILE ────────────────────────────────────────
export const getProfile = (userId) =>
  API.get(`/profiles/user/${userId}`);

export const createProfile = (userId, data) =>
  API.post(`/profiles/user/${userId}`, data);

// ─── CATEGORIES ─────────────────────────────────────
export const getCategories = () =>
  API.get('/categories');

export const getCategory = (id) =>
  API.get(`/categories/${id}`);

// ─── PRODUCTS ───────────────────────────────────────
export const getProductDetails = (id) =>
    API.get(`/products/${id}/details`);

export const getProducts = () =>
  API.get('/products');

export const getProduct = (id) =>
  API.get(`/products/${id}`);

export const getProductsByCategory = (categoryId) =>
  API.get(`/products/category/${categoryId}`);

export const searchProducts = (keyword) =>
  API.get(`/products/search?keyword=${encodeURIComponent(keyword)}`);

// ─── INVENTORY ──────────────────────────────────────
export const getInventory = () =>
  API.get('/inventory');

export const getProductInventory = (productId) =>
  API.get(`/inventory/product/${productId}`);

export const checkInStock = (productId) =>
  API.get(`/inventory/product/${productId}/instock`);

// ─── CART ───────────────────────────────────────────
export const getCart = (userId) =>
  API.get(`/cart/user/${userId}`);

export const addToCart = (userId, data) =>
  API.post(`/cart/user/${userId}/add`, data);

export const updateCartItem = (userId, itemId, quantity) =>
  API.put(`/cart/user/${userId}/item/${itemId}?quantity=${quantity}`);

export const removeFromCart = (userId, itemId) =>
  API.delete(`/cart/user/${userId}/item/${itemId}`);

export const clearCart = (userId) =>
  API.delete(`/cart/user/${userId}/clear`);

// ─── ORDERS ─────────────────────────────────────────
export const checkout = (userId) =>
  API.post(`/orders/user/${userId}/checkout`);

export const getOrder = (orderId) =>
  API.get(`/orders/${orderId}`);

export const getUserOrders = (userId) =>
  API.get(`/orders/user/${userId}`);

export const getAllOrders = () =>
  API.get('/orders');

export const updateOrderStatus = (orderId, status) =>
  API.put(`/orders/${orderId}/status?status=${status}`);

// ─── REVIEWS ────────────────────────────────────────
export const createReview = (userId, data) =>
  API.post(`/reviews/user/${userId}`, data);

export const getProductReviews = (productId) =>
  API.get(`/reviews/product/${productId}`);

export const getUserReviews = (userId) =>
  API.get(`/reviews/user/${userId}`);

export const getAverageRating = (productId) =>
  API.get(`/reviews/product/${productId}/average-rating`);

export const deleteReview = (reviewId) =>
  API.delete(`/reviews/${reviewId}`);

export default API;
