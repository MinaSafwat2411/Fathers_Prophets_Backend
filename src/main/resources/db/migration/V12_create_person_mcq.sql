CREATE TABLE IF NOT EXISTS persons_mcq (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question_id INT NOT NULL REFERENCES persons_questions(id) ON DELETE CASCADE,
    first  VARCHAR(255) NOT NULL,
    second VARCHAR(255) NOT NULL,
    third VARCHAR(255) NOT NULL,
    fourth VARCHAR(255) NOT NULL,
    correct_answer INT NOT NULL CHECK (
        correct_answer IN(
            1,
            2,
            3,
            4
        )
    )
);
