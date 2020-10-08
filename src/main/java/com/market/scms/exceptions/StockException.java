package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 14:02
 */
public class StockException extends RuntimeException {

    private static final long serialVersionUID = -684212093906537407L;

    public StockException(String message) {
        super(message);
    }
}
