package com.market.scms.web.both;

import com.market.scms.entity.GodownEntry;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.enums.GodownEntryStatusStateEnum;
import com.market.scms.enums.StaffPositionStateEnum;
import com.market.scms.exceptions.GodownEntryException;
import com.market.scms.exceptions.GoodsListException;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.GodownEntryService;
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
 * @Date: 2020/10/14 10:21
 */
@RestController
@RequestMapping("/godown")
@CrossOrigin
public class GodownEntryController {
    
    @Resource
    private GodownEntryService godownEntryService;
    
    @Resource
    private SupermarketStaffService supermarketStaffService;

    @GetMapping("/queryById")
    public Map<String, Object> queryGodownEntryById (HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long godownEntryId = HttpServletRequestUtil.getLong(request, "godownEntryId");
        if (godownEntryId > 0) {
            try {
                GodownEntry godownEntry = godownEntryService.queryEntryById(godownEntryId);
                modelMap.put("godownEntry", godownEntry);
                modelMap.put("success", true);
            } catch (GoodsListException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询信息有错");
            return modelMap;
        }
        return modelMap;
    }

    @GetMapping("/queryBySupplierId")
    public Map<String, Object> queryGodownEntryBySupplierId (HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long supplierId = HttpServletRequestUtil.getLong(request, "supplierId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        if (pageIndex == -1000) {
            pageIndex = 0;
        }
        if (pageSize == -1000) {
            pageSize = 100;
        }
        if (supplierId > 0) {
            try {
                List<GodownEntry> list = godownEntryService.queryEntryListBySupplierId(supplierId, pageIndex, pageSize);
                modelMap.put("list", list);
                modelMap.put("success", true);
            } catch (GodownEntryException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询信息有错");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 通过staff职位来获取该职位需要立刻审核的入库单
     * 
     * @param request
     * @return
     */
    @GetMapping("/queryByStaffPhone")
    public Map<String, Object> queryGodownEntryByStaffPhone (HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String staffPhone = HttpServletRequestUtil.getString(request, "staffPhone");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        if (pageIndex == -1000) {
            pageIndex = 0;
        }
        if (pageSize == -1000) {
            pageSize = 100;
        }
        if (staffPhone != null) {
            try {
                SupermarketStaff staff = supermarketStaffService.queryStaffByPhone(staffPhone);
                GodownEntry godownCondition = new GodownEntry();
                Integer position = staff.getStaffPosition();
                if (position.equals(StaffPositionStateEnum.GENERAL_MANAGER.getState())) {
                    godownCondition.setGodownEntryStatus(GodownEntryStatusStateEnum.TO_BE_REVIEWED_BY_MANAGER.getState());
                } else if (position.equals(StaffPositionStateEnum.FINANCE.getState())) {
                    godownCondition.setGodownEntryStatus(GodownEntryStatusStateEnum.APPROVED_BY_MANAGER.getState());
                } else if (position.equals(StaffPositionStateEnum.WORKERS.getState())) {
                    godownCondition.setGodownEntryStatus(GodownEntryStatusStateEnum.DELIVERED_BY_SUPPLIER.getState());
                } else if (position.equals(StaffPositionStateEnum.WAREHOUSE_MANAGER.getState())) {
                    godownCondition.setGodownEntryStatus(GodownEntryStatusStateEnum.APPROVED_BY_WORKERS.getState());
                }
                List<GodownEntry> list = godownEntryService
                        .queryEntryListByGodownCondition(godownCondition, pageIndex, pageSize);
                modelMap.put("list", list);
                modelMap.put("success", "true");
            } catch (GodownEntryException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "查询信息有错");
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询信息有错");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 根据需求查询相应的入库单
     *
     * @param request
     * @return
     */
    @GetMapping("/queryByStatus")
    public Map<String, Object> queryGodownEntryListByStatus (HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int status = HttpServletRequestUtil.getInt(request, "godownEntryStatus");
        if (status != -1000) {
            GodownEntry godownEntry = new GodownEntry();
            godownEntry.setGodownEntryStatus(status);
            int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
            int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
            if (pageIndex == -1000) {
                pageIndex = 0;
            }
            if (pageSize == -1000) {
                pageSize = 100;
            }
            try {
                List<GodownEntry> list = godownEntryService
                        .queryEntryListByGodownCondition(godownEntry, pageIndex, pageSize);
                modelMap.put("list", list);
                modelMap.put("success", true);
            } catch (GodownEntryException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询信息有错");
            return modelMap;
        }
        return modelMap;
    }

}
