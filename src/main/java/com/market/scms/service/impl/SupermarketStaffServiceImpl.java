package com.market.scms.service.impl;

import com.market.scms.dao.SupermarketStaffDao;
import com.market.scms.dto.SupermarketStaffExecution;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.SupermarketStaffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 13:44
 */
@Service
public class SupermarketStaffServiceImpl implements SupermarketStaffService {

    /**
     * 12小时后失效
     * 
     */
    private final static int EXPIRE = 24;
    
    @Resource
    private SupermarketStaffDao supermarketStaffDao;
    
    @Resource
    private SupermarketStaffService supermarketStaffService;

    /**
     * 通过电话查询职工
     * 
     * @param staffPhone
     * @return
     * @throws SupermarketStaffException
     */
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

    /**
     * 查询所有职工
     * 
     * @return
     * @throws SupermarketStaffException
     */
    @Override
    public List<SupermarketStaff> queryStaffList() throws SupermarketStaffException {
        List<SupermarketStaff> list = new ArrayList<>();
        try {
            list = supermarketStaffDao.queryStaffList();
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("查询出错");
        }
        return list;
    }

    /**
     * 添加新职工信息
     * 
     * @param staff
     * @return
     */
    @Override
    @Transactional
    public int insertStaff(SupermarketStaff staff) throws SupermarketStaffException {
        int res = 0;
        if (staff != null && staff.getStaffPhone() != 0 &&
                staff.getStaffName() != null &&
                staff.getStaffPassword() != null) {
            try {
                staff.setCreateTime(new Date());
                staff.setLastEditTime(new Date());
                res = supermarketStaffDao.insertStaff(staff);
                if (res <= 0) {
                    throw new SupermarketStaffException("添加职工信息出错");
                }
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("添加职工信息出错");
            }
            return res;
        } else {
            throw new SupermarketStaffException("添加信息有错");
        }
    }

    /**
     * 更新职工信息
     * 
     * @param staff
     * @return
     */
    @Override
    @Transactional
    public int updateStaff(SupermarketStaff staff) throws SupermarketStaffException {
        if (staff == null || staff.getStaffId() == null || staff.getStaffPhone() == null) {
            throw new SupermarketStaffException("信息有错或不完整");
        } else {
            int res = 0;
            try {
                staff.setLastEditTime(new Date());
                res = supermarketStaffDao.updateStaff(staff);
                if (res <= 0) {
                    throw new SupermarketStaffException("添加信息出错");
                }
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("添加信息出错");
            }
            return res;
        }
    }

    /**
     * 职工登录
     * 
     * @param staffPhone
     * @param staffPassword
     * @return
     */
    @Override
    public SupermarketStaff staffLogin(Long staffPhone, String staffPassword) throws SupermarketStaffException {
        if (staffPhone <= 0 || staffPassword == null) {
            throw new SupermarketStaffException("登录出错");
        } else{
            SupermarketStaff staff = null;
            try {
                staff = supermarketStaffDao.staffLogin(staffPhone, staffPassword);
                if (staff == null) {
                    return null;
                } else {
                    createToken(staff);
                }
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("登录出错");
            }
            return staff;
        }
    }

    /**
     * 登录并生成token
     * 
     * @param staff
     * @return
     * @throws SupermarketStaffException
     */
    @Override
    public String createToken(SupermarketStaff staff) throws SupermarketStaffException {
        //用UUID生产token
        String token = UUID.randomUUID().toString();
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(EXPIRE);
        //保存到数据库
        staff.setLoginTime(now);
        staff.setExpireTime(expireTime);
        staff.setToken(token);
        try {
            supermarketStaffDao.updateStaff(staff);
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("生成token出错，登录失败");
        }
        return token;
    }

    /**
     * 
     * 账号退出，消除token
     * @param token
     */
    @Override
    public void logout(String token) {
        SupermarketStaff staff = supermarketStaffDao.findByToken(token);
        //使用UUID生成token
        token = UUID.randomUUID().toString();
        //替代原来的token，使其失效
        staff.setToken(token);
        supermarketStaffDao.updateStaff(staff);
    }

    /**
     * 通过token查找职工
     * 
     * @param token
     * @return
     */
    @Override
    public SupermarketStaff findByToken(String token) throws SupermarketStaffException {
        SupermarketStaff staff = null;
        if (token != null) {
            try {
                staff = supermarketStaffDao.findByToken(token);
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("查询出错");
            }
        } 
        return staff;
    }
}
