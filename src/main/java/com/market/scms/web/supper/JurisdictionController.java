package com.market.scms.web.supper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.FunctionTree;
import com.market.scms.bean.PrimaryMenuTree;
import com.market.scms.bean.SecondaryMenuTree;
import com.market.scms.bean.StaffA;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.entity.staff.*;
import com.market.scms.enums.StaffStatusStateEnum;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.service.*;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.PasswordHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 16:21
 */
@CrossOrigin
@RestController
public class JurisdictionController {
    
    @Resource
    private StaffService staffService;
    
    @Resource
    private StaffPositionRelationService staffPositionRelationService;
    
    @Resource
    private StaffPositionService staffPositionService;
    
    @Resource
    private PrimaryMenuService primaryMenuService;
    
    @Resource
    private SecondaryMenuService secondaryMenuService;
    
    @Resource
    private FunctionService functionService;
    
    @Resource
    private StaffJurisdictionService staffJurisdictionService;

    /**
     * 1.1 超级管理员 用户列表（权限管理）
     *
     * @param request
     * @return
     */
    @PostMapping("/stafflistjurisdiction")
    @ResponseBody
    @RequiresPermissions("/stafflistjurisdiction")
    public Map<String,Object> staffQuery(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        if (secondaryMenuId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问权限,访问失败");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<SupermarketStaff> list = staffService
                    .queryStaffByCondition(new SupermarketStaff(), pageIndex, pageSize);
            List<StaffA> staffAList = new ArrayList<>(list.size());
            for (SupermarketStaff staff : list) {
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
            int recordSum = staffService.countStaffAll();
            modelMap.put("staffAList", staffAList);
            modelMap.put("recordSum", recordSum);
            if (pageIndex == 0) {
                SecondaryMenu secondaryMenu = secondaryMenuService.queryByUrl("/stafflist");
                List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenu.getSecondaryMenuId());
                modelMap.put("functionList", functionList);
            }
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "访问失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 1.2超级管理员 用户列表（权限管理） 提交修改
     *
     * @param request
     * @return
     */
    @PostMapping("/stafflistjurisdiction/modifycommit")
    @ResponseBody
    @RequiresPermissions("/stafflistjurisdiction/modifycommit")
    public Map<String,Object> modifyCommit(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String staffAStr = HttpServletRequestUtil.getString(request, "staffA");
        ObjectMapper mapper = new ObjectMapper();
        StaffA staffA = null;
        if (staffAStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息为空,登录失败");
            return modelMap;
        }
        try {
            staffA = mapper.readValue(staffAStr, StaffA.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "登录失败-01");
            return modelMap;
        }
        try {
            SupermarketStaff staff = staffService.queryById(staffA.getStaffId());
            if (staff == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该用户不存在");
                return modelMap;
            }
            if (staffA.getStaffName() == null || staffA.getStaffPhone() == null
                    || staffA.getStaffPassword() == null || staff.getStaffStatus() == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "必要信息不能为空");
                return modelMap;
            }
            if (!staffA.getStaffPhone().equals(staff.getStaffPhone())) {
                SupermarketStaff cur = staffService.queryStaffByPhone(staffA.getStaffPhone());
                if (cur != null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "该手机号已被注册,修改失败");
                    return modelMap;
                }
                staff.setStaffPhone(staffA.getStaffPhone());
                staff.setSalt(ByteSource.Util.bytes(staff.getStaffPhone()).toString());
            }
            if (!staffA.getStaffPassword().equals(staff.getStaffPassword())) {
                staff.setStaffPassword(staffA.getStaffPassword());
                new PasswordHelper().encryptPassword(staff);
            }
            if (staffA.getStaffName() != null) {
                staff.setStaffName(staffA.getStaffName());
            }
            StaffStatusStateEnum staffStatusStateEnum = StaffStatusStateEnum.stateOf(staffA.getStaffStatus());
            if (staffStatusStateEnum == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该状态非法,修改失败");
                return modelMap;
            }
            staff.setStaffStatus(staffA.getStaffStatus());
            int res = staffService.updateStaff(staff);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "修改失败");
                return modelMap;
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 1.3 超级管理员 用户列表（权限管理） 删除
     *
     * @param request
     * @return
     */
    @PostMapping("/stafflistjurisdiction/deletestaff")
    @ResponseBody
    @RequiresPermissions("/stafflistjurisdiction/deletestaff")
    public Map<String,Object> deleteStaff(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        try {
            int res = staffService.deleteStaff(staffId);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "删除失败");
                return modelMap;
            }
            List<StaffPositionRelation> staffPositionRelationList = staffPositionRelationService.queryById(staffId);
            if (staffPositionRelationList.size() != 0) {
                res = staffPositionRelationService.delete(staffPositionRelationList.get(0));
                if (res == 0) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "删除失败");
                    return modelMap;
                }
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "删除失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 1.4超级管理员 用户列表（权限管理） 权限分配
     *
     * @param request
     * @return
     */
    @PostMapping("/stafflistjurisdiction/jurisdictiondistribution")
    @ResponseBody
    @RequiresPermissions("/stafflistjurisdiction/jurisdictiondistribution")
    public Map<String,Object> jurisdictionDistribution(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        try {
            SupermarketStaff staff = staffService.queryById(staffId);
            if (staff == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该用户不存在，访问失败");
                return modelMap;
            }
            //查出总功能树
            List<PrimaryMenu> primaryMenuList = primaryMenuService.queryAll();
            
            //三层顺序查找并封装
            //一级
            List<PrimaryMenuTree> primaryMenuTreeList = new ArrayList<>(primaryMenuList.size());
            System.out.println("primaryMenuList.size() = " + primaryMenuList.size());
            
            for (PrimaryMenu primaryMenu : primaryMenuList) {
                PrimaryMenuTree primaryMenuTree = new PrimaryMenuTree();
                BeanUtils.copyProperties(primaryMenu, primaryMenuTree);
                List<SecondaryMenu> secondaryMenuList = secondaryMenuService
                        .queryByPrimaryMenuId(primaryMenu.getPrimaryMenuId());
                //二级
                List<SecondaryMenuTree> secondaryMenuTreeList = new ArrayList<>(secondaryMenuList.size());
                for (SecondaryMenu secondaryMenu : secondaryMenuList) {
                    SecondaryMenuTree secondaryMenuTree = new SecondaryMenuTree();
                    //三级
                    List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenu.getSecondaryMenuId());
                    List<FunctionTree> functionTreeList = new ArrayList<>(secondaryMenuList.size());
                    for (Function function : functionList) {
                        FunctionTree functionTree = new FunctionTree();
                        BeanUtils.copyProperties(function, functionTree);
                        functionTree.setIsSelected(0);
                        functionTreeList.add(functionTree);
                    }
                    BeanUtils.copyProperties(secondaryMenu, secondaryMenuTree);
                    secondaryMenuTree.setFunctionTreeList(functionTreeList);
                    secondaryMenuTreeList.add(secondaryMenuTree);
                }
                BeanUtils.copyProperties(primaryMenu, primaryMenuTree);
                primaryMenuTree.setSecondaryMenuTreeList(secondaryMenuTreeList);
                primaryMenuTreeList.add(primaryMenuTree);
            }
            
            //获取用户权限
            List<StaffJurisdiction> staffJurisdictionList = staffJurisdictionService.queryById(staffId);
            Set<Integer> functionSet = new HashSet<>(staffJurisdictionList.size());
            for (StaffJurisdiction staffJurisdiction : staffJurisdictionList) {
                functionSet.add(staffJurisdiction.getFunctionId());
            }
            
            //给权限树isSelected赋值
            for (PrimaryMenuTree primaryMenuTree : primaryMenuTreeList) {
                for (SecondaryMenuTree secondaryMenuTree : primaryMenuTree.getSecondaryMenuTreeList()) {
                    for (FunctionTree functionTree : secondaryMenuTree.getFunctionTreeList()) {
                        if (functionSet.contains(functionTree.getFunctionId())) {
                            functionTree.setIsSelected(1);
                        }
                    }
                }
            }
            
            modelMap.put("primaryMenuTreeList", primaryMenuTreeList);
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "访问失败");
            return modelMap;
        }
        
        return modelMap;
    }
}
