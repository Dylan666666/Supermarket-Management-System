package com.market.scms.mapper;

import com.market.scms.entity.staff.Function;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 9:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionMapperTest {

    @Resource
    private FunctionMapper functionMapper;
    
    @Test
    public void insert() {
        Function function = new Function();
        function.setFunctionName("查看");
        function.setFunctionUrl("/staff/query");
        function.setFunctionWeight(10);
        function.setSecondaryMenuId(1);
        System.out.println(functionMapper.insert(function));
    }
    
    @Test
    public void query() {
        System.out.println(functionMapper.queryById(1).getFunctionName());
    }
    
    @Test
    public void update() {
        Function function = new Function();
        function.setFunctionName("查看");
        function.setFunctionUrl("/staff/query");
        function.setFunctionWeight(1);
        function.setSecondaryMenuId(1);
        function.setFunctionId(1);
        System.out.println(functionMapper.update(function));
    }
    
    @Test
    public void delete() {
        System.out.println(functionMapper.delete(1));
    }
    
}
