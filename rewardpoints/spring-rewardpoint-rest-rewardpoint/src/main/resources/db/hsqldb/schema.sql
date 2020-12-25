DROP TABLE rewardpoints IF EXISTS;
DROP TABLE owners IF EXISTS;

CREATE TABLE owners (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20)
);

CREATE INDEX owners_last_name ON owners (last_name);

CREATE TABLE rewardpoints (
  id          INTEGER IDENTITY PRIMARY KEY,
  owner_id      INTEGER NOT NULL,
  recognizer_id INTEGER,
  points      INTEGER DEFAULT 5,
  rewardpoint_date  DATE,
  description VARCHAR(255)
);

ALTER TABLE rewardpoints ADD CONSTRAINT fk_rewardpoints_owners FOREIGN KEY (owner_id) REFERENCES owners (id);
CREATE INDEX rewardpoints_owner_id ON rewardpoints (owner_id);

CREATE TABLE rewardpointpolicys (
  id          INTEGER IDENTITY PRIMARY KEY,
  predicate VARCHAR(255)
);
