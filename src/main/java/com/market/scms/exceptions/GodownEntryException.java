package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:54
 */
public class GodownEntryException extends RuntimeException {

    private static final long serialVersionUID = 3295267570105846461L;

    public GodownEntryException(String message) {
        super(message);
    }
}
