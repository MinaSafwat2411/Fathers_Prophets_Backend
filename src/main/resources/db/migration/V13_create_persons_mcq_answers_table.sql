CREATE TABLE IF NOT EXISTS persons_mcq_answers (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id INT NOT NULL REFERENCES persons(id) ON DELETE CASCADE,
    answer VARCHAR(255) NOT NULL,
    question_id INT NOT NULL REFERENCES persons_mcq(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status answer_status NOT NULL,
    CONSTRAINT uq_persons_mcq_answers_question_user UNIQUE (question_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_persons_mcq_answers_question_id ON persons_mcq_answers(question_id);
CREATE INDEX IF NOT EXISTS idx_persons_mcq_answers_user_id ON persons_mcq_answers(user_id);
CREATE INDEX IF NOT EXISTS idx_persons_mcq_answers_status ON persons_mcq_answers(status);
