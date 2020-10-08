package com.market.scms.service.impl;

import com.market.scms.dao.SupermarketStaffDao;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.SupermarketStaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:44
 */
@Service
public class SupermarketStaffServiceImpl implements SupermarketStaffService {
    
    @Resource
    private SupermarketStaffDao supermarketStaffDao;

    @Override
    public SupermarketStaff queryStaffByPhone(Long staffPhone) throws SupermarketStaffException {
        SupermarketStaff staff = null;
        if (staffPhone > 0) {
            try {
                staff = supermarketStaffDao.queryStaffByPhone(staffPhone);
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("查询出错");
            }
        } else {
            throw new SupermarketStaffException("电话号码有错");
        }
        return staff;
    }

    @Override
    public List<SupermarketStaff> queryStaffList() {
        return null;
    }

    @Override
    public int insertStaff(SupermarketStaff staff) {
        return 0;
    }

    @Override
    public int updateStaff(SupermarketStaff staff) {
        return 0;
    }

    @Override
    public SupermarketStaff staffLogin(Long staffPhone, String staffPassword) {
        return null;
    }
}
