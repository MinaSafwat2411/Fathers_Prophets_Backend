CREATE TABLE IF NOT EXISTS user_progress_quiz
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    quiz_id INT REFERENCES quiz(id) ON DELETE CASCADE,
    day_id INT REFERENCES quiz_day(id) ON DELETE CASCADE,
    score INT DEFAULT 0,
    UNIQUE (user_id, quiz_id, day_id)
);

CREATE INDEX IF NOT EXISTS idx_user_progress_quiz_user_id ON user_progress_quiz(user_id);
CREATE INDEX IF NOT EXISTS idx_user_progress_quiz_quiz_id ON user_progress_quiz(quiz_id);
CREATE INDEX IF NOT EXISTS idx_user_progress_quiz_day_id ON user_progress_quiz(day_id);