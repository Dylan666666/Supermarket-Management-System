package com.market.scms.web.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 19:25
 */
@CrossOrigin
@Controller
public class HelloHtml {
    
    @GetMapping("/index")
    public String welCome() {
        return "index";
    }
    
    
}
