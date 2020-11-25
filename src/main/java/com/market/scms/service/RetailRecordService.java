package com.market.scms.service;

import com.market.scms.entity.RetailRecord;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 12:48
 */
public interface RetailRecordService {

    public final static String RETAIL_RECORD_LIST_KEY = "retailRecordList";

    /**
     * 添加订货详情单
     *
     * @param retailRecord
     * @return
     */
    int insert(RetailRecord retailRecord);

    /**
     * 更新订货详情单
     *
     * @param retailRecord
     * @return
     */
    int update(RetailRecord retailRecord);

    /**
     * 通过ID查询订货详情单
     *
     * @param retailId
     * @return
     */
    RetailRecord queryByRetailId(String retailId);

    /**
     * 一键查询订货详情单
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RetailRecord> queryAll(int pageIndex, int pageSize);

    /**
     * 模糊查询订货详情单
     *
     * @param retailRecordCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RetailRecord> queryByCondition(RetailRecord retailRecordCondition, int pageIndex, int pageSize);
}
