package com.market.scms.service.impl;

import com.market.scms.entity.staff.Function;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.FunctionMapper;
import com.market.scms.service.FunctionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 9:43
 */
@Service
public class FunctionServiceImpl implements FunctionService {

    @Resource
    private FunctionMapper functionMapper;

    @Override
    public int insert(Function function) throws SupermarketStaffException {
        isNull(function);
        try {
            int res = functionMapper.insert(function);
            if (res == 0) {
                throw new SupermarketStaffException("添加失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("添加失败");
        }
    }

    @Override
    public int delete(int functionId) throws SupermarketStaffException {
        try {
            int res = functionMapper.delete(functionId);
            if (res == 0) {
                throw new SupermarketStaffException("删除失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("删除失败");
        }
    }

    @Override
    public Function queryById(int functionId) throws SupermarketStaffException {
        try {
            Function function = functionMapper.queryById(functionId);
            return function;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("查询失败");
        }
    }

    @Override
    public int update(Function function) throws SupermarketStaffException {
        isNull(function);
        try {
            int res = functionMapper.update(function);
            if (res == 0) {
                throw new SupermarketStaffException("更改失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("更改失败");
        }
    }

    @Override
    public List<Function> querySecondaryMenuId(int secondaryMenuId) throws SupermarketStaffException {
        try {
            List<Function> res = functionMapper.queryBySecondaryMenuId(secondaryMenuId);
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("查询失败");
        }
    }

    private void isNull(Function function) {
        if (function == null) {
            throw new SupermarketStaffException("信息不能为空");
        }
    }
}
