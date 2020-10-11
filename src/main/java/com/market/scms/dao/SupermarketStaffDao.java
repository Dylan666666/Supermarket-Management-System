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
     * 通过电话号码查询职工信息
     * 
     * @param staffPhone
     * @return
     */
    SupermarketStaff queryStaffByPhone(@Param("staffPhone") String staffPhone);

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
    SupermarketStaff staffLogin(@Param("staffPhone") String staffPhone, @Param("staffPassword") String staffPassword);

    /**
     * 通过token查找职工
     * 
     * @param token
     * @return
     */
    SupermarketStaff findByToken(String token);

    /**
     * 职工信息模糊查询
     * 
     * @param staffCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<SupermarketStaff> queryStaffByCondition(@Param("staffCondition")SupermarketStaff staffCondition,
                                                 @Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
    
}
