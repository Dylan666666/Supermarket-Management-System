package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 14:03
 */
public class StockInJournal extends RuntimeException {

    private static final long serialVersionUID = -4528755834466000701L;

    public StockInJournal(String message) {
        super(message);
    }
}
