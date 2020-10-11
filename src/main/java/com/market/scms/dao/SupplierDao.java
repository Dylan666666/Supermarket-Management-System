package com.market.scms.dao;

import com.market.scms.entity.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/9 14:37
 */
public interface SupplierDao {
    /**
     * 通过电话号码查询供应商信息
     * 
     * @param supplierPhone
     * @return
     */
    Supplier querySupplierByPhone(@Param("supplierPhone") String supplierPhone);

    /**
     * 查询所有供应商信息
     * 
     * @param supplierCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Supplier> querySupplierByCondition(@Param("supplierCondition") Supplier supplierCondition,
                                            @Param("rowIndex") int rowIndex, 
                                            @Param("pageSize") int pageSize);

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
    Supplier supplierLogin(@Param("supplierPhone") String supplierPhone,
                           @Param("supplierPassword") String supplierPassword);

    /**
     * 通过token查找职工
     *
     * @param token
     * @return
     */
    Supplier findByToken(String token);
}
