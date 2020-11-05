package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/5 16:41
 */
public class SaleException extends RuntimeException {
    private static final long serialVersionUID = 8651061835728482446L;

    public SaleException(String message) {
        super(message);
    }
}
