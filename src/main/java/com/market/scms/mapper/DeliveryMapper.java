package com.market.scms.mapper;

import com.market.scms.entity.Delivery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/23 21:07
 */
public interface DeliveryMapper {
    /**
     * 添加出库单
     *
     * @param delivery
     * @return
     */
    int insert(Delivery delivery);

    /**
     * 更新出库单信息
     *
     * @param delivery
     * @return
     */
    int update(Delivery delivery);

    /**
     * 通过ID查询出库单
     *
     * @param deliveryId
     * @return
     */
    Delivery queryByDeliveryId(String deliveryId);

    /**
     * 通过商品ID查询出库单
     *
     * @param goodsId
     * @return
     */
    Delivery queryByGoodsId(Long goodsId);
    
    /**
     * 一键查询出库单
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Delivery> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
}
