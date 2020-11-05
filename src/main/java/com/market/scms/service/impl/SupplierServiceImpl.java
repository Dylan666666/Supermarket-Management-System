package com.market.scms.service.impl;

import com.market.scms.mapper.SupplierMapper;
import com.market.scms.entity.Supplier;
import com.market.scms.enums.SupplierStatusStateEnum;
import com.market.scms.exceptions.SupplierException;
import com.market.scms.service.SupplierService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 20:31
 */
@Service
public class SupplierServiceImpl implements SupplierService {
    
    @Resource
    private SupplierMapper supplierMapper;
    
    @Override
    public Supplier queryStaffByPhone(String supplierPhone) throws SupplierException {
        if (supplierPhone != null) {
            try {
                Supplier supplier = supplierMapper.querySupplierByPhone(supplierPhone);
                return supplier;
            } catch (SupplierException e) {
                throw new SupplierException("传入信息为空");
            }
        } else {
            throw new SupplierException("传入信息为空");
        }
    }

    @Override
    public int insertSupplier(Supplier supplier) throws SupplierException {
        if (supplier != null && supplier.getSupplierName() != null && supplier.getSupplierPassword() != null &&
        supplier.getSupplierPhone() != null && supplier.getSupplierAddress() != null) {
            try {
                supplier.setLastEditTime(new Date());
                supplier.setCreateTime(new Date());
                supplier.setSupplierStatus(SupplierStatusStateEnum.GOOD.getState());
                int res = supplierMapper.insertSupplier(supplier);
                if (res == 0) {
                    throw new SupplierException("注册失败");
                }
                return res;
            } catch (SupplierException e) {
                throw new SupplierException("注册失败");
            }
        } else {
            throw new SupplierException("传入信息为空");
        }
    }

    @Override
    public int updateSupplier(Supplier supplier) throws SupplierException {
        if (supplier != null && supplier.getSupplierId() != null) {
            try {
                supplier.setLastEditTime(new Date());;
                int res = supplierMapper.updateSupplier(supplier);
                if (res == 0) {
                    throw new SupplierException("更改失败");
                }
                return res;
            } catch (SupplierException e) {
                throw new SupplierException("更改失败");
            }
        } else {
            throw new SupplierException("必要信息为空");
        }
    }
    
    @Override
    public Supplier supplierLogin(String supplierPhone, String supplierPassword) throws SupplierException {
        if (supplierPhone != null && supplierPassword != null) {
            try {
                Supplier supplier = supplierMapper.supplierLogin(supplierPhone, supplierPassword);
                return supplier;
            } catch (SupplierException e ){
                throw new SupplierException("登录失败");
            }
        } else {
            throw new SupplierException("手机号或密码为空");
        }
    }

    @Override
    public Supplier findByToken(String token) throws SupplierException {
        if (token != null) {
            try { 
                Supplier supplier = supplierMapper.findByToken(token);
                return supplier;
            } catch (SupplierException e) {
                throw new SupplierException("查询失败");
            }
        } else {
            throw new SupplierException("token为空");
        }
    }

    @Override
    public List<Supplier> querySupplierByCondition(Supplier supplierCondition, int pageIndex, int pageSize) 
            throws SupplierException {
        if (supplierCondition != null && pageIndex != -1000 && pageSize != -1000) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<Supplier> list = supplierMapper.querySupplierByCondition(supplierCondition, rowIndex, pageSize);
                return list;
            } catch (SupplierException e) {
                throw new SupplierException("查询失败");
            }
        } else {
            throw new SupplierException("必要信息为空");
        }
    }

    @Override
    public void supplierLogout(String supplierToken) throws SupplierException {
        try {
            Supplier supplier = supplierMapper.findByToken(supplierToken);
            supplier.setToken(UUID.randomUUID().toString());
            supplierMapper.updateSupplier(supplier);
        } catch (SupplierException e) {
            throw new SupplierException("消除token出错");
        }
    }
}
