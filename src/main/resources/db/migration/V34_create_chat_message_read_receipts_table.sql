CREATE TABLE IF NOT EXISTS chat_message_read_receipts
(
    message_id INT NOT NULL REFERENCES chat_messages (id) ON DELETE CASCADE,
    user_id    INT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    read_at    TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    PRIMARY KEY (message_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_chat_message_read_receipts_user_id ON chat_message_read_receipts (user_id);