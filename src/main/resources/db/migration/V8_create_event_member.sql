CREATE TABLE IF NOT EXISTS event_members
(
    id INT  GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id INT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    event_type event_type NOT NULL,
    UNIQUE(event_id,user_id)
);
