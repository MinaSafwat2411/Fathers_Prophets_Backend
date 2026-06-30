CREATE TABLE IF NOT EXISTS guess_person_questions
(
    id                INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question          VARCHAR(255)       NOT NULL,
    correct_person_id INT REFERENCES persons (id) ON DELETE CASCADE,
    difficulty        INT,
    first             JSON               NOT NULL,
    second            JSON               NOT NULL,
    third             JSON               NOT NULL,
    fourth            JSON               NOT NULL,
    correct_answer    mcq_correct_answer NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_guess_person_questions_correct_person_id ON guess_person_questions(correct_person_id);