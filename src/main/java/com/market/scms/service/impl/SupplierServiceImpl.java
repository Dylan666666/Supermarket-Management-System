package com.market.scms.service.impl;

import com.market.scms.dao.SupplierDao;
import com.market.scms.entity.Supplier;
import com.market.scms.enums.SupplierPaymentStateEnum;
import com.market.scms.enums.SupplierStatusStateEnum;
import com.market.scms.enums.TokenTimeEnum;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.exceptions.SupplierException;
import com.market.scms.service.SupplierService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/11 10:48
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    /**
     * 24小时后失效
     *
     */
    private final static int EXPIRE = 24;
    
    @Resource
    private SupplierDao supplierDao;

    /**
     * 通过电话号码查询供应商信息
     * 
     * @param supplierPhone
     * @return
     * @throws SupplierException
     */
    @Override
    public Supplier querySupplierByPhone(String supplierPhone) throws SupplierException {
        Supplier supplier = null;
        if (supplierPhone != null) {
            try {
                supplier = supplierDao.querySupplierByPhone(supplierPhone);
            } catch (SupplierException e) {
                throw new SupplierException("查询出错");
            }
        } else {
            throw new SupplierException("电话号码不能为空");
        }
        return supplier;
    }

    /**
     * 模糊查询供应商信息
     * 
     * @param supplier
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws SupplierException
     */
    @Override
    public List<Supplier> querySupplierByCondition(Supplier supplier, int pageIndex, int pageSize) 
            throws SupplierException {
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Supplier> list = null;
        if (supplier != null) {
            list = supplierDao.querySupplierByCondition(supplier, rowIndex, pageSize);
        } else {
            throw new SupplierException("传入信息出错");
        }
        return list;
    }

    /**
     * 添加供应商信息
     * 
     * @param supplier
     * @return
     * @throws SupplierException
     */
    @Override
    public int insertSupplier(Supplier supplier) throws SupplierException {
        int res = 0;
        if (supplier != null && supplier.getSupplierName() != null && supplier.getSupplierPassword() != null
            && supplier.getSupplierPhone() != null && supplier.getSupplierAddress() != null) {
            try {
                supplier.setCreateTime(new Date());
                supplier.setLastEditTime(new Date());
                supplier.setSupplierStatus(SupplierStatusStateEnum.GOOD.getState());
                res = supplierDao.insertSupplier(supplier);
            } catch (SupplierException e) {
                throw new SupplierException("注册失败");
            }
        } else {
            throw new SupplierException("缺少必要信息，无法注册");
        }
        return res;
    }

    /**
     * 更改供应商信息
     * 
     * @param supplier
     * @return
     * @throws SupplierException
     */
    @Override
    public int updateSupplier(Supplier supplier) throws SupplierException {
        int res = 0;
        if (supplier != null && supplier.getSupplierId() != null) {
            supplier.setLastEditTime(new Date());
            res = supplierDao.updateSupplier(supplier);
            if (res <= 0) {
                throw new SupplierException("更改信息失败");
            }
        } else {
            throw new SupplierException("信息传入失败，无法更改");
        }
        return res;
    }

    /**
     * 供应商登录
     * 
     * @param supplierPhone
     * @param supplierPassword
     * @return
     * @throws SupplierException
     */
    @Override
    public Supplier supplierLogin(String supplierPhone, String supplierPassword) throws SupplierException {
        Supplier supplier = null;
        if (supplierPhone != null && supplierPassword != null) {
            try {
                supplier = supplierDao.supplierLogin(supplierPhone, supplierPassword);
                if (supplier == null) {
                    throw new SupplierException("账号或密码错误");
                } else {
                    createToken(supplier);
                }
            } catch (SupplierException e) {
                throw new SupplierException("登录失败");
            }
        } else {
            throw new SupplierException("请输入完整的账号和密码");
        }
        return supplier;
    }

    /**
     * 登陆时创建 token
     * 
     * @param supplier
     * @return
     * @throws SupplierException
     */
    @Override
    public String createToken(Supplier supplier) throws SupplierException {
        //用UUID生产token
        String token = UUID.randomUUID().toString();
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(TokenTimeEnum.EXPIRE_TIME.getState());
        //保存到数据库
        supplier.setLoginTime(now);
        supplier.setExpireTime(expireTime);
        supplier.setToken(token);
        try {
            supplierDao.updateSupplier(supplier);
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("生成token出错，登录失败");
        }
        return token;
    }

    /**
     * 退出账号并消除原token
     * 
     * @param token
     * @throws SupplierException
     */
    @Override
    public void logout(String token) throws SupplierException {
        Supplier supplier = supplierDao.findByToken(token);
        //使用UUID生成token
        token = UUID.randomUUID().toString();
        //替代原来的token，使其失效
        supplier.setToken(token);
        supplierDao.updateSupplier(supplier);
    }

    /**
     * 通过token获取供应商信息
     * 
     * @param token
     * @return
     * @throws SupplierException
     */
    @Override
    public Supplier findByToken(String token) throws SupplierException {
        Supplier supplier = null;
        if (token != null) {
            supplier = supplierDao.findByToken(token);
        } else {
            throw new SupplierException("token为空");
        }
        return supplier;
    }
}
