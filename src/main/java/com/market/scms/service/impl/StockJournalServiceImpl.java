package com.market.scms.service.impl;

import com.market.scms.dao.StockJournalDao;
import com.market.scms.entity.StockJournal;
import com.market.scms.exceptions.StockJournalException;
import com.market.scms.service.StockJournalService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/15 9:02
 */
@Service
public class StockJournalServiceImpl implements StockJournalService {

    @Resource
    private StockJournalDao stockJournalDao;
    
    /**
     * 通过入库流水编号查询入库流水账表
     *
     * @param stockJournalId
     * @return
     */
    @Override
    public StockJournal queryStockJournalById(Long stockJournalId) throws StockJournalException {
        if (stockJournalId > 0) {
            try {
                StockJournal stockJournal = stockJournalDao.queryStockJournalById(stockJournalId);
                return stockJournal;
            } catch (StockJournalException e) {
                throw new StockJournalException("查询失败");
            }
        } else {
            throw new StockJournalException("传入信息有错");
        }
    }

    /**
     * 查询所有的入库流水账表的集合
     *
     * @return
     */
    @Override
    public List<StockJournal> queryStockJournalList() throws StockJournalException {
        try {
            List<StockJournal> list = stockJournalDao.queryStockJournalList();
            return list;
        } catch (StockJournalException e) {
            throw new StockJournalException("查询失败");
        }
    }

    /**
     * 条件查询入库流水账表集合
     *
     * @param stockJournalCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<StockJournal> queryStockJournalListByCondition(StockJournal stockJournalCondition, int pageIndex, int pageSize) 
            throws StockJournalException  {
        if (stockJournalCondition != null && pageIndex >= 0 && pageSize > 0) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<StockJournal> list = stockJournalDao.queryStockJournalListByCondition(stockJournalCondition,
                        rowIndex, pageSize);
                return list;
            } catch (StockJournalException e) {
                throw new StockJournalException("查询失败");
            }
        } else {
            throw new StockJournalException("传入信息有错");
        }
    }

    /**
     * 添加入库流水账表集合
     *
     * @param stockJournal
     * @return
     */
    @Override
    public int insertStockJournal(StockJournal stockJournal) throws StockJournalException {
        if (stockJournal != null && stockJournal.getStockJournalNum() != null &&
                stockJournal.getStockJournalDate() != null && stockJournal.getStockJournalGodownEntryId() != null &&
        stockJournal.getStockJournalGoodsId() != null && stockJournal.getStockJournalGoodsPrice() != null && 
        stockJournal.getStockJournalPaid() != null && stockJournal.getStockJournalSupplierId() != null) {
            try {
                int res = stockJournalDao.insertStockJournal(stockJournal);
                if (res == 0) {
                    throw new StockJournalException("添加流水账单失败");
                } 
                return res;
            } catch (StockJournalException e) {
                throw new StockJournalException("添加流水账单失败");
            }
        } else {
            throw new StockJournalException("信息不完整");
        }
    }
}
