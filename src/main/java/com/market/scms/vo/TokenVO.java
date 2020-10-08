package com.market.scms.vo;

import java.time.LocalDateTime;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 15:57
 */
public class TokenVO {
    private String token;
    private LocalDateTime expireTime;

    @Override
    public String toString() {
        return "TokenVO{" +
                "token='" + token + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
