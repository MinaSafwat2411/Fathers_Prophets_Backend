CREATE TYPE answer_status AS ENUM ('TEACHER_STILL_NOT_CORRECTED', 'IS_TRUE', 'IS_FALSE');

CREATE TABLE IF NOT EXISTS persons_answers (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    answer TEXT NOT NULL,
    question_id INT NOT NULL REFERENCES persons_questions(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status answer_status NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_persons_answers_question_id ON persons_answers(question_id);
CREATE INDEX IF NOT EXISTS idx_persons_answers_user_id ON persons_answers(user_id);
CREATE INDEX IF NOT EXISTS idx_persons_answers_status ON persons_answers(status);