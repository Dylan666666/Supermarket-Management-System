package com.market.scms.mapper;

import com.market.scms.entity.Delivery;
import com.market.scms.util.DeliveryIdCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/23 21:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryMapperTest {
    
    @Resource
    private DeliveryMapper deliveryMapper;
    
    @Test
    public void insert() {
        Delivery delivery = new Delivery();
        delivery.setDeliveryStockGoodsId(1234567890123L);
        delivery.setDeliveryId(DeliveryIdCreator.get(1234567890123L));
        delivery.setDeliveryNum(100);
        delivery.setDeliveryPrice(1.8);
        System.out.println(deliveryMapper.insert(delivery));
    }
    
    @Test
    public void query() {
        System.out.println(deliveryMapper.queryAll(0, 10).get(0).getDeliveryPrice());
        System.out.println(deliveryMapper.queryByDeliveryId("1234567890123201123212757").getDeliveryPrice());
        System.out.println(deliveryMapper.queryByGoodsId(1234567890123L).getDeliveryPrice());
    }
    
    @Test
    public void update() {
        Delivery delivery = deliveryMapper.queryByGoodsId(1234567890123L);
        delivery.setDeliveryPrice(1.79);
        System.out.println(deliveryMapper.update(delivery));
    }
    
}
