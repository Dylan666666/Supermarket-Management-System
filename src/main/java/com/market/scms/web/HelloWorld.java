package com.market.scms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Mr_OO
 * @Date: 2020/9/23 12:55
 */
@Controller
public class HelloWorld {
    
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }
}
