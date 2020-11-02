package com.market.scms.util;

import com.market.scms.entity.SupermarketStaff;
import com.market.scms.entity.Supplier;
import com.market.scms.enums.TokenTimeEnum;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 创建token工具
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/2 14:40
 */
public class CreateToken {
    public static void staffCreateToken(SupermarketStaff staff) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusHours(TokenTimeEnum.EXPIRE_TIME.getState());
        String token = UUID.randomUUID().toString();
        staff.setToken(token);
        staff.setLoginTime(now);
        staff.setExpireTime(expireTime);
    }

    public static void supplierCreateToken(Supplier supplier) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusHours(TokenTimeEnum.EXPIRE_TIME.getState());
        String token = UUID.randomUUID().toString();
        supplier.setToken(token);
        supplier.setLoginTime(now);
        supplier.setExpireTime(expireTime);
    }
}
