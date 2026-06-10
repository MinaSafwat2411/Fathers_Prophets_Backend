CREATE TABLE IF NOT EXISTS escape_from_eqypt_questions(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    escape_egypt_id INT REFERENCES escape_egypt(id) ON DELETE CASCADE,
    question VARCHAR(255) NOT NULL,
    correct_answer VARCHAR(255) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_escape_from_eqypt_questions_escape_egypt_id ON escape_from_eqypt_questions(escape_egypt_id);