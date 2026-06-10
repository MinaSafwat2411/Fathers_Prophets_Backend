CREATE TABLE IF NOT EXISTS timeline
(
    id            INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_1       VARCHAR(255) NOT NULL,
    event_2       VARCHAR(255) NOT NULL,
    event_3       VARCHAR(255) NOT NULL,
    event_4       VARCHAR(255) NOT NULL,
    correct_order INT[] NOT NULL
);