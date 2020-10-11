package com.market.scms.dao;

import com.market.scms.entity.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/9 15:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierDaoTest {
    @Resource
    private SupplierDao supplierDao;
    
    @Test
    public void insertTest() {
        Supplier supplier = new Supplier();
        supplier.setSupplierName("娃哈哈");
        supplier.setSupplierAddress("中国");
        supplier.setSupplierPhone("1");
        supplier.setSupplierPassword("1");
        int res = supplierDao.insertSupplier(supplier);
        System.out.println(res);
    } 
    
    @Test
    public void queryTest() {
        System.out.println(supplierDao.querySupplierByPhone("1").getSupplierName());
        System.out.println(supplierDao.supplierLogin("1", "1").getSupplierName());
    }
    
    @Test
    public void updateTest() {
        Supplier supplier = supplierDao.querySupplierByPhone("1");
        supplier.setCreateTime(new Date());
        supplier.setToken("1");
        System.out.println(supplierDao.updateSupplier(supplier));
        System.out.println(supplierDao.findByToken("1").getSupplierName());
    }
    
    @Test
    public void queryByCondition() {
        Supplier supplier = new Supplier();
        supplier.setSupplierName("哈");
        List<Supplier> list = supplierDao.querySupplierByCondition(supplier, 0, 100);
        for (Supplier s : list) {
            System.out.println(s.getSupplierName());
        }
    }
    
    
}
