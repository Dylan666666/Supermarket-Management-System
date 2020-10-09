package com.market.scms.web.staff;

import com.market.scms.entity.SupermarketStaff;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.SupermarketStaffService;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 19:34
 */
@RestController
@RequestMapping("/staff")
@CrossOrigin
public class SupermarketStaffController {
    
    @Resource
    private SupermarketStaffService supermarketStaffService;
    
    @PostMapping("/insert")
    public Map<String, Object> insertStaff(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //TODO 验证码验证未判断
        String staffName = HttpServletRequestUtil.getString(request, "staffName");
        String staffPassword = HttpServletRequestUtil.getString(request, "staffPassword");
        String staffPhone = HttpServletRequestUtil.getString(request, "staffPhone");
        SupermarketStaff staff = new SupermarketStaff();
        staff.setStaffName(staffName);
        staff.setStaffPassword(staffPassword);
        staff.setStaffPhone(staffPhone);
        //非空判断
        if (staffName != null && staffPassword != null && staffPhone != null) {
            try {
                int res = supermarketStaffService.insertStaff(staff);
                if (res == 1) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "添加失败");
                }
            } catch (SupermarketStaffException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请务必输入姓名，密码以及电话");
        }
        return modelMap;
    }
    
    
}
