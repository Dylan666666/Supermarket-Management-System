package com.market.scms.mapper;

import com.market.scms.entity.log.LoggingEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/16 14:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggingEventMapperTest {
    
    @Resource
    private LoggingEventMapper loggingEventMapper;
    
    @Test
    public void delete() {
        System.out.println(loggingEventMapper.delete(222L));
    }

    @Test
    public void query() {
        System.out.println("============================");
        System.out.println(loggingEventMapper.queryAll(0 ,1).get(0).getFormattedMessage());
        System.out.println("============================");
        System.out.println(loggingEventMapper.queryByCondition(new LoggingEvent(),0 ,1)
                .get(0).getFormattedMessage());
    }
    
}
