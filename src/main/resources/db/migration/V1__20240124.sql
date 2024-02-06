-- This is a migration file for user table creation

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    username   VARCHAR(64)           NOT NULL UNIQUE,
    password   VARCHAR(64)           NOT NULL,
    status     VARCHAR(16)           NOT NULL,
    api_key    VARCHAR(128) UNIQUE,
    is_blocked BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS forecasts
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    station        VARCHAR(64)           NOT NULL,
    time           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    temperature    DOUBLE PRECISION      NOT NULL,
    precipitations VARCHAR(8)            NOT NULL,
    wind_speed     INT                   NOT NULL,
    wind_angle     DOUBLE PRECISION      NOT NULL,
    cloud_type     VARCHAR(16)           NOT NULL,
    cloud_octant   VARCHAR(4)            NOT NULL
);

CREATE TABLE IF NOT EXISTS stations
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    code VARCHAR(128) UNIQUE   NOT NULL,
    city VARCHAR(64) UNIQUE    NOT NULL
);

