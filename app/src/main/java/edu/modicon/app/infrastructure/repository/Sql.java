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
    }
}
