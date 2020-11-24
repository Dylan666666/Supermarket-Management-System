package com.market.scms.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.StaffA;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.entity.staff.*;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.*;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 14:33
 */
@CrossOrigin
@RestController
public class StaffController {

    @Resource
    private StaffService staffService;
    
    @Resource
    private PrimaryMenuService primaryMenuService;
    
    @Resource
    private SecondaryMenuService secondaryMenuService;
    
    @Resource
    private FunctionService functionService;
    
    @Resource
    private StaffJurisdictionService staffJurisdictionService;
    
    @Resource
    private StaffPositionRelationService staffPositionRelationService;
    
    @Resource
    private StaffPositionService staffPositionService;
    
    @PostMapping("/staff/insert")
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

    @PostMapping("/staff/login")
    @ResponseBody
    @Transactional
    public Map<String,Object> staffLogin(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
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
        if (staffA != null && staffA.getStaffPhone() != null && staffA.getStaffPassword() != null) {
            try {
                SupermarketStaff staff = staffService.staffLogin(staffA.getStaffPhone(), staffA.getStaffPassword());
                if (staff == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "登录失败");
                    return modelMap;
                }
                List<StaffJurisdiction> staffJurisdictionList = staffJurisdictionService.queryById(staff.getStaffId());
                Set<Integer> secondaryIdSet = new HashSet<>();
                Set<Integer> primaryIdSet = new HashSet<>();
                List<SecondaryMenu> secondaryMenuList = new ArrayList<>();
                List<PrimaryMenu> primaryMenuList = new ArrayList<>();
                for (StaffJurisdiction staffJurisdiction : staffJurisdictionList) {
                    Function function = functionService.queryById(staffJurisdiction.getFunctionId());
                    SecondaryMenu curSe = secondaryMenuService.queryById(function.getSecondaryMenuId());
                    if (secondaryIdSet.contains(curSe.getSecondaryMenuId())) {
                        continue;
                    } else {
                        secondaryIdSet.add(curSe.getSecondaryMenuId());
                        secondaryMenuList.add(curSe);
                        PrimaryMenu curPri =primaryMenuService.queryById(curSe.getPrimaryMenuId());
                        if (!primaryIdSet.contains(curPri.getPrimaryMenuId())) {
                            primaryIdSet.add(curPri.getPrimaryMenuId());
                            primaryMenuList.add(curPri);
                        }
                    }
                }
                StaffA staffA1 = new StaffA();
                BeanUtils.copyProperties(staff, staffA1);
                modelMap.put("staffA", staffA1);
                modelMap.put("staffToken", staff.getToken());
                modelMap.put("primaryMenuList", primaryMenuList);
                modelMap.put("secondaryMenuList", secondaryMenuList);
                modelMap.put("success",true);
            } catch (SupermarketStaffException staffException) {
                modelMap.put("success",false);
                modelMap.put("errMsg", staffException.getMessage());
                return modelMap;
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "登录失败");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "登录信息不完整");
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
    public Map<String,Object> staffUpdate(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String staffAStr = HttpServletRequestUtil.getString(request, "staffA");
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
            SupermarketStaff staff = new SupermarketStaff();
            BeanUtils.copyProperties(staffA, staff);
            if (staff.getStaffPhone() != null) {
                SupermarketStaff cur = staffService.queryStaffByPhone(staff.getStaffPhone());
                if (cur != null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "该手机号已被注册，信息更改失败");
                    return modelMap;
                }
            }
            //更改职工信息
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
        } catch (Exception e) {
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
            pageSize = 100;
        }
        
        try {
            List<SupermarketStaff> list = staffService
                    .queryStaffByCondition(new SupermarketStaff(), pageIndex, pageSize);
            List<StaffA> staffAList = new ArrayList<>(list.size());
            for (SupermarketStaff staff : list) {
                StaffA staffA = new StaffA();
                BeanUtils.copyProperties(staff, staffA);
                staffAList.add(staffA);
            }
            modelMap.put("staffAList", staffAList);
            modelMap.put("recordSum", staffAList.size());
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
            StaffPositionRelation staffPositionRelation = new StaffPositionRelation();
            staffPositionRelation.setStaffId(staffId);
            res = staffPositionRelationService.delete(staffPositionRelation);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "删除失败");
                return modelMap;
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
            StaffPositionRelation staffPositionRelation = staffPositionRelationService.queryById(staffId).get(0);
            modelMap.put("staffPositionRelation", staffPositionRelation);
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
            StaffPositionRelation cur = staffPositionRelationService.queryById(staffPositionRelation.getStaffId()).get(0);
            if (cur != null) {
                if (cur.getStaffPositionStatus().equals(staffPositionRelation.getStaffPositionStatus()) &&
                        cur.getStaffPositionId().equals(staffPositionRelation.getStaffPositionId())) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "信息无变化，更改失败");
                    return modelMap;
                }
                if (cur.getStaffPositionId().equals(staffPositionRelation.getStaffPositionId())) {
                    staffPositionRelationService.update(staffPositionRelation);
                } else {
                    staffPositionRelationService.delete(cur);
                    staffPositionRelationService.insert(staffPositionRelation);
                }
            } else {
                staffPositionRelationService.insert(staffPositionRelation);
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }
}
