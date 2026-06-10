CREATE TYPE escape_egypt_type AS ENUM ('from', 'to');

CREATE TABLE IF NOT EXISTS escape_egypt(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    type escape_egypt_type NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_escape_egypt_type ON escape_egypt(type);