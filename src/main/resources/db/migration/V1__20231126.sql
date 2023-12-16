-- This is a migration file for user table creation

CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT PRIMARY KEY NOT NULL,
    username   VARCHAR(64)     NOT NULL UNIQUE,
    password   VARCHAR(64)     NOT NULL,
    status     VARCHAR(16)     NOT NULL,
    api_key    VARCHAR(64) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);