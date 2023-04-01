package edu.hm.network.group12.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LampException extends RuntimeException {

    public LampException() {
        super();
    }

    public LampException(String message, Throwable cause) {
        super(message, cause);
    }

    public LampException(String message) {
        super(message);
    }

    public LampException(Throwable cause) {
        super(cause);
    }
}

