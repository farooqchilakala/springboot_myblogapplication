package net.farooq.Storify.exception;

import org.springframework.http.HttpStatus;

public class StorifyBlogApiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public StorifyBlogApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
