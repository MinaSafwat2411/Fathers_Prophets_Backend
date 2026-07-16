CREATE TYPE mcq_correct_answer AS ENUM ('first', 'second', 'third', 'fourth');

CREATE TABLE IF NOT EXISTS persons_mcq (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    first  VARCHAR(255) NOT NULL,
    second VARCHAR(255) NOT NULL,
    third VARCHAR(255) NOT NULL,
    fourth VARCHAR(255) NOT NULL,
    correct_answer mcq_correct_answer NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_persons_mcq_question_id ON persons_mcq(question_id);