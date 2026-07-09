DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'super_event_booking_status') THEN
        CREATE TYPE super_event_booking_status AS ENUM ('booked', 'waiting', 'cancelled');
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS super_event_bookings
(
    id             INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    super_event_id INT                        NOT NULL REFERENCES super_events (id) ON DELETE CASCADE,
    user_id        INT                        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name           VARCHAR(255)               NOT NULL,
    total_paid     INT                        NOT NULL DEFAULT 0,
    status         super_event_booking_status NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE   DEFAULT NOW(),
    teacher_id     INT                        REFERENCES users (id) ON DELETE SET NULL,

    CONSTRAINT super_event_bookings_event_user_unique UNIQUE (super_event_id, user_id)
);

CREATE INDEX IF NOT EXISTS idx_super_event_bookings_event_id ON super_event_bookings (super_event_id);
CREATE INDEX IF NOT EXISTS idx_super_event_bookings_user_id ON super_event_bookings (user_id);
CREATE INDEX IF NOT EXISTS idx_super_event_bookings_status ON super_event_bookings (status);
CREATE INDEX IF NOT EXISTS idx_super_event_bookings_teacher_id ON super_event_bookings (teacher_id);