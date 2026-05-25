CREATE TABLE IF NOT EXISTS persons
(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    title       VARCHAR(255),
    short_story VARCHAR(255),
    full_story  VARCHAR(255),
    image       VARCHAR(255),
    type VARCHAR(255) NOT NULL CHECK(
       type IN(
        'prophets',
        'fathers',
        'saints',
        'apostles',
        'judges'
        )
    )
);