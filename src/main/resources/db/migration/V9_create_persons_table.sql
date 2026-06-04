CREATE TYPE person_type AS ENUM (
    'prophets',
    'fathers',
    'saints',
    'apostles',
    'judges'
);

CREATE TABLE IF NOT EXISTS persons
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    nickname       VARCHAR(255),
    short_story VARCHAR(255),
    full_story  VARCHAR(255),
    image       VARCHAR(255),
    type person_type NOT NULL
);