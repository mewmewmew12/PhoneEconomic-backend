package org.example.test.respone;

public class BadResquestException extends RuntimeException{
    public BadResquestException(String mess){
        super(mess);
    }
}
