CREATE TABLE IF NOT EXISTS class_members
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    class_id INT NOT NULL REFERENCES classes(id) ON DELETE CASCADE,
    user_id INT  NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (class_id,user_id),
    teacher BOOLEAN DEFAULT FALSE NOT NULL,
    image VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS idx_class_members_class_id ON class_members(class_id);
CREATE INDEX IF NOT EXISTS idx_class_members_user_id ON class_members(user_id);