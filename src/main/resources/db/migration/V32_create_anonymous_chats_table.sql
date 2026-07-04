
CREATE TABLE IF NOT EXISTS anonymous_chats
(
    id          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    member_id   INT                  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    servant_id  INT                  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    last_message VARCHAR(255),
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW(),

    UNIQUE(member_id, servant_id)
);