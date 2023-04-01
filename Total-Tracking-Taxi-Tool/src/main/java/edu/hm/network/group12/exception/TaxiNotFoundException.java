package edu.hm.network.group12.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaxiNotFoundException extends RuntimeException {

    public TaxiNotFoundException() {
        super();
    }

    public TaxiNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaxiNotFoundException(String message) {
        super(message);
    }

    public TaxiNotFoundException(Throwable cause) {
        super(cause);
    }
}
