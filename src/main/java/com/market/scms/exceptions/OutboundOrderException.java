package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:57
 */
public class OutboundOrderException extends RuntimeException {

    private static final long serialVersionUID = -2248055858118483435L;

    public OutboundOrderException(String message) {
        super(message);
    }
}
