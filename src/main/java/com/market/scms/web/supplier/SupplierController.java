package com.market.scms.web.supplier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.Supplier;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.exceptions.SupplierException;
import com.market.scms.service.SupplierService;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/11 14:05
 */
@RestController
@RequestMapping("/supplier")
@CrossOrigin
public class SupplierController {
    
    @Resource
    private SupplierService supplierService;

    @PostMapping("/insert")
    public Map<String, Object> insertSupplier(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //TODO 验证码验证未判断
        String supplierName = HttpServletRequestUtil.getString(request, "supplierName");
        String supplierPassword = HttpServletRequestUtil.getString(request, "supplierPassword");
        String supplierPhone = HttpServletRequestUtil.getString(request, "supplierPhone");
        String supplierAddress = HttpServletRequestUtil.getString(request, "supplierAddress");
        if (supplierName != null && supplierPassword != null && supplierPhone != null && supplierAddress != null) {
            try {
                Supplier curSupplier = supplierService.querySupplierByPhone(supplierPhone);
                if (curSupplier != null) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "该手机号已注册");
                    return modelMap;
                }
                Supplier supplier = new Supplier();
                supplier.setSupplierName(supplierName);
                supplier.setSupplierPassword(supplierPassword);
                supplier.setSupplierPhone(supplierPhone);
                supplier.setSupplierAddress(supplierAddress);
                int res = supplierService.insertSupplier(supplier);
                if (res == 1) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "注册失败");
                }
            } catch (SupplierException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请务必正确输入供应商名称，地址，电话以及密码");
        }
        return modelMap;
    }

    @PostMapping("/login")
    public Map<String, Object> login(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String supplierPhone = HttpServletRequestUtil.getString(request, "supplierPhone");
        String supplierPassword = HttpServletRequestUtil.getString(request, "supplierPassword");
        if (supplierPassword != null && supplierPhone != null) {
            try {
                Supplier supplier = supplierService.querySupplierByPhone(supplierPhone);
                if (supplier == null) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "该手机号不存在");
                    return modelMap;
                }
                supplier = supplierService.supplierLogin(supplierPhone, supplierPassword);
                if (supplier == null) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "密码错误");
                    return modelMap;
                }
                modelMap.put("success", true);
                modelMap.put("supplier", supplier);
                modelMap.put("supplierToken", supplier.getToken());
            } catch (SupplierException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请务必正确输入完整的电话以及密码");
        }
        return modelMap;
    }
    
    @PostMapping("/update")
    public Map<String, Object> updateSupplier(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String supplierStr = HttpServletRequestUtil.getString(request, "supplier");
        ObjectMapper mapper = new ObjectMapper();
        Supplier supplier = null;
        try {
            supplier = mapper.readValue(supplierStr, Supplier.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输失败" + e.getMessage());
            return modelMap;
        }
        if (supplier != null && supplier.getSupplierId() != null) {
            try {
                int res = supplierService.updateSupplier(supplier);
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
        String token = HttpServletRequestUtil.getString(request, "supplierToken");
        // 将用户session置为空
        supplierService.logout(token);
    }

    @PostMapping("/changePassword")
    public Map<String, Object> changePassword(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String supplierPhone = HttpServletRequestUtil.getString(request, "supplierPhone");
        String supplierPassword = HttpServletRequestUtil.getString(request, "supplierPassword");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        if (supplierPhone != null && supplierPassword != null && newPassword != null) {
            try {
                Supplier supplier = supplierService.querySupplierByPhone(supplierPhone);
                if (supplier == null) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "该手机号不存在");
                    return modelMap;
                }
                if (!supplier.getSupplierPassword().equals(supplierPassword)) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "密码错误");
                    return modelMap;
                }
                supplier.setSupplierPassword(newPassword);
                int res = supplierService.updateSupplier(supplier);
                if (res != 1) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "更改密码失败");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (SupplierException e) {
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
    public Map<String, Object> querySupplier(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String supplierStr = HttpServletRequestUtil.getString(request, "supplier");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        ObjectMapper mapper = new ObjectMapper();
        Supplier supplier = null;
        try {
            supplier = mapper.readValue(supplierStr, Supplier.class);
            if (supplier == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "传入数据为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输出错: " + e.getMessage());
            return modelMap;
        }
        try {
            if (pageIndex == -1000) {
                pageIndex = 0;
            }
            if (pageSize == -1000) {
                pageSize = 100;
            }
            List<Supplier> list = supplierService.querySupplierByCondition(supplier, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("staffList", list);
            modelMap.put("staffCount", list.size());
        } catch (SupplierException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }
    
}
