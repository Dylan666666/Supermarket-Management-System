package com.market.scms.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.SupermarketStaffService;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
                SupermarketStaff curStaff = supermarketStaffService.queryStaffByPhone(staffPhone);
                if (curStaff != null) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "该手机号已注册");
                    return modelMap;
                }
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
            modelMap.put("errMsg", "请务必正确输入姓名，密码以及电话");
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
                modelMap.put("success", true);
                modelMap.put("staff", staff);
                modelMap.put("staffToken", staff.getToken());
            } catch (SupermarketStaffException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请务必正确输入电话和密码");
        }
        return modelMap;
    }
    
    @PostMapping("/update")
    public Map<String, Object> updateStaff(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String staffStr = HttpServletRequestUtil.getString(request, "staff");
        ObjectMapper mapper = new ObjectMapper();
        SupermarketStaff staff = null;
        try {
            staff = mapper.readValue(staffStr, SupermarketStaff.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输失败" + e.getMessage());
            return modelMap;
        }
        if (staff != null && staff.getStaffId() > 0 && staff.getStaffPhone() != null) {
            try {
                int res = supermarketStaffService.updateStaff(staff);
                if (res == 0) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "更改失败");
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
    public void logout(HttpServletRequest request) {
        String token = HttpServletRequestUtil.getString(request, "staffToken");
        // 将用户session置为空
        supermarketStaffService.logout(token);
    }
    
    @PostMapping("/changePassword")
    public Map<String, Object> changePassword(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String staffPhone = HttpServletRequestUtil.getString(request, "staffPhone");
        String staffPassword = HttpServletRequestUtil.getString(request, "staffPassword");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        if (staffPhone != null && staffPassword != null && newPassword != null) {
            try {
                SupermarketStaff staff = supermarketStaffService.queryStaffByPhone(staffPhone);
                if (staff == null) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "该手机号不存在");
                    return modelMap;
                }
                if (!staff.getStaffPassword().equals(staffPassword)) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "密码错误");
                    return modelMap;
                }
                staff.setStaffPassword(newPassword);
                int res = supermarketStaffService.updateStaff(staff);
                if (res != 1) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "更改密码失败");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (SupermarketStaffException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入信息不能为空");
        }
        return modelMap;
    }
    
    @GetMapping("/query")
    public Map<String, Object> queryStaff(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String staffStr = HttpServletRequestUtil.getString(request, "staff");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        ObjectMapper mapper = new ObjectMapper();
        SupermarketStaff staff = null;
        try {
            staff = mapper.readValue(staffStr, SupermarketStaff.class);
            if (staff == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "数据传输出错");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输出错" + e.getMessage());
            return modelMap;
        }
        try {
            if (pageIndex == -1000) {
                pageIndex = 0;
            }
            if (pageSize == -1000) {
                pageSize = 100;
            }
            List<SupermarketStaff> list = supermarketStaffService.queryStaffByCondition(staff, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("staffList", list);
            modelMap.put("staffCount", list.size());
        } catch (SupermarketStaffException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }
    
}
