CREATE TABLE IF NOT EXISTS events
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(255) NOT NULL CHECK
    (type IN
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
    title     VARCHAR(255) NOT NULL,
    date_time DATE         NOT NULL,
    image     VARCHAR(255)
);
