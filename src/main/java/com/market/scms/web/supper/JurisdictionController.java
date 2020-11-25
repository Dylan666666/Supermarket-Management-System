package com.market.scms.web.supper;

import com.market.scms.service.PrimaryMenuService;
import com.market.scms.service.StaffPositionRelationService;
import com.market.scms.service.StaffPositionService;
import com.market.scms.service.StaffService;
import com.market.scms.util.HttpServletRequestUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 100;
        }
        
        
        return modelMap;
    }
    
}
