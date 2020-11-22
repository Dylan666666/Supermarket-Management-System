package com.market.scms.mapper;

import com.market.scms.entity.ExportBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 19:09
 */
public interface ExportBillMapper {
    /**
     * 生成入库单
     * 
     * @param exportBill
     * @return
     */
    int insert(ExportBill exportBill);

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
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<ExportBill> queryAll(@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
}
