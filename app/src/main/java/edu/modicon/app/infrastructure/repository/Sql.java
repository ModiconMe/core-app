package edu.modicon.app.infrastructure.repository;

abstract public class Sql {

    public static final class User {

        private static final String BASE_FIND = """
                SELECT id,
                       email,
                       username,
                       password,
                       bio,
                       image
                  FROM users
                  %s
                """;

        public static final String FIND_BY_ID = BASE_FIND.formatted("WHERE id = :id");

        public static final String FIND_BY_EMAIL = BASE_FIND.formatted("WHERE email = :email");

        public static final String FIND_BY_USERNAME = BASE_FIND.formatted("WHERE username = :username");

        public static final String SAVE = """
                INSERT INTO users (email, username, password, bio, image)
                VALUES (:email, :username, :password, :bio, :image)
                """;

        public static final String UPDATE = """
                UPDATE users
                   SET email = :email,
                       username = :username,
                       password = :password,
                       bio = :bio,
                       image = :image
                 WHERE id = :id
                """;

    }

    public static class Profile {
        private static final String BASE_FIND = """
                SELECT id,
                       email,
                       username,
                       password,
                       bio,
                       image
                  FROM users
                  %s
                """;

        public static final String FIND_BY_USERNAME = """
                WITH is_follow AS (
                    SELECT COUNT(*)>0 AS following
                      FROM follow_relation
                     WHERE id_followee = (SELECT id FROM users WHERE username = :currentUsername) AND
                           id_follower = (SELECT id FROM users WHERE username = :profileUsername)
                    )
                SELECT username,
                       bio,
                       image,
                       (SELECT following FROM is_follow)
                  FROM users
                 WHERE username = :profileUsername
                """;
    }
}
