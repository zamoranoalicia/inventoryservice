-- Create brands table
CREATE TABLE IF NOT EXISTS brands (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Create laboratories table
CREATE TABLE IF NOT EXISTS laboratories (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255)
);

-- Create active ingredients table
CREATE TABLE IF NOT EXISTS active_ingredients (
    id UUID NOT NULL PRIMARY KEY,
    ingredient_name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create therapeutic actions table
CREATE TABLE IF NOT EXISTS therapeutic_actions (
    id UUID NOT NULL PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create prices table
CREATE TABLE IF NOT EXISTS prices (
    id UUID NOT NULL PRIMARY KEY,
    amount NUMERIC(15, 2) NOT NULL,
    currency VARCHAR(255)
);

-- Create date alerts table
CREATE TABLE IF NOT EXISTS date_alerts (
    id UUID NOT NULL PRIMARY KEY,
    expiration_date TIMESTAMP WITH TIME ZONE NOT NULL,
    alert_date TIMESTAMP WITH TIME ZONE
);

-- Create product suppliers table
CREATE TABLE IF NOT EXISTS product_suppliers (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    tax_id VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255),
    email VARCHAR(255),
    sanitary_authorization_number VARCHAR(255)
);

-- Create presentation types table
CREATE TABLE IF NOT EXISTS presentation_types (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id UUID NOT NULL PRIMARY KEY,
    sku VARCHAR(255) NOT NULL UNIQUE,
    bar_code VARCHAR(255) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_description TEXT,
    category VARCHAR(255),
    prescription_required BOOLEAN NOT NULL,
    controlled_substance BOOLEAN NOT NULL,
    laboratory_id UUID,
    brand_id UUID,
    sanitary_registration VARCHAR(255) UNIQUE,
    reorder_level INTEGER NOT NULL,
    CONSTRAINT fk_products_laboratory FOREIGN KEY (laboratory_id) REFERENCES laboratories(id),
    CONSTRAINT fk_products_brand FOREIGN KEY (brand_id) REFERENCES brands(id)
);

-- Create product presentations table
CREATE TABLE IF NOT EXISTS product_presentations (
    id UUID NOT NULL PRIMARY KEY,
    product_id UUID,
    presentation_type_id UUID,
    units_contained INTEGER,
    sale_price_net NUMERIC(15, 2),
    tax_percentage NUMERIC(15, 2),
    sale_price_gross NUMERIC(15, 2),
    active BOOLEAN,
    CONSTRAINT fk_product_presentations_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_product_presentations_type FOREIGN KEY (presentation_type_id) REFERENCES presentation_types(id)
);

-- Create inventory batches table
CREATE TABLE IF NOT EXISTS inventory_batches (
    id UUID NOT NULL PRIMARY KEY,
    product_id UUID NOT NULL,
    supplier_id UUID,
    lot_number VARCHAR(255),
    reception_date TIMESTAMP WITH TIME ZONE,
    expiration_date TIMESTAMP WITH TIME ZONE,
    quantity INTEGER,
    alert_before_days INTEGER,
    purchase_price NUMERIC(15, 2),
    CONSTRAINT fk_inventory_batches_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_inventory_batches_supplier FOREIGN KEY (supplier_id) REFERENCES product_suppliers(id)
);

-- Create product active ingredients mapping table
CREATE TABLE IF NOT EXISTS product_active_ingredients (
    product_id UUID NOT NULL,
    active_ingredients_id UUID NOT NULL,
    CONSTRAINT fk_prd_ingredient_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_prd_ingredient_active FOREIGN KEY (active_ingredients_id) REFERENCES active_ingredients(id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, active_ingredients_id)
);

-- Create product prices mapping table
CREATE TABLE IF NOT EXISTS product_prices (
    product_id UUID NOT NULL,
    prices_id UUID NOT NULL,
    CONSTRAINT fk_prd_prices_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_prd_prices_price FOREIGN KEY (prices_id) REFERENCES prices(id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, prices_id)
);

-- Create product therapeutic actions mapping table
CREATE TABLE IF NOT EXISTS product_therapeutic_actions (
    product_id UUID NOT NULL,
    therapeutic_actions_id UUID NOT NULL,
    CONSTRAINT fk_prd_action_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_prd_action_therapeutic FOREIGN KEY (therapeutic_actions_id) REFERENCES therapeutic_actions(id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, therapeutic_actions_id)
);

-- Create product presentations mapping table
CREATE TABLE IF NOT EXISTS product_product_presentations (
    product_id UUID NOT NULL,
    product_presentations_id UUID NOT NULL,
    CONSTRAINT fk_prd_pres_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_prd_pres_presentation FOREIGN KEY (product_presentations_id) REFERENCES product_presentations(id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, product_presentations_id)
);

-- Create inventory batches mapping table
CREATE TABLE IF NOT EXISTS product_inventory_batches (
    product_id UUID NOT NULL,
    inventory_batches_id UUID NOT NULL,
    CONSTRAINT fk_prd_batch_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_prd_batch_inventory FOREIGN KEY (inventory_batches_id) REFERENCES inventory_batches(id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, inventory_batches_id)
);

