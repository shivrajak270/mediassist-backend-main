package com.shivraj.medassist.Exceptions;

public class MedicineNotFoundException extends RuntimeException {
    public MedicineNotFoundException(String message) {
        super(message);
    }
}
