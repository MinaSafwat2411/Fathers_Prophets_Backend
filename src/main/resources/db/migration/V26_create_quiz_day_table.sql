CREATE TYPE day_of_week AS ENUM ('SAT', 'SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI');
CREATE TYPE quiz_day_type AS ENUM ('TRUE_FALSE', 'MCQ');

CREATE TABLE IF NOT EXISTS quiz_day (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    quiz_id INT NOT NULL REFERENCES quiz(id) ON DELETE CASCADE,
    day_name day_of_week NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    book VARCHAR(255) NOT NULL,
    chapter INT NOT NULL,
    verse_from INT NOT NULL,
    verse_to INT NOT NULL,
    type_day quiz_day_type NOT NULL,
    UNIQUE (quiz_id, day_name)
);

CREATE INDEX IF NOT EXISTS idx_quiz_day_quiz_id ON quiz_day(quiz_id);
CREATE INDEX IF NOT EXISTS idx_quiz_day_start_at ON quiz_day(start_at);
CREATE INDEX IF NOT EXISTS idx_quiz_day_end_at ON quiz_day(end_at);