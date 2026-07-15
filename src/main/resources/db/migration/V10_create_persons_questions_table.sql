CREATE TYPE question_type AS ENUM (
    'mcq',
    'complete'
);

CREATE TABLE IF NOT EXISTS persons_questions (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    person_id INT NOT NULL REFERENCES persons(id) ON DELETE CASCADE,
    type question_type NOT NULL,
    correct_answer VARCHAR(255) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_persons_questions_person_id ON persons_questions(person_id);
CREATE INDEX IF NOT EXISTS idx_persons_questions_type ON persons_questions(type);