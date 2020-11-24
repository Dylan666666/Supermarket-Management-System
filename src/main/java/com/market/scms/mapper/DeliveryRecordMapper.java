package com.market.scms.mapper;

import com.market.scms.entity.DeliveryRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/23 22:10
 */
public interface DeliveryRecordMapper {
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
     * @param rowIndex
     * @param pageSize 
     * @return
     */
    List<DeliveryRecord> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 模糊查询出库详情单
     * 
     * @param deliveryRecordCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<DeliveryRecord> queryByCondition(@Param("deliveryRecordCondition") DeliveryRecord deliveryRecordCondition, 
                                    @Param("rowIndex") int rowIndex, 
                                    @Param("pageSize") int pageSize);
}
