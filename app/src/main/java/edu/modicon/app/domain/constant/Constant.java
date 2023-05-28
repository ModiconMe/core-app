package edu.modicon.app.domain.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Endpoint {
        public static final String API_PREFIX = "api";
        public static final String USER = API_PREFIX + "/user";
        public static final String USERS = API_PREFIX +"/users";
        public static final String PROFILES = API_PREFIX + "/profiles";
        public static final String ARTICLES = API_PREFIX + "/articles";
        public static final String TAGS = API_PREFIX + "/tags";
    }
}
