CREATE TYPE anonymous_chat_status AS ENUM ('OPEN', 'CLOSED');

CREATE TABLE IF NOT EXISTS anonymous_chats
(
    id          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    member_id   INT                  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    teacher_id  INT                  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    subject     VARCHAR(255)         NOT NULL,
    status      anonymous_chat_status NOT NULL DEFAULT 'OPEN',
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_anonymous_chats_member_id ON anonymous_chats (member_id);
CREATE INDEX IF NOT EXISTS idx_anonymous_chats_teacher_id ON anonymous_chats (teacher_id);
CREATE INDEX IF NOT EXISTS idx_anonymous_chats_status ON anonymous_chats (status);
CREATE INDEX IF NOT EXISTS idx_anonymous_chats_created_at ON anonymous_chats (created_at);
CREATE INDEX IF NOT EXISTS idx_anonymous_chats_updated_at ON anonymous_chats (updated_at);