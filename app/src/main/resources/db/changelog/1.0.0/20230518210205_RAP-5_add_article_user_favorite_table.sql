-- liquibase formatted sql

-- changeset popov.d:RAP-5
CREATE TABLE article_user_favorite
(
    id_user     bigint,
    id_article  bigint,
    date_create timestamp DEFAULT now(),
    CONSTRAINT pk_article_user_favorite_id_user_id_article PRIMARY KEY (id_user, id_article),
    CONSTRAINT fk_id_user FOREIGN KEY (id_user) REFERENCES users (id),
    CONSTRAINT fk_id_article FOREIGN KEY (id_article) REFERENCES article (id)
);