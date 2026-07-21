CREATE TABLE product_category
(
    category_id UUID NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_product_category PRIMARY KEY (category_id)
);