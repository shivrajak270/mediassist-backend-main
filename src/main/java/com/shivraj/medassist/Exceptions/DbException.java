package com.shivraj.medassist.Exceptions;

public class DbException extends RuntimeException{
    public DbException(String message){
        super(message);
    }
}
