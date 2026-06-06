CREATE TABLE IF NOT EXISTS persons_stories
(
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id INT REFERENCES persons(id) ON DELETE CASCADE,
    title      VARCHAR(255) NOT NULL,
    content    VARCHAR(255) NOT NULL,
    image      VARCHAR(255),
    question   VARCHAR(255) NOT NULL,
);

