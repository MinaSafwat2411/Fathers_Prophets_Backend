CREATE TABLE IF NOT EXISTS persons_questions (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    person_id INT NOT NULL REFERENCES persons(id) ON DELETE CASCADE,
    type VARCHAR(255) NOT NULL CHECK (
        type IN(
            'mcq',
            'complete'
        )
    )
);