CREATE TABLE IF NOT EXISTS persons_story_questions
(
    id       INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    story_id INT NOT NULL REFERENCES persons_stories(id) ON DELETE CASCADE,
    question VARCHAR(255) NOT NULL,
    correctAnswer VARCHAR(255) NOT NULL
);