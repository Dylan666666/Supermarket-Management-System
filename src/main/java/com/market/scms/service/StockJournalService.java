package com.market.scms.service;

import com.market.scms.entity.StockJournal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/15 9:00
 */
public interface StockJournalService {

    /**
     * 通过入库流水编号查询入库流水账表
     *
     * @param stockJournalId
     * @return
     */
    StockJournal queryStockJournalById(Long stockJournalId);

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
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<StockJournal> queryStockJournalListByCondition(StockJournal stockJournalCondition, int pageIndex, int pageSize);

    /**
     * 添加入库流水账表集合
     *
     * @param stockJournal
     * @return
     */
    int insertStockJournal(StockJournal stockJournal);
    
}
