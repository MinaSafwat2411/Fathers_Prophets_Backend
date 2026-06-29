CREATE TABLE IF NOT EXISTS persons_stories
(
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id INT REFERENCES persons(id) ON DELETE CASCADE,
    title      VARCHAR(255) NOT NULL,
    content    TEXT NOT NULL,
    image      VARCHAR(255),
    question   VARCHAR(255) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_persons_stories_person_id ON persons_stories(person_id);