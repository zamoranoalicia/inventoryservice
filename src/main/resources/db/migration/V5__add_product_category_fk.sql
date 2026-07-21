-- Link products to product_category (product_category is created in V2,
-- so the FK cannot be declared in V1's products table definition).
ALTER TABLE products
    ADD CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES product_category (category_id);
