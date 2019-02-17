CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(50)  NOT NULL PRIMARY KEY,
  password VARCHAR(500) NOT NULL,
  enabled  BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
  authority VARCHAR(50) NOT NULL,
  username  VARCHAR(50) NOT NULL,
  primary key (authority, username),
  constraint authorities_username_fkey FOREIGN KEY (username) REFERENCES users
);

CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id               VARCHAR(256) PRIMARY KEY,
  resource_ids            VARCHAR(256),
  client_secret           VARCHAR(256),
  scope                   VARCHAR(256),
  authorized_grant_types  VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities             VARCHAR(256),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(4096),
  autoapprove             VARCHAR(256)
);
