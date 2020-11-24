package com.market.scms.service.impl;

import com.market.scms.entity.staff.StaffPosition;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.mapper.StaffPositionMapper;
import com.market.scms.service.StaffPositionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 21:17
 */
@Service
public class StaffPositionServiceImpl implements StaffPositionService {
    
    @Resource
    private StaffPositionMapper staffPositionMapper;
    
    @Override
    public int insert(StaffPosition position) throws SupermarketStaffException {
        isNull(position);
        try {
            int res = staffPositionMapper.insert(position);
            if (res == 0) {
                throw new SupermarketStaffException("添加失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("添加失败");
        }
    }

    @Override
    public int delete(int staffPositionId) throws SupermarketStaffException {
        try {
            int res = staffPositionMapper.delete(staffPositionId);
            if (res == 0) {
                throw new SupermarketStaffException("删除失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("删除失败");
        }
    }

    @Override
    public StaffPosition queryById(int staffPositionId) throws SupermarketStaffException {
        try {
            StaffPosition position = staffPositionMapper.queryById(staffPositionId);
            return position;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("查询失败");
        }
    }

    @Override
    public int update(StaffPosition position) throws SupermarketStaffException {
        isNull(position);
        try {
            int res = staffPositionMapper.update(position);
            if (res == 0) {
                throw new SupermarketStaffException("更改失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("更改失败");
        }
    }

    @Override
    public List<StaffPosition> queryAll() throws SupermarketStaffException {
        try {
            List<StaffPosition> staffPositionList = staffPositionMapper.queryAll();
            return staffPositionList;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("查询职位字典失败");
        }
    }

    private void isNull(StaffPosition position) {
        if (position == null) {
            throw new SupermarketStaffException("传入信息为空");
        }
    }
    
}
