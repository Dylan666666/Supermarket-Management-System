package com.market.scms.service.impl;

import com.market.scms.dao.SupermarketStaffDao;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.enums.StaffStatusStateEnum;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.StaffService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 14:18
 */
@Service
public class StaffServiceImpl implements StaffService {
    
    @Resource
    private SupermarketStaffDao staffDao;
    
    @Override
    public SupermarketStaff queryStaffByPhone(String staffPhone) throws SupermarketStaffException {
        if (staffPhone != null) {
            try {
                SupermarketStaff staff = staffDao.queryStaffByPhone(staffPhone);
                return staff;
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("查询失败");
            }
        } else {
            throw new SupermarketStaffException("信息为空");
        }
    }

    @Override
    public int insertStaff(SupermarketStaff staff) throws SupermarketStaffException {
        if (staff != null && staff.getStaffPhone() != null && staff.getStaffPassword() != null &&
                staff.getStaffName() != null) {
            try {
                staff.setLastEditTime(new Date());
                staff.setCreateTime(new Date());
                staff.setStaffStatus(StaffStatusStateEnum.JUST_REGISTERED.getState());
                int res = staffDao.insertStaff(staff);
                if (res == 0) {
                    throw new SupermarketStaffException("注册失败");
                }
                return res;
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("注册失败");
            }
        } else {
            throw new SupermarketStaffException("必要信息为空");
        }
    }

    @Override
    public int updateStaff(SupermarketStaff staff) throws SupermarketStaffException {
        if (staff != null) {
            try {
                staff.setLastEditTime(new Date());
                int res = staffDao.updateStaff(staff);
                if (res == 0) {
                    throw new SupermarketStaffException("信息更改失败");
                }
                return res;
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("信息更改失败");
            }
        } else {
            throw new SupermarketStaffException("信息为空");
        }
    }

    @Override
    public SupermarketStaff staffLogin(String staffPhone, String staffPassword) throws SupermarketStaffException {
        if (staffPhone != null && staffPassword != null) {
            try {
                SupermarketStaff staff = staffDao.staffLogin(staffPhone, staffPassword);
                return staff;
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("登录失败");
            }
        } else {
            throw new SupermarketStaffException("账号或密码为空");
        }
    }

    @Override
    public SupermarketStaff findByToken(String token) throws SupermarketStaffException {
        if (token != null) {
            try {
                SupermarketStaff staff = staffDao.findByToken(token);
                return staff;
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("查询失败");
            }
        } else {
            throw new SupermarketStaffException("token为空");
        }
    }

    @Override
    public List<SupermarketStaff> queryStaffByCondition(SupermarketStaff staffCondition, int pageIndex, int pageSize)
            throws SupermarketStaffException{
        if (staffCondition != null && pageIndex != -1000 && pageSize != -1000) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<SupermarketStaff> list = staffDao.queryStaffByCondition(staffCondition, rowIndex, pageSize);
                return list;
            } catch (SupermarketStaffException staffException) {
                throw new SupermarketStaffException("查询失败");
            }
        } else {
            throw new SupermarketStaffException("信息不足，查询失败");
        }
    }

    @Override
    public void staffLogout(String staffToken) throws SupermarketStaffException {
        try {
            SupermarketStaff staff = staffDao.findByToken(staffToken);
            staff.setToken(UUID.randomUUID().toString());
            staffDao.updateStaff(staff);
        } catch (SupermarketStaffException staffException) {
            throw new SupermarketStaffException("消除token出错");
        }
    }
}
