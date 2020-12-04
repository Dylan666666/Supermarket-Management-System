package com.market.scms.service;

import com.market.scms.entity.ExportBill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/22 19:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExportBillServiceTest {
    
    @Resource
    private ExportBillService exportBillService;
    
    @Resource
    private CacheService cacheService;
    
    @Test
    public void insert() {
        ExportBill exportBill = new ExportBill();
        System.out.println(exportBillService.insert(exportBill, 1234567890123L));
    }

    @Test
    public void query() {
        cacheService.removeFromCache(ExportBillService.EXPORT_BILL_KEY);
        System.out.println(exportBillService.queryAll(0, 100).get(1));
        cacheService.removeFromCache(ExportBillService.EXPORT_BILL_KEY);
    }
    
}
