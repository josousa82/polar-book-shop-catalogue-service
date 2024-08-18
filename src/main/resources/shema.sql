DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    id               BIGSERIAL PRIMARY KEY NOT NULL,
    author           varchar(255)          NOT NULL,
    isbn             varchar(255) UNIQUE   NOT NULL,
    price            float8                NOT NULL,
    createdDate      TIMESTAMP             NOT NULL,
    lastModifiedDate TIMESTAMP             NOT NULL,
    version          INTEGER               NOT NULL
);