package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/5 16:42
 */
public class RefundException extends RuntimeException {
    private static final long serialVersionUID = 4607083331103856542L;

    public RefundException(String message) {
        super(message);
    }
}
