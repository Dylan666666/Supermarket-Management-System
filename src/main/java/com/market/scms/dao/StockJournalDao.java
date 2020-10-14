package com.market.scms.dao;

import com.market.scms.entity.StockJournal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/14 20:17
 */
public interface StockJournalDao {
    /**
     * 通过入库流水编号查询入库流水账表
     * 
     * @param stockJournalId
     * @return
     */
    StockJournal queryStockJournalById(@Param("stockJournalId") Long stockJournalId);

    /**
     * 查询所有的入库流水账表的集合
     * 
     * @return
     */
    List<StockJournal> queryStockJournalList();

    /**
     * 条件查询入库流水账表集合
     * 
     * @param stockJournalCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<StockJournal> queryStockJournalListByCondition(@Param("stockJournalCondition")StockJournal stockJournalCondition, 
                                                        @Param("rowIndex") int rowIndex,
                                                        @Param("pageSize") int pageSize);

    /**
     * 添加入库流水账表集合
     * 
     * @param stockJournal
     * @return
     */
    int insertStockJournal(StockJournal stockJournal);
}
