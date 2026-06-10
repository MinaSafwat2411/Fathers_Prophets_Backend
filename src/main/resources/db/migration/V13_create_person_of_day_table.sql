CREATE TABLE IF NOT EXISTS person_of_day
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id INT REFERENCES
    persons (id) ON DELETE CASCADE,
    message VARCHAR(255) NOT NULL,
    verse   VARCHAR(255) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_person_of_day_person_id ON person_of_day(person_id);