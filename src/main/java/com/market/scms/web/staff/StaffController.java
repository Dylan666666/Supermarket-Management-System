package com.market.scms.web.staff;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.StaffService;
import com.market.scms.util.CreateToken;
import com.market.scms.util.EncryptionUtil;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 14:33
 */
@CrossOrigin
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Resource
    private StaffService staffService;
    
    @PostMapping("/insert")
    @ResponseBody
    public Map<String,Object> insertStaff(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String staffStr = HttpServletRequestUtil.getString(request, "staff");
        ObjectMapper mapper = new ObjectMapper();
        SupermarketStaff staff = null;
        try {
            staff = mapper.readValue(staffStr, SupermarketStaff.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "注册出错-01");
            return modelMap;
        }
        try {
            staff.setStaffPassword(EncryptionUtil.getMd5(staff.getStaffPassword()));
            int res = staffService.insertStaff(staff);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "注册失败");
                return modelMap;
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException staffException) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "注册失败," + staffException.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    @PostMapping("/login")
    @ResponseBody
    @Transactional
    public Map<String,Object> staffLogin(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String staffPhone = HttpServletRequestUtil.getString(request, "staffPhone");
        String staffPassword = HttpServletRequestUtil.getString(request, "staffPassword");
        if (staffPhone != null && staffPassword != null) {
            try {
                staffPassword = EncryptionUtil.getMd5(staffPassword);
                SupermarketStaff staff = staffService.staffLogin(staffPhone, staffPassword);
                if (staff == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "账号或密码错误，登录失败");
                    return modelMap;
                }
                CreateToken.staffCreateToken(staff);
                int res = staffService.updateStaff(staff);
                if (res == 1) {
                    HttpSession session = request.getSession();
                    session.setAttribute("token", staff.getToken());
                    modelMap.put("success",true);
                    modelMap.put("staff", staff);
                    modelMap.put("staffToken", staff.getToken());
                } else {
                    throw new SupermarketStaffException("登录失败");
                }
            } catch (SupermarketStaffException staffException) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "登录失败");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "输入信息为空");
            return modelMap;
        }
        return modelMap;
    }

    @PostMapping("/update")
    @ResponseBody
    @Transactional
    public Map<String,Object> staffUpdate(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String staffStr = HttpServletRequestUtil.getString(request, "staff");
        ObjectMapper mapper = new ObjectMapper();
        SupermarketStaff staff = null;
        try {
            staff = mapper.readValue(staffStr, SupermarketStaff.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息更改失败-01");
            return modelMap;
        }
        try {
            int res = staffService.updateStaff(staff);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "信息更改失败");
                return modelMap;
            }
            modelMap.put("success",true);
        } catch (SupermarketStaffException staffException) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息更改失败");
            return modelMap;
        }
        return modelMap;
    }


    @PostMapping("/logout")
    @ResponseBody
    public void staffLogout(HttpServletRequest request) {
        String token = HttpServletRequestUtil.getString(request, "staffToken");
        if (token != null) {
            try {
                staffService.staffLogout(token);
            } catch (SupermarketStaffException e) {}
        }
    }

    @PostMapping("/changePassword")
    @ResponseBody
    @Transactional
    public Map<String,Object> staffChangePassword(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String staffPhone = HttpServletRequestUtil.getString(request, "staffPhone");
        String staffPassword = HttpServletRequestUtil.getString(request, "staffPassword");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        if (staffPassword != null && staffPhone != null && newPassword != null) {
            try {
                SupermarketStaff staff = staffService.queryStaffByPhone(staffPhone);
                if (staff == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "手机号或密码错误");
                    return modelMap;
                }
                staff.setStaffPassword(EncryptionUtil.getMd5(newPassword));
                int res = staffService.updateStaff(staff);
                if (res == 0) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "密码更改失败");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (SupermarketStaffException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "密码更改失败");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息不能为空");
            return modelMap;
        }
        return modelMap;
    }


    @PostMapping("/query")
    @ResponseBody
    public Map<String,Object> staffQuery(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String staffStr = HttpServletRequestUtil.getString(request, "staff");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 100;
        }
        ObjectMapper mapper = new ObjectMapper();
        SupermarketStaff staff = null;
        try {
            staff = mapper.readValue(staffStr, SupermarketStaff.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息查询失败-01");
            return modelMap;
        }
        if (staff != null) {
            try {
                List<SupermarketStaff> list = staffService.queryStaffByCondition(staff, pageIndex, pageSize);
                modelMap.put("success", true);
                modelMap.put("staffList", list);
                modelMap.put("staffCount", list.size());
            } catch (SupermarketStaffException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }
        return modelMap;
    }
    
}
