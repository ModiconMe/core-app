package edu.modicon.app.application.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    private ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    private static ApiException exception(HttpStatus status, String message, Object... args) {
        return new ApiException(status, format(message, args));
    }

    public static ApiException notFound(String message, Object... args) {
        return exception(HttpStatus.NOT_FOUND, message, args);
    }

    public static ApiException unauthorized(String message, Object... args) {
        return exception(HttpStatus.UNAUTHORIZED, message, args);
    }

    public static ApiException forbidden(String message, Object... args) {
        return exception(HttpStatus.FORBIDDEN, message, args);
    }

    public static ApiException unprocessableEntity(String message, Object... args) {
        return exception(HttpStatus.UNPROCESSABLE_ENTITY, message, args);
    }
}
