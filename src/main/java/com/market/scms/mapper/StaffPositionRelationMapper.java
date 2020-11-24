package com.market.scms.mapper;

import com.market.scms.entity.staff.StaffPosition;
import com.market.scms.entity.staff.StaffPositionRelation;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:35
 */
public interface StaffPositionRelationMapper {

    /**
     * 添加职工职位信息
     *
     * @param position
     * @return
     */
    int insert(StaffPositionRelation position);

    /**
     * 删除职工职位信息
     *
     * @param position
     * @return
     */
    int delete(StaffPositionRelation position);

    /**
     * 通过职位id查询职工职位行数据
     *
     * @param staffId
     * @return
     */
    List<StaffPositionRelation> queryById(int staffId);

    /**
     * 更改职位状态
     *
     * @param staffPositionRelation
     * @return
     */
    int update(StaffPositionRelation staffPositionRelation);

    /**
     * 一键查询
     * 
     * @return
     */
    List<StaffPositionRelation> queryAll();
}
