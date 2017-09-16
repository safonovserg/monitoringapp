package com.app.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Sergey on 13.09.2017.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MeasurementNotFound extends RuntimeException{
    public MeasurementNotFound(String userId) {
        super("could not find user '" + userId + "'.");
    }
}
