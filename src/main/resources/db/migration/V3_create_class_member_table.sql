CREATE TABLE IF NOT EXISTS class_members
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    class_id INT NOT NULL REFERENCES classes(id),
    user_id INT  NOT NULL REFERENCES users(id),
    UNIQUE (class_id,user_id),
    teacher BOOLEAN DEFAULT FALSE NOT NULL,
    image VARCHAR(255)
);