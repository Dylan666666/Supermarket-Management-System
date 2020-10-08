package com.market.scms.dao;

import com.market.scms.entity.SupermarketStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 9:40
 */
public interface SupermarketStaffDao {

    /**
     * 查询职工信息
     * 
     * @param staffPhone
     * @return
     */
    SupermarketStaff queryStaffByPhone(@Param("staffPhone") Long staffPhone);

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
    SupermarketStaff staffLogin(@Param("staffPhone") Long staffPhone, @Param("staffPassword") String staffPassword);
}
