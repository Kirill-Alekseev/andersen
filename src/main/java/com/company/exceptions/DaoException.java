package com.company.exceptions;

public class DaoException extends RuntimeException {

    public DaoException(String format, Object... args) {
        super(String.format(format, args));
    }
}
