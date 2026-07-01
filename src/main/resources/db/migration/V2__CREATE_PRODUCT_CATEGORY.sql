CREATE TABLE product_category
(
    categoryId  UUID NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    createdAt   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updatedAt   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_product_category PRIMARY KEY (categoryId)
);