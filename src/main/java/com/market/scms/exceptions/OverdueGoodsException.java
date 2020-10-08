package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:58
 */
public class OverdueGoodsException extends RuntimeException {
    private static final long serialVersionUID = -4376131608091033834L;

    public OverdueGoodsException(String message) {
        super(message);
    }
}
