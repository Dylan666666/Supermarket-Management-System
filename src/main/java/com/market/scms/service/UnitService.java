package com.market.scms.service;

import com.market.scms.entity.Unit;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 13:48
 */
public interface UnitService {

    public final static String UNIT_LIST_KEY = "unitList";
    
    /**
     * 查询销售单位列表
     *
     * @return
     */
    List<Unit> queryAll();

    /**
     * 添加销售单位
     *
     * @param unit
     * @return
     */
    int insert(Unit unit);

    /**
     * ID查询单位
     *
     * @param unitId
     * @return
     */
    Unit queryById(int unitId);

    /**
     * 更新销售单位
     *
     * @param unit
     * @return
     */
    int update(Unit unit);
}
