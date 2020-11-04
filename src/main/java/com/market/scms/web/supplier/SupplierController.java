package com.market.scms.web.supplier;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.Supplier;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.exceptions.SupplierException;
import com.market.scms.service.SupplierService;
import com.market.scms.util.CreateToken;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 20:58
 */
@CrossOrigin
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    
    @Resource
    private SupplierService supplierService;

    @PostMapping("/insert")
    @ResponseBody
    public Map<String,Object> insertSupplier(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String supplierName = HttpServletRequestUtil.getString(request, "supplierName");
        String supplierPassword = HttpServletRequestUtil.getString(request, "supplierName");
        String supplierPhone = HttpServletRequestUtil.getString(request, "supplierName");
        String supplierAddress = HttpServletRequestUtil.getString(request, "supplierName");
        if (supplierName != null && supplierPassword != null && supplierPhone != null && supplierAddress != null) {
            try {
                Supplier supplier = new Supplier();
                supplier.setSupplierName(supplierName);
                supplier.setSupplierPassword(supplierPassword);
                supplier.setSupplierPhone(supplierPhone);
                supplier.setSupplierAddress(supplierAddress);
                int res = supplierService.insertSupplier(supplier);
                if (res == 0) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "注册失败");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (SupplierException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "注册失败," + e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "必要信息不完整");
            return modelMap;
        }
        return modelMap;
    }


    @PostMapping("/login")
    @ResponseBody
    @Transactional
    public Map<String,Object> supplierLogin(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String supplierPassword = HttpServletRequestUtil.getString(request, "supplierName");
        String supplierPhone = HttpServletRequestUtil.getString(request, "supplierName");
        if (supplierPassword != null && supplierPhone != null) {
            try {
                Supplier supplier = supplierService.supplierLogin(supplierPhone, supplierPassword);
                if (supplier == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "手机号或密码错误");
                    return modelMap;
                }
                CreateToken.supplierCreateToken(supplier);
                int res = supplierService.updateSupplier(supplier);
                if (res == 0) {
                    throw new SupplierException("登陆失败");
                }
                HttpSession session = request.getSession();
                session.setAttribute("token", supplier.getToken());
                modelMap.put("supplier", supplier);
                modelMap.put("supplierToken", supplier.getToken());
                modelMap.put("success", true);
            } catch (SupplierException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "登录失败");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "手机号或者密码为空");
            return modelMap;
        }
        return modelMap;
    }

    @PostMapping("/update")
    @ResponseBody
    @Transactional
    public Map<String,Object> supplierUpdate(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String supplierStr = HttpServletRequestUtil.getString(request, "supplier");
        ObjectMapper mapper = new ObjectMapper();
        Supplier supplier = null;
        try {
            supplier = mapper.readValue(supplierStr, Supplier.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息更改失败-01");
            return modelMap;
        }
        try {
            int res = supplierService.updateSupplier(supplier);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "信息更改失败");
                return modelMap;
            }
            modelMap.put("success", true);
        } catch (SupplierException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息更改失败");
            return modelMap;
        }
        return modelMap;
    }
    
    @PostMapping("/logout")
    @ResponseBody
    public void supplierLogout(HttpServletRequest request) {
        String token = HttpServletRequestUtil.getString(request, "supplierToken");
        if (token != null) {
            try {
                supplierService.supplierLogout(token);
            } catch (SupermarketStaffException e) {}
        }
    }

    @PostMapping("/changePassword")
    @ResponseBody
    @Transactional
    public Map<String,Object> supplierChangePassword(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String supplierPhone = HttpServletRequestUtil.getString(request, "supplierPhone");
        String supplierPassword = HttpServletRequestUtil.getString(request, "supplierPassword");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        if (supplierPassword != null && supplierPhone != null && newPassword != null) {
            try {
                Supplier supplier = supplierService.queryStaffByPhone(supplierPhone);
                if (supplier != null) {
                    supplier.setSupplierPassword(newPassword);
                    int res = supplierService.updateSupplier(supplier);
                    if (res == 0) {
                        modelMap.put("success",false);
                        modelMap.put("errMsg", "更改密码失败");
                        return modelMap;
                    }
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "手机号或密码错误");
                    return modelMap;
                }
            } catch (SupplierException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "更改密码失败");
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
        Map<String, Object> modelMap = new HashMap<>(16);
        String supplierStr = HttpServletRequestUtil.getString(request, "supplier");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 100;
        }
        ObjectMapper mapper = new ObjectMapper();
        Supplier supplier = null;
        try {
            supplier = mapper.readValue(supplierStr, Supplier.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息查询失败-01");
            return modelMap;
        }
        if (supplier != null) {
            try {
                List<Supplier> list = supplierService.querySupplierByCondition(supplier, pageIndex, pageSize);
                modelMap.put("supplierList", list);
                modelMap.put("supplierCount", list.size());
                modelMap.put("success", true);
            } catch (SupplierException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", e.getMessage());
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
