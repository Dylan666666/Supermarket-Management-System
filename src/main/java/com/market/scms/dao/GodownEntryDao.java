package com.market.scms.dao;

import com.market.scms.entity.GodownEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/13 16:12
 */
public interface GodownEntryDao {

    /**
     * 通过入库单号查询入库单
     *
     * @param godownEntryId
     * @return
     */
    GodownEntry queryEntryById(@Param("godownEntryId") Long godownEntryId);

    /**
     * 通过商品单号查询入库单
     *
     * @param godownEntryGoodsId
     * @return
     */
    GodownEntry queryEntryByGoodsId(@Param("godownEntryGoodsId") Long godownEntryGoodsId);

    /**
     * 根据供应商代码查询入库单集合
     *
     * @param godownEntrySupplierId
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<GodownEntry> queryEntryListBySupplierId(@Param("godownEntrySupplierId") Long godownEntrySupplierId,
                                        @Param("rowIndex") int rowIndex,
                                        @Param("pageSize") int pageSize);

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
