CREATE TABLE IF NOT EXISTS guess_person_answers
(
    id          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question_id INT     NOT NULL REFERENCES guess_person_questions (id) ON DELETE CASCADE,
    user_id     INT     NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    person_id   INT     NOT NULL REFERENCES persons (id) ON DELETE CASCADE,
    status answer_status NOT NULL,
    UNIQUE (question_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_guess_person_answers_question_id ON guess_person_answers(question_id);
CREATE INDEX IF NOT EXISTS idx_guess_person_answers_user_id ON guess_person_answers(user_id);
CREATE INDEX IF NOT EXISTS idx_guess_person_answers_person_id ON guess_person_answers(person_id);