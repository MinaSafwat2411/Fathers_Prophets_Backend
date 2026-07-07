CREATE TABLE IF NOT EXISTS anonymous_chat_messages
(
    id         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    chat_id    INT                      NOT NULL REFERENCES anonymous_chats (id) ON DELETE CASCADE,
    member_id  INT                      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    servant_id  INT                      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    message    TEXT                     NOT NULL,
    servant_name VARCHAR(255),
    member_name VARCHAR(255),
    is_read    BOOLEAN                  NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);