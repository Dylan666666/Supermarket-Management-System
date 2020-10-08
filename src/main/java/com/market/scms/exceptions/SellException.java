package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 14:02
 */
public class SellException extends RuntimeException {

    private static final long serialVersionUID = 2946428891309409346L;

    public SellException(String message) {
        super(message);
    }
}
