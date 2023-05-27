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

        public static final String FIND_BY_USERNAME = """
                WITH is_follow AS (
                    SELECT COUNT(*)>0 AS following
                      FROM follow_relation
                     WHERE id_followee = (SELECT id FROM users WHERE username = :profileUsername) AND
                           id_follower = (SELECT id FROM users WHERE username = :currentUsername)
                    )
                SELECT username,
                       bio,
                       image,
                       (SELECT following FROM is_follow)
                  FROM users
                 WHERE username = :profileUsername
                """;

        public static final String FOLLOW = """
                INSERT INTO follow_relation (id_followee, id_follower)
                VALUES ((SELECT id FROM users WHERE username = :currentUsername),
                        (SELECT id FROM users WHERE username = :profileUsername))
                       RETURNING id_followee;
                """;

        public static final String UNFOLLOW = """
                DELETE FROM follow_relation
                 WHERE id_followee = (SELECT id FROM users WHERE username = :profileUsername)
                   AND id_follower = (SELECT id FROM users WHERE username = :currentUsername)
                       RETURNING id_followee;
                """;
    }
}
