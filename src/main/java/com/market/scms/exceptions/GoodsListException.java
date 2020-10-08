package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:55
 */
public class GoodsListException extends RuntimeException {
    private static final long serialVersionUID = -1153579087113067131L;

    public GoodsListException(String message) {
        super(message);
    }
}
