package com.market.scms.service.impl;

import com.market.scms.entity.Unit;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.UnitMapper;
import com.market.scms.service.UnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 13:49
 */
@Service
public class UnitServiceImpl implements UnitService {
    
    @Resource
    private UnitMapper unitMapper;
    
    @Override
    public List<Unit> queryAll() throws WareHouseManagerException {
        try {
            List<Unit> res = unitMapper.queryAll();
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("销售单位查询失败");
        }
    }

    @Override
    public int insert(Unit unit) throws WareHouseManagerException {
        try {
            int res = unitMapper.insert(unit);
            if (res == 0) {
                throw new WareHouseManagerException("销售单位插入失败");
            }
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("销售单位插入失败");
        }
    }

    @Override
    public Unit queryById(int unitId) throws WareHouseManagerException {
        if (unitId > 0) {
            Unit unit = unitMapper.queryById(unitId);
            return unit;
        } else {
            throw new WareHouseManagerException("查询单位失败");
        }
    }
}
