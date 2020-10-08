package com.market.scms.service;

import com.market.scms.entity.SupermarketStaff;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:43
 */
public interface SupermarketStaffService {
    /**
     * 通过电话号码查询职工信息
     *
     * @param staffPhone
     * @return
     */
    SupermarketStaff queryStaffByPhone(Long staffPhone);

    /**
     * 查询所有职工信息
     *
     * @return
     */
    List<SupermarketStaff> queryStaffList();

    /**
     * 注册职工信息
     *
     * @param staff
     * @return
     */
    int insertStaff(SupermarketStaff staff);

    /**
     * 更新职工信息
     *
     * @param staff
     * @return
     */
    int updateStaff(SupermarketStaff staff);

    /**
     * 职工登录
     *
     * @param staffPhone
     * @param staffPassword
     * @return
     */
    SupermarketStaff staffLogin(Long staffPhone, String staffPassword);
}
