package org.example.server.exceptions;

public class UserDoesNotExistException extends Exception {
   public UserDoesNotExistException(String message ){
        super(message);
    }
}
