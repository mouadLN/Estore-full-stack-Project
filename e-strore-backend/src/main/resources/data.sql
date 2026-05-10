-- ========================================
-- 1. USERS
-- ========================================
INSERT INTO users (first_name, last_name, email, password) VALUES
('Mouad', 'LAJANE', 'lajanemouad@gmail.com', '123456'),
('Assia', 'LAGNADI', 'assialagnadi@gmail.com', '123456'),
('Ahmed', 'Benali', 'ahmed@petstore.com', '123456'),
('Sara', 'Ouazzani', 'sara@petstore.com', '123456'),
('Karim', 'El Fassi', 'karim@petstore.com', '123456'),
('Fatima', 'Zahra', 'fatima@petstore.com', '123456'),
('Youssef', 'Alaoui', 'youssef@petstore.com', '123456');

-- ========================================
-- 2. PROFILES
-- ========================================
INSERT INTO profiles (phone, address, city, country, user_id) VALUES
('+212 611223344', '15 Rue Mohamed V', 'Casablanca', 'Morocco', 1),
('+212 622334455', '8 Avenue Hassan II', 'Rabat', 'Morocco', 2),
('+212 633445566', '25 Rue de la Liberté', 'Marrakech', 'Morocco', 3),
('+212 644556677', '42 Boulevard Mohammed VI', 'Fes', 'Morocco', 4),
('+212 655667788', '10 Rue des Orangers', 'Tanger', 'Morocco', 5),
('+212 666778899', '3 Rue des Oliviers', 'Agadir', 'Morocco', 6),
('+212 677889900', '7 Avenue des FAR', 'Casablanca', 'Morocco', 7);

-- ========================================
-- 3. CATEGORIES
-- ========================================
INSERT INTO categories (name, description) VALUES
('Dog Food', 'Premium food for dogs of all ages and sizes'),
('Cat Food', 'Nutritious food for cats and kittens'),
('Dog Accessories', 'Collars, leashes, toys, beds and more for dogs'),
('Cat Accessories', 'Scratchers, toys, beds and accessories for cats'),
('Small Animals', 'Food and accessories for rabbits, hamsters, guinea pigs'),
('Bird Supplies', 'Cages, food, perches and toys for birds'),
('Fish Supplies', 'Aquariums, filters, food and decorations for fish'),
('Health & Wellness', 'Vitamins, supplements, grooming and healthcare products'),
('Training & Behavior', 'Training pads, clickers, treats and behavior aids');

-- ========================================
-- 4. PRODUCTS
-- ========================================
INSERT INTO products (name, description, price, image_url, category_id) VALUES
('Premium Adult Dog Food - Chicken 15kg', 'High protein, grain-free formula for active adult dogs', 450.00, '/images/products/Premium Adult Dog Food - Chicken 15kg.jpg', 1),
('Puppy Growth Formula - Lamb 10kg', 'Essential nutrients for healthy puppy development', 380.00, '/images/products/Puppy Growth Formula - Lamb 10kg.jpg', 1),
('Senior Dog Food - Salmon 12kg', 'Easy to digest with joint support for senior dogs', 420.00, '/images/products/Senior Dog Food - Salmon 12kg.jpg', 1),
('Premium Adult Cat Food - Tuna 10kg', 'Rich in omega-3 for healthy skin and coat', 350.00, '/images/products/Premium Adult Cat Food - Tuna 10kg.jpg', 2),
('Kitten Formula - Chicken 10kg', 'DHA for brain development', 280.00, '/images/products/Kitten Formula - Chicken 10kg.jpg', 2),
('Nylon Dog Collar - Adjustable', 'Durable with reflective stitching for night walks', 45.00, '/images/products/Nylon Dog Collar - Adjustable.jpg', 3),
('Orthopedic Dog Bed - Memory Foam', 'Ergonomic support for joints', 350.00, '/images/products/Orthopedic Dog Bed - Memory Foam.jpg', 3),
('Rubber Dog Chew Toy - Dental', 'Cleans teeth and massages gums', 35.00, '/images/products/Rubber Dog Chew Toy - Dental.jpg', 3),
('Cat Scratching Post - 80cm', 'Sturdy with hanging toy', 150.00, '/images/products/Cat Scratching Post - 80cm.jpg', 4),
('Cat Bed - Donut Style', 'Warm and cozy for napping', 120.00, '/images/products/Cat Bed - Donut Style.jpg', 4),
('Interactive Cat Wand Toy', 'Feathers and bells for hunting instinct', 45.00, '/images/products/Interactive Cat Wand Toy.jpg', 4),
('Aquarium Starter Kit - 60L', 'Includes filter, heater and LED light', 650.00, '/images/products/Aquarium Starter Kit - 60L.jpg', 7),
('Fish Food Flakes - Tropical 250g', 'Balanced nutrition for tropical fish', 45.00, '/images/products/Fish Food Flakes - Tropical 250g.jpg', 7),
('Bird Cage - Large', 'Includes perches and feeding doors', 380.00, '/images/products/Bird Cage - Large.jpg', 6),
('Bird Perch - Natural Wood', 'Helps maintain healthy feet', 35.00, '/images/products/Bird Perch - Natural Wood.jpg', 6);

