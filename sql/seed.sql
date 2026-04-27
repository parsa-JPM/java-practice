-- ============================================================
-- Practice Dataset: E-Commerce Store
-- Covers: B-tree / Hash / GIN / BRIN indexes, views,
--         materialized views, cross join, window functions,
--         CTEs, full-text search, JSONB
-- ============================================================

-- ────────────────────────────────────────────────────────────
-- EXTENSIONS
-- ────────────────────────────────────────────────────────────
CREATE EXTENSION IF NOT EXISTS pg_trgm;   -- trigram GIN indexes
CREATE EXTENSION IF NOT EXISTS btree_gin; -- GIN on scalar types

-- ────────────────────────────────────────────────────────────
-- SCHEMA RESET
-- ────────────────────────────────────────────────────────────
DROP TABLE IF EXISTS inventory_log, reviews, order_items, orders,
                     products, categories, customers, employees,
                     departments CASCADE;

-- ────────────────────────────────────────────────────────────
-- TABLES
-- ────────────────────────────────────────────────────────────

CREATE TABLE departments (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE
);

CREATE TABLE employees (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    department_id INT REFERENCES departments(id),
    hire_date   DATE NOT NULL,
    salary      NUMERIC(10,2)
);

-- Self-referential hierarchy (good for recursive CTE)
CREATE TABLE categories (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    parent_id   INT  REFERENCES categories(id)
);

