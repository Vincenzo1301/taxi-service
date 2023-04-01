package edu.hm.network.group12.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TaxiNotAvailableException extends RuntimeException {

    public TaxiNotAvailableException() {
        super();
    }

    public TaxiNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaxiNotAvailableException(String message) {
        super(message);
    }

    public TaxiNotAvailableException(Throwable cause) {
        super(cause);
    }
}
