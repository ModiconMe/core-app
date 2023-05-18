-- liquibase formatted sql

-- changeset popov.d:RAP-2
CREATE TABLE follow_relation
(
    id_followee bigint,
    id_follower bigint,
    date_create timestamp DEFAULT now(),
    CONSTRAINT pk_follow_relation_followeeId_followerId PRIMARY KEY (id_followee, id_follower),
    CONSTRAINT fk_followee_id FOREIGN KEY (id_followee) REFERENCES users (id),
    CONSTRAINT fk_follower_id FOREIGN KEY (id_follower) REFERENCES users (id)
);