CREATE TABLE products (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    category_id INT  REFERENCES categories(id),
    price       NUMERIC(10,2) NOT NULL,
    stock       INT NOT NULL DEFAULT 0,
    description TEXT,
    tags        TEXT[],
    metadata    JSONB,
    search_vec  TSVECTOR,   -- for full-text search
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE customers (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    email       TEXT NOT NULL UNIQUE,
    country     TEXT NOT NULL,
    metadata    JSONB,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE orders (
    id          SERIAL PRIMARY KEY,
    customer_id INT  NOT NULL REFERENCES customers(id),
    status      TEXT NOT NULL DEFAULT 'pending'
                     CHECK (status IN ('pending','processing','shipped','delivered','cancelled')),
    total       NUMERIC(12,2),
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE order_items (
    id          SERIAL PRIMARY KEY,
    order_id    INT  NOT NULL REFERENCES orders(id),
    product_id  INT  NOT NULL REFERENCES products(id),
    quantity    INT  NOT NULL CHECK (quantity > 0),
    unit_price  NUMERIC(10,2) NOT NULL
);

CREATE TABLE reviews (
    id          SERIAL PRIMARY KEY,
    product_id  INT  NOT NULL REFERENCES products(id),
    customer_id INT  NOT NULL REFERENCES customers(id),
    rating      SMALLINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    body        TEXT,
    search_vec  TSVECTOR,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- High-volume time-series table (good for BRIN)
CREATE TABLE inventory_log (
    id          BIGSERIAL PRIMARY KEY,
    product_id  INT  NOT NULL REFERENCES products(id),
    change      INT  NOT NULL,   -- positive=restock, negative=sale
    reason      TEXT,
    logged_at   TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- ────────────────────────────────────────────────────────────
-- SEED DATA
-- ────────────────────────────────────────────────────────────

INSERT INTO departments (name) VALUES
    ('Engineering'), ('Marketing'), ('Sales'), ('Logistics'), ('Support');

INSERT INTO employees (name, department_id, hire_date, salary) VALUES
    ('Alice Chen',       1, '2019-03-15', 95000),
    ('Bob Martinez',     1, '2020-07-01', 88000),
    ('Carol White',      2, '2018-11-20', 72000),
    ('David Kim',        3, '2021-02-10', 68000),
    ('Eva Novak',        3, '2021-06-30', 71000),
    ('Frank Osei',       4, '2017-09-05', 62000),
    ('Grace Tanaka',     5, '2022-01-15', 58000),
    ('Hiro Patel',       1, '2023-03-01', 91000),
    ('Iris Johansson',   2, '2022-08-20', 74000),
    ('Jack O''Brien',    4, '2020-12-01', 65000);

-- Category hierarchy: root → sub-category
INSERT INTO categories (id, name, parent_id) VALUES
    (1,  'Electronics',       NULL),
    (2,  'Laptops',           1),
    (3,  'Smartphones',       1),
    (4,  'Accessories',       1),
    (5,  'Clothing',          NULL),
    (6,  'Men''s Clothing',   5),
    (7,  'Women''s Clothing', 5),
    (8,  'Home & Garden',     NULL),
    (9,  'Kitchen',           8),
    (10, 'Garden Tools',      8);

INSERT INTO products (name, category_id, price, stock, description, tags, metadata) VALUES
    ('ProBook 15 Laptop',     2,  1299.99, 45,  'Powerful 15" laptop with 16GB RAM and 512GB SSD. Perfect for developers and creatives.',
     ARRAY['laptop','portable','work'], '{"brand":"ProTech","warranty_years":2,"color":"silver"}'),

    ('UltraPhone X',          3,   899.99, 120, 'Flagship smartphone with 6.7" OLED display, 5G, and 108MP camera.',
     ARRAY['smartphone','5g','camera'], '{"brand":"UltraCo","warranty_years":1,"color":"black"}'),

    ('Wireless Earbuds Pro',  4,   149.99, 300, 'True wireless earbuds with active noise cancellation and 30h battery.',
     ARRAY['audio','wireless','anc'], '{"brand":"SoundWave","warranty_years":1,"color":"white"}'),

    ('USB-C Hub 7-in-1',      4,    49.99, 500, 'Compact USB-C hub with HDMI, SD card, and fast charging support.',
     ARRAY['hub','usb-c','adapter'], '{"brand":"ConnectAll","warranty_years":1,"color":"grey"}'),

    ('Slim Fit Chinos',       6,    59.99, 200, 'Classic slim-fit chino trousers in premium cotton. Available in multiple colors.',
     ARRAY['trousers','cotton','slim'], '{"brand":"UrbanWear","sizes":["S","M","L","XL"],"color":"khaki"}'),

    ('Summer Dress',          7,    79.99, 150, 'Lightweight floral summer dress made from breathable fabric.',
     ARRAY['dress','summer','floral'], '{"brand":"FloraFashion","sizes":["XS","S","M","L"],"color":"blue"}'),

    ('Chef''s Knife Set',     9,    89.99,  80, 'Professional 5-piece chef knife set with ergonomic handles and stainless steel blades.',
     ARRAY['knives','kitchen','professional'], '{"brand":"CutMaster","pieces":5,"material":"stainless steel"}'),

    ('Stainless Cookware Set', 9,   229.99,  60, '10-piece cookware set compatible with all stovetop types including induction.',
     ARRAY['cookware','induction','set'], '{"brand":"ChefElite","pieces":10,"material":"stainless steel"}'),

    ('Garden Hose 50ft',      10,   34.99, 180, 'Heavy-duty expandable garden hose with spray nozzle included.',
     ARRAY['garden','hose','outdoor'], '{"brand":"GreenThumb","length_ft":50,"color":"green"}'),

    ('Mechanical Keyboard',   4,   129.99, 130, 'Tenkeyless mechanical keyboard with Cherry MX switches and RGB backlight.',
     ARRAY['keyboard','mechanical','rgb'], '{"brand":"KeyCraft","switch":"Cherry MX Red","layout":"TKL"}'),

    ('Budget Laptop',         2,   599.99,  70, 'Entry-level 14" laptop ideal for students and everyday computing tasks.',
     ARRAY['laptop','budget','student'], '{"brand":"BasicTech","warranty_years":1,"color":"black"}'),

    ('SmartWatch Series 3',   4,   249.99,  90, 'Smartwatch with health tracking, GPS, and 7-day battery life.',
     ARRAY['smartwatch','gps','health'], '{"brand":"TimeTech","warranty_years":1,"color":"black"}');

-- Build tsvector for full-text search
UPDATE products SET search_vec = to_tsvector('english', name || ' ' || COALESCE(description,''));

INSERT INTO customers (name, email, country, metadata) VALUES
    ('Alice Johnson',    'alice@example.com',   'US', '{"tier":"gold","referral":"organic"}'),
    ('Bob Smith',        'bob@example.com',     'UK', '{"tier":"silver","referral":"ads"}'),
    ('Carlos Ruiz',      'carlos@example.com',  'ES', '{"tier":"bronze","referral":"social"}'),
    ('Diana Prince',     'diana@example.com',   'DE', '{"tier":"gold","referral":"email"}'),
    ('Erik Larsen',      'erik@example.com',    'DK', '{"tier":"silver","referral":"organic"}'),
    ('Fatima Hassan',    'fatima@example.com',  'AE', '{"tier":"bronze","referral":"ads"}'),
    ('George Tanaka',    'george@example.com',  'JP', '{"tier":"gold","referral":"organic"}'),
    ('Hannah Müller',    'hannah@example.com',  'DE', '{"tier":"silver","referral":"email"}'),
    ('Ivan Petrov',      'ivan@example.com',    'RU', '{"tier":"bronze","referral":"social"}'),
    ('Julia Santos',     'julia@example.com',   'BR', '{"tier":"gold","referral":"organic"}'),
    ('Kevin Okafor',     'kevin@example.com',   'NG', '{"tier":"silver","referral":"ads"}'),
    ('Laura Chen',       'laura@example.com',   'US', '{"tier":"gold","referral":"email"}');

-- Orders spread across time
INSERT INTO orders (customer_id, status, created_at) VALUES
    (1,  'delivered',   '2024-01-15 10:30:00+00'),
    (1,  'delivered',   '2024-04-22 14:00:00+00'),
    (2,  'delivered',   '2024-02-10 09:15:00+00'),
    (3,  'shipped',     '2024-06-05 16:45:00+00'),
    (4,  'delivered',   '2024-03-18 11:00:00+00'),
    (5,  'processing',  '2025-01-20 08:30:00+00'),
    (6,  'delivered',   '2024-07-30 13:20:00+00'),
    (7,  'delivered',   '2024-08-14 17:00:00+00'),
    (8,  'cancelled',   '2024-09-02 10:00:00+00'),
    (9,  'delivered',   '2024-10-11 12:45:00+00'),
    (10, 'shipped',     '2025-02-28 15:30:00+00'),
    (11, 'pending',     '2025-03-05 09:00:00+00'),
    (12, 'delivered',   '2024-11-20 14:15:00+00'),
    (1,  'delivered',   '2024-12-01 10:00:00+00'),
    (4,  'delivered',   '2025-01-08 11:30:00+00');

INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
    (1,  1,  1, 1299.99),
    (1,  3,  1,  149.99),
    (2,  10, 1,  129.99),
    (3,  2,  1,  899.99),
    (3,  4,  2,   49.99),
    (4,  5,  2,   59.99),
    (5,  7,  1,   89.99),
    (5,  8,  1,  229.99),
    (6,  12, 1,  249.99),
    (6,  3,  2,  149.99),
    (7,  9,  1,   34.99),
    (7,  6,  1,   79.99),
    (8,  11, 1,  599.99),
    (9,  4,  3,   49.99),
    (9,  10, 1,  129.99),
    (10, 2,  1,  899.99),
    (11, 1,  2, 1299.99),
    (12, 7,  1,   89.99),
    (12, 9,  2,   34.99),
    (13, 3,  1,  149.99),
    (13, 12, 1,  249.99),
    (14, 1,  1, 1299.99),
    (14, 4,  1,   49.99),
    (15, 8,  1,  229.99);

-- Update order totals
UPDATE orders o
SET total = (
    SELECT SUM(quantity * unit_price) FROM order_items WHERE order_id = o.id
);

INSERT INTO reviews (product_id, customer_id, rating, body) VALUES
    (1,  1, 5, 'Excellent laptop, runs all my dev tools smoothly. Build quality is outstanding.'),
    (1,  4, 4, 'Great performance but the fan gets loud under load. Otherwise perfect.'),
    (2,  3, 5, 'Best phone I have ever owned. Camera quality is breathtaking.'),
    (2, 10, 3, 'Good phone but battery drains faster than expected after updates.'),
    (3,  6, 5, 'Noise cancellation is superb. Crystal clear sound for calls and music.'),
    (3,  7, 4, 'Very comfortable fit. Wish the case were a bit more compact.'),
    (7,  5, 5, 'Sharpest knives I have used. Worth every penny for home cooking.'),
    (8,  4, 5, 'Even heat distribution and very easy to clean. Highly recommended.'),
    (10, 1, 4, 'Satisfying tactile feedback. RGB is a nice touch for my desk setup.'),
    (11, 9, 3, 'Decent budget laptop. Struggles with heavy multitasking but fine for basic work.'),
    (12, 7, 5, 'GPS is accurate and health metrics are detailed. Great everyday watch.');

UPDATE reviews SET search_vec = to_tsvector('english', COALESCE(body,''));

-- Inventory log: ~200 rows spread over 2 years (great for BRIN index practice)
INSERT INTO inventory_log (product_id, change, reason, logged_at)
SELECT
    (random()*11 + 1)::INT,
    CASE WHEN random() > 0.3 THEN -(random()*5+1)::INT ELSE (random()*50+10)::INT END,
    CASE WHEN random() > 0.3 THEN 'sale' ELSE 'restock' END,
    '2023-01-01 00:00:00+00'::timestamptz + (random() * INTERVAL '2 years')
FROM generate_series(1, 500);

-- ────────────────────────────────────────────────────────────
-- INDEXES  (the main event)
-- ────────────────────────────────────────────────────────────

-- B-tree (default) — range queries, sorting, equality
CREATE INDEX idx_orders_created_at      ON orders(created_at);
CREATE INDEX idx_orders_customer_status ON orders(customer_id, status);
CREATE INDEX idx_products_price         ON products(price);
CREATE INDEX idx_products_category      ON products(category_id);

-- Hash — fast equality-only lookups
CREATE INDEX idx_customers_email_hash   ON customers USING HASH (email);
CREATE INDEX idx_orders_status_hash     ON orders    USING HASH (status);

-- GIN — full-text search & JSONB & array containment
CREATE INDEX idx_products_search_vec    ON products USING GIN (search_vec);
CREATE INDEX idx_reviews_search_vec     ON reviews  USING GIN (search_vec);
CREATE INDEX idx_products_tags          ON products USING GIN (tags);
CREATE INDEX idx_products_metadata      ON products USING GIN (metadata);
CREATE INDEX idx_customers_metadata     ON customers USING GIN (metadata);

-- GIN trigram — fuzzy LIKE/ILIKE searches
CREATE INDEX idx_products_name_trgm     ON products USING GIN (name gin_trgm_ops);
CREATE INDEX idx_customers_name_trgm    ON customers USING GIN (name gin_trgm_ops);

-- BRIN — very efficient for naturally ordered large tables
CREATE INDEX idx_inventory_log_brin     ON inventory_log USING BRIN (logged_at);
CREATE INDEX idx_orders_brin            ON orders        USING BRIN (created_at);

-- ────────────────────────────────────────────────────────────
-- VIEWS
-- ────────────────────────────────────────────────────────────

-- Simple view: product catalogue with category name
CREATE OR REPLACE VIEW v_product_catalogue AS
SELECT
    p.id,
    p.name,
    c.name        AS category,
    p.price,
    p.stock,
    p.tags,
    p.metadata ->> 'brand' AS brand
FROM products p
JOIN categories c ON c.id = p.category_id;

-- View: order summary (join across 3 tables)
CREATE OR REPLACE VIEW v_order_summary AS
SELECT
    o.id          AS order_id,
    cu.name       AS customer,
    cu.country,
    o.status,
    o.total,
    o.created_at,
    COUNT(oi.id)  AS item_count
FROM orders o
JOIN customers  cu ON cu.id = o.customer_id
JOIN order_items oi ON oi.order_id = o.id
GROUP BY o.id, cu.name, cu.country, o.status, o.total, o.created_at;

-- View: product average rating
CREATE OR REPLACE VIEW v_product_ratings AS
SELECT
    p.id,
    p.name,
    ROUND(AVG(r.rating), 2) AS avg_rating,
    COUNT(r.id)             AS review_count
FROM products p
LEFT JOIN reviews r ON r.product_id = p.id
GROUP BY p.id, p.name;

-- Materialized view: sales by category (refresh manually after data changes)
CREATE MATERIALIZED VIEW mv_category_sales AS
SELECT
    c.name                    AS category,
    COUNT(DISTINCT o.id)      AS total_orders,
    SUM(oi.quantity)          AS units_sold,
    SUM(oi.quantity * oi.unit_price) AS revenue
FROM categories c
JOIN products    p  ON p.category_id = c.id
JOIN order_items oi ON oi.product_id = p.id
JOIN orders      o  ON o.id = oi.order_id
WHERE o.status != 'cancelled'
GROUP BY c.name
ORDER BY revenue DESC;

CREATE UNIQUE INDEX ON mv_category_sales (category);

-- ────────────────────────────────────────────────────────────
-- SAMPLE QUERIES (run with \i seed.sql, then try these)
-- ────────────────────────────────────────────────────────────

-- 1. CROSS JOIN — all possible employee × department combinations
--    SELECT e.name AS employee, d.name AS department
--    FROM employees e CROSS JOIN departments d
--    ORDER BY e.name, d.name;

-- 2. Full-text search (uses GIN index on search_vec)
--    SELECT name, price FROM products
--    WHERE search_vec @@ plainto_tsquery('english', 'wireless noise cancellation');

-- 3. Trigram fuzzy search (uses GIN trgm index)
--    SELECT name, price FROM products WHERE name ILIKE '%keyboard%';

-- 4. JSONB containment (uses GIN index on metadata)
--    SELECT name FROM products WHERE metadata @> '{"brand":"ProTech"}';

-- 5. Array containment (uses GIN index on tags)
--    SELECT name FROM products WHERE tags @> ARRAY['laptop'];

-- 6. Window function — rank customers by total spend
--    SELECT customer, total,
--           RANK() OVER (ORDER BY total DESC) AS spend_rank
--    FROM v_order_summary;

-- 7. Recursive CTE — full category hierarchy
--    WITH RECURSIVE cat_tree AS (
--      SELECT id, name, parent_id, 1 AS depth, name::TEXT AS path
--      FROM categories WHERE parent_id IS NULL
--      UNION ALL
--      SELECT c.id, c.name, c.parent_id, ct.depth+1,
--             ct.path || ' > ' || c.name
--      FROM categories c JOIN cat_tree ct ON c.parent_id = ct.id
--    )
--    SELECT path, depth FROM cat_tree ORDER BY path;

-- 8. BRIN index range scan — check with EXPLAIN ANALYZE
--    SELECT COUNT(*) FROM inventory_log
--    WHERE logged_at BETWEEN '2024-01-01' AND '2024-06-30';

-- 9. Refresh materialized view
--    REFRESH MATERIALIZED VIEW mv_category_sales;
--    SELECT * FROM mv_category_sales;
