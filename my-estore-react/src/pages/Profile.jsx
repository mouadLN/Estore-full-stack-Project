import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getProfile, createProfile } from '../services/api';
import './Profile.css';

export default function Profile() {
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [editing, setEditing] = useState(false);
  const [form, setForm] = useState({ phone: '', address: '', city: '', country: '' });
  const [msg, setMsg] = useState('');

  useEffect(() => {
    if (!userId) { navigate('/login'); return; }
    getProfile(userId)
      .then(res => {
        if (res.data) {
          setProfile(res.data);
          setForm({
            phone: res.data.phone || '',
            address: res.data.address || '',
            city: res.data.city || '',
            country: res.data.country || '',
          });
        } else {
          setEditing(true);
        }
      })
      .catch(() => setEditing(true))
      .finally(() => setLoading(false));
  }, [userId]);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMsg('');
    try {
      const res = await createProfile(userId, form);
      setProfile(res.data);
      setEditing(false);
      setMsg('Profile saved successfully! ✓');
    } catch { setMsg('Failed to save profile.'); }
  };

  if (loading) return <div className="spinner" style={{ marginTop: '120px' }}></div>;

  return (
    <div className="profile-page" id="profile-page">
      <div className="container">
        <div className="profile-card fade-in">
          <div className="profile-card__header">
            <div className="profile-card__avatar">
              {userId?.toString().charAt(0).toUpperCase() || 'U'}
            </div>
            <div>
              <h1 className="profile-card__title">My Profile</h1>
              <p className="profile-card__uid">User ID: {userId}</p>
            </div>
          </div>

          {msg && <div className={`alert ${msg.includes('✓') ? 'alert-success' : 'alert-error'}`}>{msg}</div>}

          {!editing && profile ? (
            <div className="profile-info">
              <div className="profile-info__grid">
                <div className="profile-info__item">
                  <span className="profile-info__label">📞 Phone</span>
                  <span className="profile-info__value">{profile.phone || '—'}</span>
                </div>
                <div className="profile-info__item">
                  <span className="profile-info__label">📍 Address</span>
                  <span className="profile-info__value">{profile.address || '—'}</span>
                </div>
                <div className="profile-info__item">
                  <span className="profile-info__label">🏙️ City</span>
                  <span className="profile-info__value">{profile.city || '—'}</span>
                </div>
                <div className="profile-info__item">
                  <span className="profile-info__label">🌍 Country</span>
                  <span className="profile-info__value">{profile.country || '—'}</span>
                </div>
              </div>
              <button className="btn btn-secondary" onClick={() => setEditing(true)} id="edit-profile-btn">
                Edit Profile
              </button>
            </div>
          ) : (
            <form onSubmit={handleSubmit} className="profile-form">
              <h2 className="profile-form__title">{profile ? 'Edit Profile' : 'Create Your Profile'}</h2>
              <div className="form-row">
                <div className="form-group">
                  <label className="form-label" htmlFor="prof-phone">Phone</label>
                  <input id="prof-phone" name="phone" placeholder="+1 234 567 890" value={form.phone} onChange={handleChange} />
                </div>
                <div className="form-group">
                  <label className="form-label" htmlFor="prof-country">Country</label>
                  <input id="prof-country" name="country" placeholder="United States" value={form.country} onChange={handleChange} />
                </div>
              </div>
              <div className="form-group">
                <label className="form-label" htmlFor="prof-address">Address</label>
                <input id="prof-address" name="address" placeholder="123 Pet Street" value={form.address} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label className="form-label" htmlFor="prof-city">City</label>
                <input id="prof-city" name="city" placeholder="Casablanca" value={form.city} onChange={handleChange} />
              </div>
              <div className="profile-form__actions">
                <button type="submit" className="btn btn-primary" id="save-profile-btn">Save Profile</button>
                {profile && <button type="button" className="btn btn-secondary" onClick={() => setEditing(false)}>Cancel</button>}
              </div>
            </form>
          )}
        </div>
      </div>
    </div>
  );
}
