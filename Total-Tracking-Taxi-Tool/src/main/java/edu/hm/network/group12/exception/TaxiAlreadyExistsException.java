package edu.hm.network.group12.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TaxiAlreadyExistsException extends Throwable {

    public TaxiAlreadyExistsException() {
        super();
    }

    public TaxiAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaxiAlreadyExistsException(String message) {
        super(message);
    }

    public TaxiAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
