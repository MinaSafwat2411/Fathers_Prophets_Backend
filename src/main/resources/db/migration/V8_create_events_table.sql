CREATE TABLE IF NOT EXISTS events
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL CHECK
    (name IN
        (
        'football',
        'volleyball',
        'chess',
        'pingPong',
        'pray',
        'praise',
        'doctrine',
        'bible',
        'ritual',
        'coptic',
        'choir',
        'mahrgan',
        'odas',
        'shmas',
        'melodies'
        )
    ),
    date_time DATE         NOT NULL,
    image     VARCHAR(255)
);
