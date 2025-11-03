CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL CHECK ( price >= 0 ),
    categoryId BIGINT NOT NULL,
    sellerId BIGINT NOT NULL
)