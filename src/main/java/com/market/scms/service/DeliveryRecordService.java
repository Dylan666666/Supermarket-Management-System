package com.market.scms.service;

import com.market.scms.entity.DeliveryRecord;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/24 15:13
 */
public interface DeliveryRecordService {

    public final static String DELIVERY_RECORD_LIST_KEY = "deliveryRecordList";
    
    /**
     * 添加出库详情单
     *
     * @param deliveryRecord
     * @return
     */
    int insert(DeliveryRecord deliveryRecord);

    /**
     * 更新出库详情单
     *
     * @param deliveryRecord
     * @return
     */
    int update(DeliveryRecord deliveryRecord);

    /**
     * 通过ID查询出库详情单
     *
     * @param deliveryId
     * @return
     */
    DeliveryRecord queryByDeliveryId(String deliveryId);

    /**
     * 一键查询出库详情单
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<DeliveryRecord> queryAll(int pageIndex, int pageSize);

    /**
     * 模糊查询出库详情单
     *
     * @param deliveryRecordCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<DeliveryRecord> queryByCondition(DeliveryRecord deliveryRecordCondition,
                                          int pageIndex,
                                          int pageSize);
}
