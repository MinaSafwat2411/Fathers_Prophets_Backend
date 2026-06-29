CREATE TABLE IF NOT EXISTS stories_answers
(
    id       INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    story_id INT NOT NULL REFERENCES persons_stories(id) ON DELETE CASCADE,
    user_id  INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    answered VARCHAR(255) NOT NULL,
    question_id INT NOT NULL REFERENCES persons_story_questions(id) ON DELETE CASCADE,
    status answer_status NOT NULL,
    UNIQUE (story_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_stories_answers_story_id ON stories_answers(story_id);
CREATE INDEX IF NOT EXISTS idx_stories_answers_user_id ON stories_answers(user_id);