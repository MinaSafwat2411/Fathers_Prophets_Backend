CREATE TABLE IF NOT EXISTS guess_person_questions
(
    id                INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question          VARCHAR(255)       NOT NULL,
    correct_person_id INT REFERENCES persons (id) ON DELETE CASCADE,
    difficulty        INT,
    first             VARCHAR(255)       NOT NULL,
    second            VARCHAR(255)       NOT NULL,
    third             VARCHAR(255)       NOT NULL,
    fourth            VARCHAR(255)       NOT NULL,
    correct_answer    mcq_correct_answer NOT NULL
);
