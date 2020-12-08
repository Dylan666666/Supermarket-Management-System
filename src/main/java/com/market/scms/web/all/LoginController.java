package com.market.scms.web.all;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.StaffA;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.entity.staff.*;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.*;
import com.market.scms.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/30 13:05
 */
@CrossOrigin
@RestController
@Slf4j
public class LoginController {

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
    
    /**
     * 1.1 职工注册
     *
     * @param request
     * @return
     */
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

    /**
     * 1.2 职工登录
     *
     * @param request
     * @return
     */
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
                List<StaffPositionRelation> relation = staffPositionRelationService.queryById(staff.getStaffId());
                log.info("============手机号为" + staff.getStaffPhone() + "的用户登陆==========");
                log.info("============该用户名称为：" + staff.getStaffName() + "==========");
                if (relation != null || relation.size() != 0) {
                    StaffPosition staffPosition = staffPositionService.queryById(relation.get(0).getStaffPositionId());
                    log.info("============该用户职位为：" + staffPosition.getStaffPositionName() + "==========");
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
                List<StaffPositionRelation> relationList = staffPositionRelationService.queryById(staff.getStaffId());
                if (relationList.size() > 0) {
                    StaffPosition staffPosition = staffPositionService.queryById(relationList.get(0).getStaffPositionId());
                    BeanUtils.copyProperties(staffPosition, staffA1);
                    BeanUtils.copyProperties(relationList.get(0), staffA1);
                }
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
     * 1.4 职工退出
     *
     * @param request
     */
    @PostMapping("/logout")
    @ResponseBody
    public void staffLogout(HttpServletRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        } catch (Exception e) {}
    }
}
