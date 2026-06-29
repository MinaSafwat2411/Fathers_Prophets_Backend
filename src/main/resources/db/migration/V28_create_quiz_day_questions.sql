CREATE TABLE IF NOT EXISTS quiz_day_questions (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    quiz_day_id INT NOT NULL REFERENCES quiz_day(id) ON DELETE CASCADE,
    question TEXT NOT NULL,
    choice_1 VARCHAR(255) NOT NULL,
    choice_2 VARCHAR(255) NOT NULL,
    choice_3 VARCHAR(255),
    choice_4 VARCHAR(255),
    correct_answer mcq_correct_answer NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_quiz_day_questions_quiz_day_id ON quiz_day_questions(quiz_day_id);