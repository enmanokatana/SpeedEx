package org.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExamNotFoundException extends RuntimeException {
    public ExamNotFoundException(String examWasNotFound) {
        super(examWasNotFound);
    }
}
