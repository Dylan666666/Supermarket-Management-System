package com.market.scms.service;

import com.market.scms.entity.SupermarketStaff;
import com.market.scms.entity.Supplier;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/10 18:09
 */
public interface SupplierService {

    /**
     * 通过电话查找供应商
     * 
     * @param supplierPhone
     * @return
     */
    Supplier querySupplierByPhone(String supplierPhone);

    /**
     * 查询所有供应商信息
     *
     * @return
     */
    List<Supplier> querySupplierList();

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
    Supplier supplierLogin(String supplierPhone, String supplierPassword);

    /**
     * 生成 token
     *
     * @param staff
     * @return
     */
    String createToken(Supplier staff);

    /**
     * 根据token去修改用户token ，使原本token失效
     *
     * @param token
     */
    void logout(String token);

    /**
     * 根据token获取供应商信息
     *
     * @param token
     * @return
     */
    Supplier findByToken(String token);
}
