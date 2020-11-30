package com.market.scms.config.shiro;

import com.market.scms.entity.SupermarketStaff;
import com.market.scms.entity.staff.Function;
import com.market.scms.entity.staff.SecondaryMenu;
import com.market.scms.entity.staff.StaffJurisdiction;
import com.market.scms.service.FunctionService;
import com.market.scms.service.SecondaryMenuService;
import com.market.scms.service.StaffJurisdictionService;
import com.market.scms.service.StaffService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/9 16:31
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private StaffJurisdictionService staffJurisdictionService;

    @Resource
    private FunctionService functionService;
    
    @Resource
    private SecondaryMenuService secondaryMenuService;
    
    @Resource
    private StaffService staffService;
    
    /**
     * 授权
     * 
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要staff对象
        SupermarketStaff staff = (SupermarketStaff) principalCollection.getPrimaryPrincipal();
            
        //取出职工功能表数据
        List<StaffJurisdiction> staffJurisdictionList = staffJurisdictionService.queryById(staff.getStaffId());
        List<String> primaryPermissionList = new ArrayList<>();
        Set<String> secondaryPermissionSet = new HashSet<>();
        for (StaffJurisdiction jurisdiction : staffJurisdictionList) {
            Function function = functionService.queryById(jurisdiction.getFunctionId());
            primaryPermissionList.add(function.getFunctionUrl());
            SecondaryMenu secondaryMenu = secondaryMenuService.queryById(function.getSecondaryMenuId());
            secondaryPermissionSet.add(secondaryMenu.getSecondaryMenuUrl());
        }
        
        //授权
        for (String s : primaryPermissionList) {
            authorizationInfo.addStringPermission(s);
        }
        for (String s : secondaryPermissionSet) {
            authorizationInfo.addStringPermission(s);
        }
        
        return authorizationInfo;
    }

    /**
     * 身份认证
     * 
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的输入的账号.
        String staffPhone = (String) token.getPrincipal();
        
        //通过username从数据库中查找 User对象.
        SupermarketStaff staff = staffService.queryStaffByPhone(staffPhone);
        if (Objects.isNull(staff)) {
            return null;
        }
        
        return new SimpleAuthenticationInfo(
                //传入对象（一定要是对象！！！getPrimaryPrincipal()要取得）
                staff, 
                //传入的是加密后的密码
                staff.getStaffPassword(),
                //传入salt
                ByteSource.Util.bytes(staff.getStaffPhone()), 
                getName());
    }
}
