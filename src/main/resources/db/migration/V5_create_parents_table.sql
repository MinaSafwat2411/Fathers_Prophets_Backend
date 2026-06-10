CREATE TABLE IF NOT EXISTS parents(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    mother_phone VARCHAR(255),
    father_phone VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS idx_parents_user_id ON parents(user_id);