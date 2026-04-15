CREATE TABLE IF NOT EXISTS users
(
    id
    INT
    GENERATED
    ALWAYS AS
    IDENTITY
    PRIMARY
    KEY,
    name
    VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL UNIQUE,
    email           VARCHAR(255) UNIQUE,
    phone           VARCHAR(50),
    address         VARCHAR(255),
    birth_date      DATE,
    father_name     VARCHAR(255),
    is_shams        BOOLEAN,
    profile         VARCHAR(255),
    is_reviewed     BOOLEAN,
    role            VARCHAR(255) NOT NULL CHECK
(
    role
    IN
(
    'member',
    'admin',
    'superadmin',
    'football',
    'teacher',
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
    'sports',
    'spiritual',
    'melodies',
    'games',
    'quiz'
)
    ),
    fcm_token       VARCHAR(255),
    class_id        INT REFERENCES classes
(
    id
),
    chats           VARCHAR(255),
    member_id       VARCHAR(100),
    skip_membership BOOLEAN,
    comments        VARCHAR(255),
    password_hash   VARCHAR(255) NOT NULL,
    token           VARCHAR(255),
    refresh_token   VARCHAR(255)
    );
