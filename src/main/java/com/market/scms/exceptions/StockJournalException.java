package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 14:03
 */
public class StockJournalException extends RuntimeException {

    private static final long serialVersionUID = -4528755834466000701L;

    public StockJournalException(String message) {
        super(message);
    }
}
