CREATE TABLE IF NOT EXISTS stories_members
(
    id       INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    story_id INT NOT NULL REFERENCES persons_stories(id) ON DELETE CASCADE,
    user_id  INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    answered VARCHAR(255) NOT NULL,
    status answer_status NOT NULL,
    UNIQUE (story_id, user_id)
);
