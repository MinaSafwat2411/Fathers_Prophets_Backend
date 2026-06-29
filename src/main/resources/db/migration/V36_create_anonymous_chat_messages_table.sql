CREATE TABLE IF NOT EXISTS anonymous_chat_messages
(
    id         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    chat_id    INT                      NOT NULL REFERENCES anonymous_chats (id) ON DELETE CASCADE,
    sender_id  INT                      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    message    TEXT                     NOT NULL,
    is_read    BOOLEAN                  NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_anonymous_chat_messages_chat_id ON anonymous_chat_messages(chat_id);
CREATE INDEX IF NOT EXISTS idx_anonymous_chat_messages_sender_id ON anonymous_chat_messages(sender_id);
CREATE INDEX IF NOT EXISTS idx_anonymous_chat_messages_created_at ON anonymous_chat_messages(created_at);
