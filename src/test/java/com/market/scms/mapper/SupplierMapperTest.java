package com.market.scms.mapper;

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
public class SupplierMapperTest {
    @Resource
    private SupplierMapper supplierMapper;
    
    @Test
    public void insertTest() {
        Supplier supplier = new Supplier();
        supplier.setSupplierName("娃哈哈");
        supplier.setSupplierAddress("中国");
        supplier.setSupplierPhone("1");
        supplier.setSupplierPassword("1");
        int res = supplierMapper.insertSupplier(supplier);
        System.out.println(res);
    } 
    
    @Test
    public void queryTest() {
        System.out.println(supplierMapper.querySupplierByPhone("1").getSupplierName());
        System.out.println(supplierMapper.supplierLogin("1", "1").getSupplierName());
    }
    
    @Test
    public void updateTest() {
        Supplier supplier = supplierMapper.querySupplierByPhone("1");
        supplier.setCreateTime(new Date());
        supplier.setToken("1");
        System.out.println(supplierMapper.updateSupplier(supplier));
        System.out.println(supplierMapper.findByToken("1").getSupplierName());
    }
    
    @Test
    public void queryByCondition() {
        Supplier supplier = new Supplier();
        supplier.setSupplierName("哈");
        List<Supplier> list = supplierMapper.querySupplierByCondition(supplier, 0, 100);
        for (Supplier s : list) {
            System.out.println(s.getSupplierName());
        }
    }
    
    
}
