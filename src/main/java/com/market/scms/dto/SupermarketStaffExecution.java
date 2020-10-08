package com.market.scms.dto;

import com.market.scms.entity.SupermarketStaff;
import com.market.scms.vo.TokenVO;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 16:40
 */
public class SupermarketStaffExecution {
    private TokenVO tokenVO;
    private SupermarketStaff staff;

    public SupermarketStaffExecution(TokenVO tokenVO, SupermarketStaff staff) {
        this.tokenVO = tokenVO;
        this.staff = staff;
    }

    public TokenVO getTokenVO() {
        return tokenVO;
    }

    public void setTokenVO(TokenVO tokenVO) {
        this.tokenVO = tokenVO;
    }

    public SupermarketStaff getStaff() {
        return staff;
    }

    public void setStaff(SupermarketStaff staff) {
        this.staff = staff;
    }
}
