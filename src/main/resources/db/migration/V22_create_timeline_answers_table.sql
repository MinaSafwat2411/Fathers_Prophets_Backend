CREATE TABLE IF NOT EXISTS timeline_answers
(
    id      INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    timeline_id INT           NOT NULL REFERENCES timeline (id) ON DELETE CASCADE,
    user_id INT           NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    status  answer_status NOT NULL,
    order   INT[]         NOT NULL,
    UNIQUE (timeline_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_timeline_answers_timeline_id ON timeline_answers(timeline_id);
CREATE INDEX IF NOT EXISTS idx_timeline_answers_user_id ON timeline_answers(user_id);