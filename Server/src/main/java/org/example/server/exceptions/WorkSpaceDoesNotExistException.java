package org.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WorkSpaceDoesNotExistException extends Exception {
    public WorkSpaceDoesNotExistException(String s) {
        super(s);
    }
}
