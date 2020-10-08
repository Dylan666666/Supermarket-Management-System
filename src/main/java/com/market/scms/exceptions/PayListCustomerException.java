package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:59
 */
public class PayListCustomerException extends RuntimeException {

    private static final long serialVersionUID = -3380867083037008000L;

    public PayListCustomerException(String message) {
        super(message);
    }
}
