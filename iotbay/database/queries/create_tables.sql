-- DROP any old versions
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS accesslogs;

-- Users table
CREATE TABLE users (
  id        INTEGER PRIMARY KEY AUTOINCREMENT,
  fullname  TEXT    NOT NULL,
  email     TEXT    NOT NULL UNIQUE,
  password  TEXT    NOT NULL,
  phone     TEXT
);

-- Access logs table
CREATE TABLE accesslogs (
  id         INTEGER PRIMARY KEY AUTOINCREMENT,
  userId     INTEGER NOT NULL,
  loginTime  TEXT    NOT NULL,
  logoutTime TEXT,
  FOREIGN KEY(userId) REFERENCES users(id)
);
