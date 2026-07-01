CREATE TABLE IF NOT EXISTS matching_pairs_answers
(
    id         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pair_id    INT           NOT NULL REFERENCES matching_pairs (id) ON DELETE CASCADE,
    user_id    INT           NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    user_pair JSON           NOT NULL,
    status     answer_status NOT NULL,
    UNIQUE (pair_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_matching_pairs_answers_pair_id ON matching_pairs_answers(pair_id);
CREATE INDEX IF NOT EXISTS idx_matching_pairs_answers_user_id ON matching_pairs_answers(user_id);