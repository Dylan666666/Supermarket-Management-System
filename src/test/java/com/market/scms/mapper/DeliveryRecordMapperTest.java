package com.market.scms.mapper;

import com.market.scms.entity.DeliveryRecord;
import com.market.scms.util.DeliveryIdCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/24 10:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryRecordMapperTest {
    
    @Resource
    private DeliveryRecordMapper deliveryRecordMapper;
    
    @Test
    public void insert() {
        DeliveryRecord deliveryRecord = new DeliveryRecord();
        deliveryRecord.setDeliveryId(DeliveryIdCreator.get(1234567890123L));
        deliveryRecord.setDeliveryPaid(0D);
        deliveryRecord.setDeliveryStatus(0);
        deliveryRecord.setDeliveryLaunchedStaffId(25L);
        deliveryRecord.setDeliveryCheckOutStatus(0);
        deliveryRecord.setDeliveryRefundStatus(0);
        deliveryRecord.setDeliveryCreateDate(new Date());
        System.out.println(deliveryRecordMapper.insert(deliveryRecord));
    }
    
    @Test
    public void query() {
        System.out.println(deliveryRecordMapper.queryByDeliveryId("1234567890123201124101043").getDeliveryPaid());
        System.out.println(deliveryRecordMapper.queryAll(0, 100).get(0).getDeliveryPaid());
        DeliveryRecord deliveryRecord = new DeliveryRecord();
        deliveryRecord.setDeliveryId("1234567890123201124101043");
        System.out.println(deliveryRecordMapper.queryByCondition(deliveryRecord, 0, 10).get(0).getDeliveryPaid());
    }
    
    @Test
    public void update() {
        DeliveryRecord deliveryRecord = deliveryRecordMapper.queryByDeliveryId("1234567890123201124101043");
        deliveryRecord.setDeliveryLaunchedStaffId(26L);
        System.out.println(deliveryRecordMapper.update(deliveryRecord));
    }
    
}
