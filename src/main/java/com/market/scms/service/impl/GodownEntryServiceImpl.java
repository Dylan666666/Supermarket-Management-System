package com.market.scms.service.impl;

import com.market.scms.dao.GodownEntryDao;
import com.market.scms.entity.GodownEntry;
import com.market.scms.exceptions.GodownEntryException;
import com.market.scms.service.GodownEntryService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/13 21:11
 */
@Service
public class GodownEntryServiceImpl implements GodownEntryService {
    
    @Resource
    private GodownEntryDao godownEntryDao;

    /**
     * 通过入库单号查询入库单
     * 
     * @param godownEntryId
     * @return
     * @throws GodownEntryException
     */
    @Override
    public GodownEntry queryEntryById(Long godownEntryId) throws GodownEntryException {
        if (godownEntryId > 0) {
            try {
                GodownEntry entry = godownEntryDao.queryEntryById(godownEntryId);
                return entry;
            } catch (GodownEntryException e) {
                throw new GodownEntryException("查询失败");
            }
        } else {
            throw new GodownEntryException("传入数据有错");
        }
    }

    /**
     * 通过商品单号查询入库单
     * 
     * @param godownEntryGoodsId
     * @return
     */
    @Override
    public GodownEntry queryEntryByGoodsId(Long godownEntryGoodsId) throws GodownEntryException {
        if (godownEntryGoodsId > 0) {
            try {
                GodownEntry godownEntry = godownEntryDao.queryEntryByGoodsId(godownEntryGoodsId);
                return godownEntry;
            } catch (GodownEntryException e) {
                throw new GodownEntryException("查询失败");
            }
        } else {
            throw new GodownEntryException("传入数据有错");
        }
    }

    /**
     * 根据供应商代码查询入库单集合
     * 
     * @param godownEntrySupplierId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<GodownEntry> queryEntryListBySupplierId(Long godownEntrySupplierId, int pageIndex, int pageSize)
            throws GodownEntryException {
        if (godownEntrySupplierId > 0 && pageIndex >= 0 && pageSize > 0) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<GodownEntry> list = godownEntryDao.queryEntryListBySupplierId(godownEntrySupplierId,
                        rowIndex, pageSize);
                return list;
            } catch (GodownEntryException e) {
                throw new GodownEntryException("查询失败");
            }
        } else {
            throw new GodownEntryException("传入数据有错");
        }
    }

    /**
     * 模糊查询（主用于根据状态属性查询）
     * 
     * @param godownCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<GodownEntry> queryEntryListByGodownCondition(GodownEntry godownCondition, int pageIndex, int pageSize) 
    throws GodownEntryException {
        if (godownCondition != null && pageIndex >= 0 && pageSize > 0) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<GodownEntry> list = godownEntryDao
                        .queryEntryListByGodownCondition(godownCondition, rowIndex, pageSize);
                return list;
            } catch (GodownEntryException e) {
                throw new GodownEntryException("查询失败");
            }
        } else {
            throw new GodownEntryException("传入数据有错");
        }
    }

    /**
     * 添加新入库单信息
     * 
     * @param godownEntry
     * @return
     */
    @Override
    public int insertEntry(GodownEntry godownEntry) throws GodownEntryException {
        if (godownEntry != null) {
            try {
                int res = godownEntryDao.insertEntry(godownEntry);
                if (res == 0) {
                    throw new GodownEntryException("添加入库失败");
                }
                return res;
            } catch (GodownEntryException e) {
                throw new GodownEntryException("添加入库失败");
            }
        } else {
            throw new GodownEntryException("传入数据有错");
        }
    }

    /**
     * 更改入库单信息
     * 
     * @param godownEntry
     * @return
     */
    @Override
    public int updateEntry(GodownEntry godownEntry) throws GodownEntryException {
        if (godownEntry != null) {
            try {
                int res = godownEntryDao.updateEntry(godownEntry);
                if (res == 0) {
                    throw new GodownEntryException("更改失败");
                }
                return res;
            } catch (GodownEntryException e) {
                throw new GodownEntryException("更改失败");
            }
        } else {
            throw new GodownEntryException("传入数据有错");
        }
    }
}
