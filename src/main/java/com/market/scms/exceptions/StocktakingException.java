package com.market.scms.exceptions;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/5 16:43
 */
public class StocktakingException extends RuntimeException {
    private static final long serialVersionUID = -4555257441999046340L;

    public StocktakingException(String message) {
        super(message);
    }
}
