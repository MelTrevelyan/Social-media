  DROP TABLE IF EXISTS users;

  CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  email VARCHAR(255)                        NOT NULL,
  username VARCHAR(255)                     NOT NULL,
  user_password VARCHAR(200)                NOT NULL,
  CONSTRAINT uq_user_email UNIQUE (email),
  CONSTRAINT uq_user_name UNIQUE (username));

