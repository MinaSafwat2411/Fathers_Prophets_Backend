CREATE TABLE IF NOT EXISTS notifications
(
    id         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id   INT                      NOT NULL REFERENCES events (id) ON DELETE CASCADE,
    type       event_type               NOT NULL,
    title      VARCHAR(255)             NOT NULL,
    message    TEXT,
    is_read    BOOLEAN                  NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_notifications_event_id ON notifications (event_id);
CREATE INDEX IF NOT EXISTS idx_notifications_type ON notifications (type);