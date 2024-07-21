package org.example.test.respone;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
