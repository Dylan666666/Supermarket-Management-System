package com.market.scms.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.StaffService;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
            int res = staffService.insertStaff(staff);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "注册失败");
                return modelMap;
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException staffException) {
            modelMap.put("success",false);
            modelMap.put("errMsg", staffException.getMessage());
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
                SupermarketStaff staff = staffService.staffLogin(staffPhone, staffPassword);
                modelMap.put("staff", staff);
                modelMap.put("success",true);
            } catch (SupermarketStaffException staffException) {
                modelMap.put("success",false);
                modelMap.put("errMsg", staffException.getMessage());
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
    @RequiresPermissions("/staff/update")
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
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        } catch (Exception e) {}
    }

    @PostMapping("/changePassword")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/staff/changePassword")
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
                    modelMap.put("errMsg", "手机号不存在");
                    return modelMap;
                }
                PasswordHelper helper = new PasswordHelper();
                if (!helper.encryptPassword(staffPhone, staffPassword).equals(staff.getStaffPassword())) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "密码输入错误");
                    return modelMap;
                }
                staff.setStaffPassword(newPassword);
                helper.encryptPassword(staff);
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
    @RequiresPermissions("/staff/query")
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
