package com.market.scms.service;

import com.market.scms.entity.Delivery;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/23 21:33
 */
public interface DeliveryService {

    public final static String DELIVERY_LIST_KEY = "deliveryList";
    
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
    List<Delivery> queryByDeliveryId(String deliveryId);

    /**
     * 通过商品ID查询出库单
     *
     * @param deliveryId
     * @param goodsId
     * @return
     */
    Delivery queryByGoodsId(String deliveryId, Long goodsId);

    /**
     * 一键查询出库单
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Delivery> queryAll(int pageIndex, int pageSize);
}