-- ========================================
-- 5. INVENTORY
-- ========================================
INSERT INTO inventory (quantity, product_id) VALUES
(50, 1), (40, 2), (35, 3), (55, 4), (45, 5),
(100, 6), (80, 7), (30, 8), (120, 9), (75, 10),
(60, 11), (85, 12), (45, 13), (40, 14), (90, 15);

-- ========================================
-- 6. CARTS
-- ========================================
INSERT INTO carts (user_id) VALUES (1), (2), (3), (4), (5), (6), (7);

-- ========================================
-- 7. ORDERS
-- ========================================
INSERT INTO orders (order_date, status, total_amount, user_id) VALUES
('2026-04-15 10:30:00', 'DELIVERED', 535.00, 1),
('2026-04-20 14:45:00', 'DELIVERED', 890.00, 2),
('2026-04-25 09:15:00', 'SHIPPED', 350.00, 3),
('2026-05-01 16:20:00', 'CONFIRMED', 1200.00, 4),
('2026-05-05 11:00:00', 'PENDING', 225.00, 5);

-- ========================================
-- 8. ORDER ITEMS
-- ========================================
INSERT INTO order_items (quantity, price, subtotal, order_id, product_id) VALUES
(1, 450.00, 450.00, 1, 1),
(1, 35.00, 35.00, 1, 8),
(2, 25.00, 50.00, 1, 15),
(1, 380.00, 380.00, 2, 2),
(1, 350.00, 350.00, 2, 9),
(1, 160.00, 160.00, 2, 11),
(1, 350.00, 350.00, 3, 4),
(1, 650.00, 650.00, 4, 13),
(1, 350.00, 350.00, 4, 7),
(1, 120.00, 120.00, 4, 10),
(1, 120.00, 120.00, 5, 12),
(1, 45.00, 45.00, 5, 14),
(1, 35.00, 35.00, 5, 6),
(1, 25.00, 25.00, 5, 15);

-- ========================================
-- 9. REVIEWS
-- ========================================
INSERT INTO reviews (product_id, user_id, user_email, user_name, rating, comment, created_at) VALUES
(1, 3, 'ahmed@petstore.com', 'Ahmed Benali', 5, 'Excellent dog food! My dog loves it!', '2026-04-20 10:30:00'),
(1, 4, 'sara@petstore.com', 'Sara Ouazzani', 4, 'Good quality, a bit expensive', '2026-04-22 14:15:00'),
(2, 5, 'karim@petstore.com', 'Karim El Fassi', 5, 'Perfect for my puppy!', '2026-04-25 09:00:00'),
(4, 3, 'ahmed@petstore.com', 'Ahmed Benali', 5, 'My cat loves this tuna food!', '2026-04-28 16:45:00'),
(6, 6, 'fatima@petstore.com', 'Fatima Zahra', 4, 'Very durable collar', '2026-05-01 11:20:00'),
(9, 7, 'youssef@petstore.com', 'Youssef Alaoui', 5, 'My cat plays with this every day!', '2026-05-05 13:30:00');