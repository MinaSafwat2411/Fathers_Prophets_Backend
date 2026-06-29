CREATE TYPE chat_member_role AS ENUM ('ADMIN', 'MEMBER');

CREATE TABLE IF NOT EXISTS chat_room_members
(
    room_id    INT NOT NULL REFERENCES chat_rooms (id) ON DELETE CASCADE,
    user_id    INT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role       chat_member_role NOT NULL DEFAULT 'MEMBER',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    PRIMARY KEY (room_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_chat_room_members_user_id ON chat_room_members (user_id);
CREATE INDEX IF NOT EXISTS idx_chat_room_members_role ON chat_room_members (role);