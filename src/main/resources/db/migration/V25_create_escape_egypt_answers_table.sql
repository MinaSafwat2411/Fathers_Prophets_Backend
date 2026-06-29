CREATE TABLE IF NOT EXISTS escape_egypt_answers(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    escape_egypt_id INT REFERENCES escape_egypt(id) ON DELETE CASCADE,
    escape_question_id INT REFERENCES escape_from_eqypt_questions(id) ON DELETE CASCADE,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    answer VARCHAR(255) NOT NULL,
    status  answer_status NOT NULL,
    UNIQUE (escape_question_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_escape_egypt_answers_escape_egypt_id ON escape_egypt_answers(escape_egypt_id);
CREATE INDEX IF NOT EXISTS idx_escape_egypt_answers_escape_question_id ON escape_egypt_answers(escape_question_id);
CREATE INDEX IF NOT EXISTS idx_escape_egypt_answers_user_id ON escape_egypt_answers(user_id);