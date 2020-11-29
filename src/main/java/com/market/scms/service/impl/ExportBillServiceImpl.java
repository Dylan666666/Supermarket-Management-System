package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.ExportBill;
import com.market.scms.enums.ExportBillStatusStateEnum;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.ExportBillMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.ExportBillService;
import com.market.scms.util.ExportBillIdCreator;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 20:10
 */
@Service
public class ExportBillServiceImpl implements ExportBillService {
    
    @Resource
    private ExportBillMapper exportBillMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insert(ExportBill exportBill, Long couponGoodsId) throws WareHouseManagerException {
        if (exportBill != null && couponGoodsId != null) {
            exportBill.setExportBillStatus(ExportBillStatusStateEnum.START.getState());
            exportBill.setExportBillId(ExportBillIdCreator.get(couponGoodsId));
            try {
                int res = exportBillMapper.insert(exportBill);
                if (res == 0) {
                    throw new WareHouseManagerException("添加失败");
                }
                cacheService.removeFromCache(EXPORT_BILL_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加失败");
            }
        } else {
            throw new WareHouseManagerException("信息不完整，添加失败");
        }
    }

    @Override
    public int update(ExportBill exportBill) throws WareHouseManagerException {
        if (exportBill != null && exportBill.getExportBillCouponId() != null && exportBill.getExportBillStatus() != null) {
            try {
                if (exportBill.getExportBillStatus().equals(ExportBillStatusStateEnum.TO_STOCK.getState())) {
                    exportBill.setExportBillTime(new Date());
                }
                int res = exportBillMapper.update(exportBill);
                if (res == 0) {
                    throw new WareHouseManagerException("信息更改失败");
                }
                cacheService.removeFromCache(EXPORT_BILL_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("信息更改失败");
            }
        } else {
            throw new WareHouseManagerException("信息不完整，更改失败");
        }
    }

    @Override
    public ExportBill queryByBillId(String id) throws WareHouseManagerException {
        if (id != null) {
            try {
                ExportBill exportBill = exportBillMapper.queryByBillId(id);
                return exportBill;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        } else {
            throw new WareHouseManagerException("信息不完整，查询失败");
        }
    }

    @Override
    public ExportBill queryByCouponId(Long couponId) throws WareHouseManagerException {
        if (couponId != null) {
            try {
                ExportBill exportBill = exportBillMapper.queryByCouponId(couponId);
                return exportBill;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        } else {
            throw new WareHouseManagerException("信息不完整，查询失败");
        }
    }

    @Override
    public List<ExportBill> queryByBillStatus(int exportBillStatus) throws WareHouseManagerException {
        try {
            List<ExportBill> res = exportBillMapper.queryByBillStatus(exportBillStatus);
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("查询失败");
        }
    }

    @Override
    public List<ExportBill> queryAll(int pageIndex, int pageSize) throws WareHouseManagerException {
        String key = EXPORT_BILL_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<ExportBill> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = exportBillMapper.queryAll(rowIndex, pageSize);
                String jsonString = null;
                try {
                    jsonString = mapper.writeValueAsString(res);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
                jedisStrings.set(key, jsonString);
            } else {
                String jsonString = jedisStrings.get(key);
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ExportBill.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
            }
        } else {
            try {
                res = exportBillMapper.queryAll(rowIndex, pageSize);
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }

    @Override
    public List<ExportBill> queryByCondition(ExportBill exportBillCondition, int pageIndex, int pageSize) 
            throws WareHouseManagerException {
        if (exportBillCondition != null) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<ExportBill> exportBillList = exportBillMapper.queryByCondition(exportBillCondition, rowIndex, pageSize);
                return exportBillList;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        } else {
            throw new WareHouseManagerException("查询失败");
        }
    }
}
