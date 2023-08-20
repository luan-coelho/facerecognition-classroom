CREATE TABLE person
(
    person_id     BIGINT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    resource_path VARCHAR(255) NOT NULL
);

CREATE SEQUENCE PERSON_SEQ;

INSERT INTO person (person_id, name, resource_path)
VALUES (nextval('PERSON_SEQ'), 'Luan', '/home/luan/Imagens/Face/peoples');
