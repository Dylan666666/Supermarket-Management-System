package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/5 16:41
 */
public class PurchaseException extends RuntimeException {
    private static final long serialVersionUID = 8011841783706513547L;

    public PurchaseException(String message) {
        super(message);
    }
}
