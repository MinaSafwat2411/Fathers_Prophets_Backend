CREATE TABLE IF NOT EXISTS super_events
(
    id                INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title             VARCHAR(255)             NOT NULL,
    description       TEXT,
    location          VARCHAR(255),
    start_date        DATE                     NOT NULL,
    end_date          DATE                     NOT NULL,
    last_booking_date DATE                     NOT NULL,
    total_seats       INT                      NOT NULL,
    waiting_list_limit INT                     NOT NULL DEFAULT 0,
    image             VARCHAR(255),
    teachers          JSONB                    NOT NULL DEFAULT '[]'::JSONB,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_super_events_start_date ON super_events (start_date);
CREATE INDEX IF NOT EXISTS idx_super_events_last_booking_date ON super_events (last_booking_date);