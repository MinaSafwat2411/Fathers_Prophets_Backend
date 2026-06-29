CREATE TABLE IF NOT EXISTS matching_pairs
(
    id         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id  INT NOT NULL REFERENCES persons (id) ON DELETE CASCADE,
    person_name VARCHAR(255) NOT NULL,
    other_side VARCHAR(255) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_matching_pairs_person_id ON matching_pairs(person_id);