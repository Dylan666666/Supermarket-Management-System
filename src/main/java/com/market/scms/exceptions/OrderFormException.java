package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:55
 */
public class OrderFormException extends RuntimeException {
    private static final long serialVersionUID = -4459058675300460565L;

    public OrderFormException(String message) {
        super(message);
    }
}
