-- This is a migration file for user table creation

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    username   VARCHAR(64)           NOT NULL UNIQUE,
    password   VARCHAR(64)           NOT NULL,
    status     VARCHAR(16)           NOT NULL,
    api_key    VARCHAR(64) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS forecasts
(
    id            BIGSERIAL PRIMARY KEY NOT NULL,
    station       VARCHAR(64)           NOT NULL,
    temperature   DOUBLE PRECISION      NOT NULL,
    precipitation VARCHAR(8)            NOT NULL,
    wind_speed    INT                   NOT NULL,
    wind_angle    DOUBLE PRECISION      NOT NULL,
    cloud_type    VARCHAR(16)           NOT NULL,
    cloud_octant  VARCHAR(4)            NOT NULL
);

CREATE TABLE IF NOT EXISTS stations
(
    id   VARCHAR PRIMARY KEY NOT NULL,
    city VARCHAR(64)         NOT NULL
);

