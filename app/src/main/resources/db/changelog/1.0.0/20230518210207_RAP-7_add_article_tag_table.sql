-- liquibase formatted sql

-- changeset popov.d:RAP-7
CREATE TABLE article_article_tag
(
    id_tag     bigint,
    id_article  bigint,
    date_create timestamp DEFAULT now(),
    CONSTRAINT pk_article_article_tag_id_tag_id_article PRIMARY KEY (id_tag, id_article),
    CONSTRAINT fk_id_tag FOREIGN KEY (id_tag) REFERENCES tag (id),
    CONSTRAINT fk_id_article FOREIGN KEY (id_article) REFERENCES article (id)
);