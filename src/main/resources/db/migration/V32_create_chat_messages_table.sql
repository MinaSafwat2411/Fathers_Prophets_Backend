CREATE TABLE IF NOT EXISTS chat_messages
(
    id         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_id    INT NOT NULL REFERENCES chat_rooms (id) ON DELETE CASCADE,
    sender_id  INT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    message    TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_chat_messages_room_id ON chat_messages (room_id);
CREATE INDEX IF NOT EXISTS idx_chat_messages_sender_id ON chat_messages (sender_id);
CREATE INDEX IF NOT EXISTS idx_chat_messages_created_at ON chat_messages (created_at);