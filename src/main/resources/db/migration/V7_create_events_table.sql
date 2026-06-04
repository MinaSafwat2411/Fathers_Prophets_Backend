CREATE TYPE event_type AS ENUM (
    'football',
    'volleyball',
    'chess',
    'pingPong',
    'pray',
    'praise',
    'doctrine',
    'bible',
    'ritual',
    'coptic',
    'choir',
    'mahrgan',
    'odas',
    'shmas',
    'melodies'
);

CREATE TABLE IF NOT EXISTS events
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type      event_type   NOT NULL,
    title     VARCHAR(255) NOT NULL,
    date_time DATE         NOT NULL,
    image     VARCHAR(255)
);