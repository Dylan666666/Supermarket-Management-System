package com.market.scms.web.supper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.StaffA;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.entity.staff.Function;
import com.market.scms.entity.staff.SecondaryMenu;
import com.market.scms.entity.staff.StaffPosition;
import com.market.scms.entity.staff.StaffPositionRelation;
import com.market.scms.enums.StaffStatusStateEnum;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.*;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.PasswordHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
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
 * RBAC 一类 - 信息
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/26 15:06
 */
@CrossOrigin
@RestController
public class StaffRoleController {

    @Resource
    private StaffService staffService;

    @Resource
    private SecondaryMenuService secondaryMenuService;

    @Resource
    private FunctionService functionService;

    @Resource
    private StaffPositionRelationService staffPositionRelationService;

    @Resource
    private StaffPositionService staffPositionService;

    /**
     * 1.2超级管理员 用户列表
     *
     * @param request
     * @return
     */
    @PostMapping("/stafflist")
    @ResponseBody
    @RequiresPermissions("/stafflist")
    public Map<String,Object> staffQuery(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
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
        } catch (SupermarketStaffException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 1.3超级管理员 用户列表 提交修改
     *
     * @param request
     * @return
     */
    @PostMapping("/stafflist/modifycommit")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/stafflist/modifycommit")
    public Map<String,Object> modifyCommit(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String staffAStr = HttpServletRequestUtil.getString(request, "staffA");
        if (staffAStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息为空，提交修改失败");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        StaffA staffA = null;
        try {
            staffA = mapper.readValue(staffAStr, StaffA.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息更改失败-01");
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
     * 1.4超级管理员 用户列表 删除
     *
     * @param request
     * @return
     */
    @PostMapping("/stafflist/deletestaff")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/stafflist/deletestaff")
    public Map<String,Object> deleteStaff(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
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
     * 1.5超级管理员 用户列表 角色分配
     *
     * @param request
     * @return
     */
    @PostMapping("/stafflist/positiondistribution")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/stafflist/positiondistribution")
    public Map<String,Object> positionDistribution(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        if (staffId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提供信息为空，查询失败");
            return modelMap;
        }
        try {
            List<StaffPosition> staffPositionList = staffPositionService.queryAll();
            List<StaffPositionRelation> staffPositionRelationList = staffPositionRelationService.queryById(staffId);
            if (staffPositionRelationList.size() != 0) {
                modelMap.put("staffPositionRelation", staffPositionRelationList.get(0));
            } else {
                modelMap.put("staffPositionRelation", null);
            }
            modelMap.put("staffPositionList", staffPositionList);
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 1.6超级管理员 用户列表 角色分配提交
     *
     * @param request
     * @return
     */
    @PostMapping("/stafflist/positiondistributioncommit")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/stafflist/positiondistributioncommit")
    public Map<String,Object> positionDistributionCommit(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String staffPositionRelationStr = HttpServletRequestUtil.getString(request, "staffPositionRelation");
        if (staffPositionRelationStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息为空，提交失败");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        StaffPositionRelation staffPositionRelation = null;
        try {
            staffPositionRelation = mapper.readValue(staffPositionRelationStr, StaffPositionRelation.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息更改失败-01");
            return modelMap;
        }
        try {
            SupermarketStaff curStaff = staffService.queryById(staffPositionRelation.getStaffId());
            if (curStaff == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该职工不存在");
                return modelMap;
            }
            List<StaffPositionRelation> cur = staffPositionRelationService.queryById(staffPositionRelation.getStaffId());
            if (cur.size() == 0) {
                int res = staffPositionRelationService.insert(staffPositionRelation);
                if (res == 0) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "修改失败");
                    return modelMap;
                }
                SupermarketStaff staff = staffService.queryById(staffPositionRelation.getStaffId());
                staff.setStaffStatus(StaffStatusStateEnum.NORMAL.getState());
                res = staffService.updateStaff(staff);
                if (res == 0) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "修改失败");
                    return modelMap;
                }
            } else {
                if (cur.get(0).getStaffPositionStatus().equals(staffPositionRelation.getStaffPositionStatus()) &&
                        cur.get(0).getStaffPositionId().equals(staffPositionRelation.getStaffPositionId())) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "信息无变化，更改失败");
                    return modelMap;
                }
                //只是状态改变
                if (cur.get(0).getStaffPositionId().equals(staffPositionRelation.getStaffPositionId())) {
                    staffPositionRelation.setStaffPositionId(null);
                    staffPositionRelationService.update(staffPositionRelation);
                } else {
                    //只是职位修改
                    staffPositionRelationService.delete(cur.get(0));
                    staffPositionRelationService.insert(staffPositionRelation);
                }
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "修改失败！");
            return modelMap;
        }
        return modelMap;
    }
    
}
