package com.market.scms.service;

import com.market.scms.entity.ExportBill;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 20:09
 */
public interface ExportBillService {
    /**
     * 生成入库单
     * 
     * @param exportBill
     * @param couponGoodsId
     * @return
     */
    int insert(ExportBill exportBill, Long couponGoodsId);

    /**
     * 更新入库单信息
     *
     * @param exportBill
     * @return
     */
    int update(ExportBill exportBill);

    /**
     * 通过入库单ID查询入库单
     *
     * @param id
     * @return
     */
    ExportBill queryByBillId(String id);

    /**
     * 通过订货单ID查询入库单
     *
     * @param couponId
     * @return
     */
    ExportBill queryByCouponId(Long couponId);

    /**
     * 通过入库单状态查询入库单集合
     *
     * @param exportBillStatus
     * @return
     */
    List<ExportBill> queryByBillStatus(int exportBillStatus);

    /**
     * 一键查询入库单
     *
     * @return
     */
    List<ExportBill> queryAll(int pageIndex, int pageSize);
}
