DROP TABLE visits IF EXISTS;
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

CREATE TABLE visits (
  id          INTEGER IDENTITY PRIMARY KEY,
  owner_id      INTEGER NOT NULL,
  visit_date  DATE,
  description VARCHAR(255)
);
ALTER TABLE visits ADD CONSTRAINT fk_visits_owners FOREIGN KEY (owner_id) REFERENCES owners (id);
CREATE INDEX visits_owner_id ON visits (owner_id);
