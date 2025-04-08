CREATE TABLE users (
    id_ SERIAL PRIMARY KEY,
    first_name_ VARCHAR(50) NOT NULL,
    last_name_ VARCHAR(50) NOT NULL,
    email_ VARCHAR(50) UNIQUE NOT NULL,
    password_hash_ VARCHAR(255) NOT NULL,
    created_at_ TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL
);