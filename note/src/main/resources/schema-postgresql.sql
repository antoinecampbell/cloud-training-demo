CREATE TABLE IF NOT EXISTS notes (
  id          BIGSERIAL    NOT NULL PRIMARY KEY,
  title       VARCHAR(100) NOT NULL,
  description VARCHAR(2000),
  owner       VARCHAR(50)  NOT NULL
);
