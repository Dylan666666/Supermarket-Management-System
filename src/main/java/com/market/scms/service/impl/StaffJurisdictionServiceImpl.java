package com.market.scms.service.impl;

import com.market.scms.entity.staff.StaffJurisdiction;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.mapper.StaffJurisdictionMapper;
import com.market.scms.service.StaffJurisdictionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 19:14
 */
@Service
public class StaffJurisdictionServiceImpl implements StaffJurisdictionService {
    
    @Resource
    private StaffJurisdictionMapper staffJurisdictionMapper;
    
    @Override
    public int insert(StaffJurisdiction staffJurisdiction) throws SupermarketStaffException {
        isNull(staffJurisdiction);
        try {
            int res = staffJurisdictionMapper.insert(staffJurisdiction);
            if (res == 0) {
                throw new SupermarketStaffException("添加失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("添加失败");
        }
    }

    @Override
    public List<StaffJurisdiction> queryById(int staffId) throws SupermarketStaffException {
        try {
            List<StaffJurisdiction> res = staffJurisdictionMapper.queryById(staffId);
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("查询失败");
        }
    }

    @Override
    public int update(StaffJurisdiction staffJurisdiction) throws SupermarketStaffException {
        isNull(staffJurisdiction);
        try {
            int res = staffJurisdictionMapper.update(staffJurisdiction);
            if (res ==  0) {
                throw new SupermarketStaffException("更新失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("更新失败");
        }
    }
    
    private void isNull(StaffJurisdiction staffJurisdiction) {
        if (staffJurisdiction == null) {
            throw new SupermarketStaffException("传入信息为空");
        }
    }
}
