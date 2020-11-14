package com.market.scms.util;

import com.market.scms.entity.SupermarketStaff;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/12 18:48
 */
public class PasswordHelper {

    private final String algorithmName = "MD5";
    private final int hashIterations = 20;

    public void encryptPassword(SupermarketStaff staff) {
        //String salt = randomNumberGenerator.nextBytes().toHex();
        String newPassword = new SimpleHash(algorithmName, staff.getStaffPassword(),
                ByteSource.Util.bytes(staff.getStaffPhone()), hashIterations).toHex();
        //String newPassword = new SimpleHash(algorithmName, user.getPassword()).toHex();
        staff.setStaffPassword(newPassword);
    }

    public String encryptPassword(String staffPhone, String staffPassword) {
        String newPassword = new SimpleHash(algorithmName, staffPassword,
                ByteSource.Util.bytes(staffPhone), hashIterations).toHex();
        return newPassword; 
    }
}
