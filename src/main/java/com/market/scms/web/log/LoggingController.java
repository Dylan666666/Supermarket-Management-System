package com.market.scms.web.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.StaffA;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.entity.log.LoggingEvent;
import com.market.scms.entity.staff.Function;
import com.market.scms.entity.staff.StaffJurisdiction;
import com.market.scms.entity.staff.StaffPosition;
import com.market.scms.entity.staff.StaffPositionRelation;
import com.market.scms.exceptions.LoggingEventException;
import com.market.scms.service.*;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.PageCalculator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/16 14:13
 */
@CrossOrigin
@RestController
public class LoggingController {
    
    @Resource
    private LoggingEventService loggingEventService;
    
    @Resource
    private StaffJurisdictionService staffJurisdictionService;
    
    @Resource
    private FunctionService functionService;
    
    @Resource
    private StaffService staffService;
    
    @Resource
    private StaffPositionService staffPositionService;
    
    @Resource
    private StaffPositionRelationService staffPositionRelationService;

    /**
     * 9.1日志记录
     *
     * @param request
     * @return
     */
    @PostMapping("/logging")
    @ResponseBody
    @RequiresPermissions("/logging")
    public Map<String,Object> logging(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        if (secondaryMenuId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败-01");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<StaffJurisdiction> staffJurisdictionList = staffJurisdictionService.queryById(staffId);
            List<Function> functionList = new ArrayList<>();
            for (StaffJurisdiction staffJurisdiction : staffJurisdictionList) {
                Function function = functionService.queryById(staffJurisdiction.getFunctionId());
                if (function.getSecondaryMenuId().equals(secondaryMenuId)) {
                    functionList.add(function);
                }
            }
            
            List<LoggingEvent> loggingEventList = loggingEventService.queryAll(0, 100000);
            int recordSum = loggingEventList.size();
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            int rightIndex = rowIndex + pageSize;
            if (recordSum < rightIndex) {
                rightIndex = recordSum;
            }
            List<LoggingEvent> res = loggingEventList.subList(rowIndex, rightIndex);
            
            List<SupermarketStaff> staffList = staffService
                    .queryStaffByCondition(new SupermarketStaff(), 0, 10000);
            List<StaffA> staffAList = new ArrayList<>(staffList.size());
            for (SupermarketStaff staff : staffList) {
                StaffA staffA = new StaffA();
                List<StaffPositionRelation> relationList = staffPositionRelationService.queryById(staff.getStaffId());
                if (relationList.size() != 0) {
                    Integer staffPositionId = relationList.get(0).getStaffPositionId();
                    StaffPosition staffPosition = staffPositionService.queryById(staffPositionId);
                    BeanUtils.copyProperties(staffPosition, staffA);
                    BeanUtils.copyProperties(relationList.get(0), staffA);
                }
                BeanUtils.copyProperties(staff, staffA);
                staffAList.add(staffA);
            }
            
            modelMap.put("staffAList", staffAList);
            modelMap.put("loggingEventList", res);
            modelMap.put("functionList", functionList);
            modelMap.put("recordSum", recordSum);
            modelMap.put("success", true);
        } catch (LoggingEventException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 9.2日志记录 模糊查询
     *
     * @param request
     * @return
     */
    @PostMapping("/logging/findByConditions")
    @ResponseBody
    @RequiresPermissions("/logging/findByConditions")
    public Map<String,Object> loggingFindByConditions(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        String loggingEventStr = HttpServletRequestUtil.getString(request, "loggingEvent");
        LoggingEvent loggingEvent = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            loggingEvent = mapper.readValue(loggingEventStr, LoggingEvent.class);
            if (loggingEvent == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "查询失败-01");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败-01");
            return modelMap;
        }
        if (loggingEventStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败-01");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<LoggingEvent> loggingEventList = loggingEventService
                    .queryByCondition(loggingEvent, 0, 100000);
            int recordSum = loggingEventList.size();
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            int rightIndex = rowIndex + pageSize;
            if (recordSum < rightIndex) {
                rightIndex = recordSum;
            }
            List<LoggingEvent> res = loggingEventList.subList(rowIndex, rightIndex);

            List<SupermarketStaff> staffList = staffService
                    .queryStaffByCondition(new SupermarketStaff(), 0, 10000);
            List<StaffA> staffAList = new ArrayList<>(staffList.size());
            for (SupermarketStaff staff : staffList) {
                StaffA staffA = new StaffA();
                List<StaffPositionRelation> relationList = staffPositionRelationService.queryById(staff.getStaffId());
                if (relationList.size() != 0) {
                    Integer staffPositionId = relationList.get(0).getStaffPositionId();
                    StaffPosition staffPosition = staffPositionService.queryById(staffPositionId);
                    BeanUtils.copyProperties(staffPosition, staffA);
                    BeanUtils.copyProperties(relationList.get(0), staffA);
                }
                BeanUtils.copyProperties(staff, staffA);
                staffAList.add(staffA);
            }

            modelMap.put("staffAList", staffAList);
            modelMap.put("loggingEventList", res);
            modelMap.put("recordSum", recordSum);
            modelMap.put("success", true);
        } catch (LoggingEventException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 9.3日志记录 删除
     *
     * @param request
     * @return
     */
    @PostMapping("/logging/delete")
    @ResponseBody
    @RequiresPermissions("/logging/delete")
    public Map<String,Object> delete(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        Long eventId = HttpServletRequestUtil.getLong(request, "eventId");
        if (eventId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "删除失败-01");
            return modelMap;
        }
        try {
            loggingEventService.delete(eventId);
            modelMap.put("success", true);
        } catch (LoggingEventException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "删除失败");
            return modelMap;
        } catch (Exception e ){
            modelMap.put("success",false);
            modelMap.put("errMsg", "删除失败");
            return modelMap;
        }
        return modelMap;
    }
    
}
