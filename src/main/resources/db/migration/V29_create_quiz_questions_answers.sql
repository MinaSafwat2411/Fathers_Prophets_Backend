CREATE TABLE IF NOT EXISTS quiz_answers(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    quiz_id INT NOT NULL REFERENCES quiz(id) ON DELETE CASCADE,
    question_id INT NOT NULL REFERENCES quiz_day_questions(id) ON DELETE CASCADE,
    day_id INT NOT NULL REFERENCES quiz_day(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    answer VARCHAR(255) NOT NULL,
    status answer_status NOT NULL,
    UNIQUE (question_id, user_id, day_id, quiz_id)
);

CREATE INDEX IF NOT EXISTS idx_quiz_answers_quiz_id ON quiz_answers(quiz_id);
CREATE INDEX IF NOT EXISTS idx_quiz_answers_question_id ON quiz_answers(question_id);
CREATE INDEX IF NOT EXISTS idx_quiz_answers_day_id ON quiz_answers(day_id);
CREATE INDEX IF NOT EXISTS idx_quiz_answers_user_id ON quiz_answers(user_id);