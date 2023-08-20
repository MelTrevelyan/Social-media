  DROP TABLE IF EXISTS posts;
  DROP TABLE IF EXISTS users;

  CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  email VARCHAR(255)                        NOT NULL,
  username VARCHAR(255)                     NOT NULL,
  user_password VARCHAR(200)                NOT NULL,
  CONSTRAINT uq_user_email UNIQUE (email),
  CONSTRAINT uq_user_name UNIQUE (username));

  CREATE TABLE IF NOT EXISTS posts (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  heading VARCHAR(150)                      NOT NULL,
  text VARCHAR(7000)                        NOT NULL,
  image BYTEA                               NOT NULL,
  author_id BIGINT                          NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE    NOT NULL,
  CONSTRAINT fk_posts_to_users FOREIGN KEY(author_id) REFERENCES users(id));

