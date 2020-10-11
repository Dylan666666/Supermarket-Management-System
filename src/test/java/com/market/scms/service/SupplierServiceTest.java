package com.market.scms.service;

import com.market.scms.entity.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/11 16:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierServiceTest {
    //当前时间
    LocalDateTime now = LocalDateTime.now();
    
    @Resource
    private SupplierService supplierService;
    
    @Test
    public void insertTest() {
        Supplier supplier = new Supplier();
        supplier.setSupplierName("腾讯食品");
        supplier.setSupplierPhone("2");
        supplier.setSupplierPassword("2");
        supplier.setSupplierAddress("天府五街");
        int res = supplierService.insertSupplier(supplier);
        System.out.println(res);
    }

    @Test
    public void queryTest() {
        System.out.println(supplierService.querySupplierByPhone("2").getSupplierName());
        System.out.println(supplierService.supplierLogin("2", "2").getSupplierName());
    }

    @Test
    public void updateTest() {
        Supplier supplier = supplierService.querySupplierByPhone("2");
        supplier.setToken("666");
        System.out.println(supplierService.updateSupplier(supplier));
    }

    @Test
    public void findTokenTest() {
        System.out.println(supplierService.findByToken("666").getSupplierName());
    }

    @Test
    public void logoutTest() { 
        supplierService.logout("666");
    }

    @Test
    public void queryByCondition() {
        Supplier supplier = new Supplier();
        List<Supplier> list = supplierService.querySupplierByCondition(supplier, 0, 100);
        for (Supplier cur : list) {
            System.out.println(cur.getSupplierName());
        }
    }
    
}
