package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:56
 */
public class OutboundJournalException extends RuntimeException {
    private static final long serialVersionUID = -8691640940195758961L;

    public OutboundJournalException(String message) {
        super(message);
    }
}
