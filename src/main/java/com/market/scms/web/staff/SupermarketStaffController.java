package com.market.scms.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.SupermarketStaffService;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    
    @PostMapping("/login")
    public Map<String, Object> login(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String staffPassword = HttpServletRequestUtil.getString(request, "staffPassword");
        String staffPhone = HttpServletRequestUtil.getString(request, "staffPhone");
        if (staffPassword != null && staffPhone != null) {
            try {
                SupermarketStaff staff = supermarketStaffService.queryStaffByPhone(staffPhone);
                if (staff == null) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "该手机号不存在");
                    return modelMap;
                }
                staff = supermarketStaffService.staffLogin(staffPhone, staffPassword);
                if (staff == null) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "密码错误");
                    return modelMap;
                }
                modelMap.put("staff", staff);
                modelMap.put("staffToken", staff.getToken());
            } catch (SupermarketStaffException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请务必输入电话和密码");
        }
        return modelMap;
    }
    
    @PostMapping("/update")
    public Map<String, Object> updateStaff(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String staffStr = HttpServletRequestUtil.getString(request, "staff");
        //测试
        System.out.println(staffStr);
        ObjectMapper mapper = new ObjectMapper();
        SupermarketStaff staff = null;
        try {
            staff = mapper.readValue(staffStr, SupermarketStaff.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输出错" + e.getMessage());
            return modelMap;
        }
        if (staff != null && staff.getStaffId() > 0 && staff.getStaffPhone() != null) {
            try {
                int res = supermarketStaffService.updateStaff(staff);
                if (res == 0) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "更改出错");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (SupermarketStaffException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "不具备更新条件");
        }
        return modelMap;
    }
    
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String token = HttpServletRequestUtil.getString(request, "staffToken");
        // 将用户session置为空
        supermarketStaffService.logout(token);
        modelMap.put("success", true);
        return modelMap;
    }
    
}
