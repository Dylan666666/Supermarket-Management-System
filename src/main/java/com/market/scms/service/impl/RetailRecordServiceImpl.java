package com.market.scms.service.impl;

import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.RetailRecord;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.RetailRecordMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.RetailRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 12:49
 */
@Service
public class RetailRecordServiceImpl implements RetailRecordService {
    
    @Resource
    private RetailRecordMapper retailRecordMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;

    private static Logger logger = LoggerFactory.getLogger(RetailRecordServiceImpl.class);


    @Override
    public int insert(RetailRecord retailRecord) throws WareHouseManagerException {
        if (retailRecord != null && retailRecord.getRetailId() != null && retailRecord.getRetailTotalPrice() != null
        && retailRecord.getRetailCollectionStaffId() != null) {
            try {
                retailRecord.setRetailTime(new Date());
                int res = retailRecordMapper.insert(retailRecord);
                if (res == 0) {
                    throw new WareHouseManagerException("添加订单详情单失败");
                }
                cacheService.removeFromCache(RETAIL_RECORD_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加订单详情单失败");
            }
        } else {
            throw new WareHouseManagerException("缺少必要信息，添加订单详情单失败");
        }
    }

    @Override
    public int update(RetailRecord retailRecord) throws WareHouseManagerException {
        if (retailRecord != null && retailRecord.getRetailRefundStatus() != null) {
            try {
                retailRecord.setRetailTime(new Date());
                int res = retailRecordMapper.update(retailRecord);
                if (res == 0) {
                    throw new WareHouseManagerException("更改订单详情单失败");
                }
                cacheService.removeFromCache(RETAIL_RECORD_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更改订单详情单失败");
            }
        } else {
            throw new WareHouseManagerException("缺少必要信息，更改订单详情单失败");
        }
    }

    @Override
    public RetailRecord queryByRetailId(String retailId) throws WareHouseManagerException {
        if (retailId != null) {
            try {
                RetailRecord retailRecord = retailRecordMapper.queryByRetailId(retailId);
                return retailRecord;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询订单详情单失败");
            }
        } else {
            throw new WareHouseManagerException("缺少必要信息，查询订单详情单失败");
        }
    }

    @Override
    public List<RetailRecord> queryAll(int pageIndex, int pageSize) throws WareHouseManagerException {
        return null;
    }

    @Override
    public List<RetailRecord> queryByCondition(RetailRecord retailRecordCondition, int pageIndex, int pageSize)
            throws WareHouseManagerException {
        return null;
    }
}
