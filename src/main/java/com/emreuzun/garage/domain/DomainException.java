package com.emreuzun.garage.domain;

public class DomainException extends RuntimeException {

    public DomainException(final String message) {
        super(message);
    }

}