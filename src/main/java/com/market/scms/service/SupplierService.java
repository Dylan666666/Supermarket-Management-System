package com.market.scms.service;

import com.market.scms.entity.Supplier;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 20:27
 */
public interface SupplierService {
    /**
     * 通过电话号码查询供应商信息
     *
     * @param supplierPhone
     * @return
     */
    Supplier queryStaffByPhone(String supplierPhone);

    /**
     * 注册供应商信息
     *
     * @param supplier
     * @return
     */
    int insertSupplier(Supplier supplier);

    /**
     * 更新供应商信息
     *
     * @param supplier
     * @return
     */
    int updateSupplier(Supplier supplier);

    /**
     * 供应商登录
     * 
     * @param supplierPhone
     * @param supplierPassword
     * @return
     */
    Supplier supplierLogin(String supplierPhone,String supplierPassword);

    /**
     * 通过token查找供应商
     *
     * @param token
     * @return
     */
    Supplier findByToken(String token);

    /**
     * 供应商信息模糊查询
     *
     * @param supplierCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Supplier> querySupplierByCondition(Supplier supplierCondition,
                                                 int pageIndex,
                                                 int pageSize);

    /**
     * 供应商退出登录
     *
     * @param supplierToken
     */
    void supplierLogout(String supplierToken);
}
