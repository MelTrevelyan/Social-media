  DROP TABLE IF EXISTS messages;
  DROP TABLE IF EXISTS friendships;
  DROP TABLE IF EXISTS user_followers;
  DROP TABLE IF EXISTS posts;
  DROP TABLE IF EXISTS users_roles;
  DROP TABLE IF EXISTS users;
  DROP TABLE IF EXISTS roles;

  CREATE TABLE IF NOT EXISTS roles (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR(20)                          NOT NULL);

  CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  email         VARCHAR(255)                NOT NULL,
  username      VARCHAR(255)                NOT NULL,
  user_password VARCHAR(200)                NOT NULL,
  CONSTRAINT uq_user_email UNIQUE (email),
  CONSTRAINT uq_user_name UNIQUE (username));

  CREATE TABLE IF NOT EXISTS users_roles (
  user_id  BIGINT                         NOT NULL,
  role_id  BIGINT                         NOT NULL,
  CONSTRAINT users_roles_pk PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_users_roles_to_users FOREIGN KEY(user_id) REFERENCES users(id),
  CONSTRAINT fk_users_roles_to_roles FOREIGN KEY(role_id) REFERENCES roles(id));

  CREATE TABLE IF NOT EXISTS posts (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  heading    VARCHAR(150)                   NOT NULL,
  text       VARCHAR(7000)                  NOT NULL,
  image      OID,
  author_id  BIGINT                         NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE    NOT NULL,
  CONSTRAINT fk_posts_to_users FOREIGN KEY(author_id) REFERENCES users(id));

  CREATE TABLE IF NOT EXISTS user_followers (
  user_id     BIGINT                        NOT NULL,
  follower_id BIGINT                        NOT NULL,
  CONSTRAINT user_followers_pk PRIMARY KEY (user_id, follower_id),
  CONSTRAINT fk_user_followers_to_users FOREIGN KEY(user_id) REFERENCES users(id),
  CONSTRAINT fk_followers_to_users FOREIGN KEY(follower_id) REFERENCES users(id));

  CREATE TABLE IF NOT EXISTS friendships (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  requester_id BIGINT                       NOT NULL,
  receiver_id  BIGINT                       NOT NULL,
  status VARCHAR(10)                        NOT NULL,
  CONSTRAINT fk_friendships_requesters_to_users FOREIGN KEY(requester_id) REFERENCES users(id),
  CONSTRAINT fk_friendships_receivers_to_users FOREIGN KEY(receiver_id) REFERENCES users(id));

  CREATE TABLE IF NOT EXISTS messages (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  sender_id    BIGINT                       NOT NULL,
  receiver_id  BIGINT                       NOT NULL,
  text         VARCHAR(7000)                NOT NULL,
  created_at   TIMESTAMP WITHOUT TIME ZONE  NOT NULL,
  CONSTRAINT fk_messages_senders_to_users FOREIGN KEY(sender_id) REFERENCES users(id),
  CONSTRAINT fk_messages_receivers_to_users FOREIGN KEY(receiver_id) REFERENCES users(id));