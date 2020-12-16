package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/16 14:43
 */
public class LoggingEventException extends RuntimeException {
    private static final long serialVersionUID = -4764272062582726210L;

    public LoggingEventException(String message) {
        super(message);
    }
}
