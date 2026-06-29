CREATE TYPE chat_room_type AS ENUM ('DIRECT', 'GROUP');

CREATE TABLE IF NOT EXISTS chat_rooms
(
    id         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255), -- Name can be nullable for direct messages
    type       chat_room_type NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_chat_rooms_type ON chat_rooms (type);
CREATE INDEX IF NOT EXISTS idx_chat_rooms_name ON chat_rooms (name) WHERE name IS NOT NULL;
CREATE INDEX IF NOT EXISTS idx_chat_rooms_created_at ON chat_rooms (created_at);
CREATE INDEX IF NOT EXISTS idx_chat_rooms_updated_at ON chat_rooms (updated_at);