package com.fifa_api.services.exceptions;

public class ClientException extends RuntimeException {
    public ClientException(Exception e) {
        super(e);
    }
    public ClientException(String message) {
        super(message);
    }
}
