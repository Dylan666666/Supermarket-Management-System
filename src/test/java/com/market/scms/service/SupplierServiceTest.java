package com.market.scms.service;

import com.market.scms.entity.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/3 9:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierServiceTest {
    
    @Resource
    private SupplierService supplierService;

    //当前时间
    LocalDateTime now = LocalDateTime.now();

    @Test
    public void InsertTest() {
        Supplier supplier = new Supplier();
        supplier.setSupplierName("可口可乐有限公司");
        supplier.setSupplierPhone("111");
        supplier.setSupplierPassword("111");
        supplier.setSupplierAddress("美国");
        System.out.println(supplierService.insertSupplier(supplier));
    }

    @Test
    public void queryTest() {
        System.out.println(supplierService.queryStaffByPhone("111").getSupplierName());
        System.out.println(supplierService.supplierLogin("111", "111").getSupplierName());
    }

    @Test
    public void updateTest() {
        Supplier supplier = supplierService.queryStaffByPhone("111");
        supplier.setLastEditTime(new Date());
        System.out.println(supplierService.updateSupplier(supplier));
    }

    @Test
    public void queryByCondition() {
        Supplier supplier = new Supplier();
        supplier.setSupplierName("口");
        System.out.println(supplierService.querySupplierByCondition(supplier, 0, 100)
                .get(0).getSupplierName());
    }
    
}
