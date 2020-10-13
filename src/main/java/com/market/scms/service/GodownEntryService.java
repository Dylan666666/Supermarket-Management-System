package com.market.scms.service;

import com.market.scms.entity.GodownEntry;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/13 21:09
 */
public interface GodownEntryService {
    /**
     * 通过入库单号查询入库单
     *
     * @param godownEntryId
     * @return
     */
    GodownEntry queryEntryById(Long godownEntryId);

    /**
     * 通过商品单号查询入库单
     *
     * @param godownEntryGoodsId
     * @return
     */
    GodownEntry queryEntryByGoodsId(Long godownEntryGoodsId);

    /**
     * 根据供应商代码查询入库单集合
     *
     * @param godownEntrySupplierId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<GodownEntry> queryEntryListBySupplierId(Long godownEntrySupplierId, int pageIndex, int pageSize);

    /**
     * 添加新入库单信息
     *
     * @param godownEntry
     * @return
     */
    int insertEntry(GodownEntry godownEntry);

    /**
     * 更改入库单信息
     *
     * @param godownEntry
     * @return
     */
    int updateEntry(GodownEntry godownEntry);
}
