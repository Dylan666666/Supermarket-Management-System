package com.market.scms.mapper;

import com.market.scms.entity.staff.StaffJurisdiction;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:34
 */
public interface StaffJurisdictionMapper {

    /**
     * 添加职工功能表
     *
     * @param staffJurisdiction
     * @return
     */
    int insert(StaffJurisdiction staffJurisdiction);

    /**
     * 通过职工ID查询功能表集合
     * 
     * @param staffId
     * @return
     */
    List<StaffJurisdiction> queryById(int staffId);

    /**
     * 更改行数据
     *
     * @param staffJurisdiction
     * @return
     */
    int update(StaffJurisdiction staffJurisdiction);

    /**
     * 删除功能权限
     *
     * @param staffId
     * @return
     */
    int delete(int staffId);

    /**
     * 一键查询
     * 
     * @return
     */
    List<StaffJurisdiction> queryAll();
    
}
