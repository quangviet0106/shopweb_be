package com.example.shopapp.exceptions;

public class PermissionDenyException extends Exception{
    public PermissionDenyException(String mess){
        super(mess);
    }
}
