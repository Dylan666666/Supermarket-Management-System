package com.market.scms.mapper;

import com.market.scms.entity.SupermarketStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 9:40
 */
public interface SupermarketStaffMapper {

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
     * 更改职工职位 (高权限)
     * 
     * @param staff
     * @return
     */
    int updateStaffPosition(SupermarketStaff staff);

    /**
     * 职工登录
     * 
     * @param staffPhone
     * @param staffPassword
     * @return
     */
    SupermarketStaff staffLogin(@Param("staffPhone") String staffPhone, @Param("staffPassword") String staffPassword);

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

    /**
     * 通过ID查询
     * 
     * @param staffId
     * @return
     */
    SupermarketStaff queryById(int staffId);

    /**
     * 1.4超级管理员 用户列表 删除
     * 
     * @param staffId
     * @return
     */
    int deleteStaff(int staffId);
}
