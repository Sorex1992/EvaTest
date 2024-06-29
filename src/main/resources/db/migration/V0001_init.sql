CREATE TABLE IF NOT EXISTS public.product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    country VARCHAR(255),
    price DOUBLE PRECISION NOT NULL,
    quantity INTEGER NOT NULL
);