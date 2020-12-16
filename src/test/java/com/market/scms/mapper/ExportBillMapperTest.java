package com.market.scms.mapper;

import com.market.scms.entity.ExportBill;
import com.market.scms.enums.ExportBillStatusStateEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 19:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExportBillMapperTest {
    
    @Resource
    private ExportBillMapper exportBillMapper;
    
    @Test
    public void insert() {
        ExportBill exportBill = new ExportBill();
        exportBill.setExportBillId("1");
        exportBill.setExportBillCouponId(1L);
        exportBill.setExportBillSupplierId(1L);
        exportBill.setExportBillGoodsBatchNumber(1L);
        exportBill.setExportBillProductionDate(new Date());
        exportBill.setExportBillShelfLife(180);
        exportBill.setExportBillPrice(1.2);
        exportBill.setExportBillStatus(ExportBillStatusStateEnum.START.getState());
        exportBill.setExportBillPaid(1000D);
        System.out.println(exportBillMapper.insert(exportBill));
    }
    
    @Test
    public void query() {
        System.out.println(exportBillMapper.queryByBillId("1").getExportBillProductionDate());
        System.out.println(exportBillMapper.queryByBillStatus(3).get(0).getExportBillProductionDate());
        System.out.println(exportBillMapper.queryByCouponId(1L).getExportBillProductionDate());
        System.out.println(exportBillMapper.queryAll(0, 100).get(0).getExportBillProductionDate());
    }

    @Test
    public void query2() {
        System.out.println(exportBillMapper
                .queryByCondition(new ExportBill(), 0, 100).get(0).getExportBillId());
    }
    
    @Test
    public void update() {
        ExportBill exportBill = exportBillMapper.queryByBillId("1");
        exportBill.setExportBillStatus(ExportBillStatusStateEnum.TO_STOCK.getState());
        exportBill.setExportConfirmStaffId(25);
        exportBill.setExportSubmitStaffId(25);
        exportBill.setExportBillTime(new Date());
        System.out.println(exportBillMapper.update(exportBill));
    }
    
}
