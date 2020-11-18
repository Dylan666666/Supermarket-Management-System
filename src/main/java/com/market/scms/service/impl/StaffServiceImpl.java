package com.market.scms.service.impl;

import com.market.scms.entity.staff.StaffPositionRelation;
import com.market.scms.mapper.*;
import com.market.scms.entity.SupermarketStaff;
import com.market.scms.enums.StaffStatusStateEnum;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.service.StaffService;
import com.market.scms.util.PageCalculator;
import com.market.scms.util.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 14:18
 */
@Service
public class StaffServiceImpl implements StaffService {
    
    @Resource
    private SupermarketStaffMapper staffMapper;
    
    @Resource
    private StaffPositionRelationMapper staffPositionRelationMapper;
    
    @Override
    public SupermarketStaff queryStaffByPhone(String staffPhone) throws SupermarketStaffException {
        if (staffPhone != null) {
            try {
                SupermarketStaff staff = staffMapper.queryStaffByPhone(staffPhone);
                return staff;
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("查询失败");
            }
        } else {
            throw new SupermarketStaffException("信息为空");
        }
    }

    @Override
    public int insertStaff(SupermarketStaff staff) throws SupermarketStaffException {
        if (staff != null && staff.getStaffPhone() != null && staff.getStaffPassword() != null &&
                staff.getStaffName() != null) {
            try {
                if (staffMapper.queryStaffByPhone(staff.getStaffPhone()) != null) {
                    throw new SupermarketStaffException("该手机号已被注册");
                }
                staff.setSalt(ByteSource.Util.bytes(staff.getStaffPhone()).toString());
                staff.setLastEditTime(new Date());
                staff.setCreateTime(new Date());
                staff.setStaffStatus(StaffStatusStateEnum.JUST_REGISTERED.getState());
                new PasswordHelper().encryptPassword(staff);
                int res = staffMapper.insertStaff(staff);
                if (res == 0) {
                    throw new SupermarketStaffException("注册失败");
                }
                return res;
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("注册失败");
            }
        } else {
            throw new SupermarketStaffException("必要信息为空");
        }
    }

    @Override
    public int updateStaff(SupermarketStaff staff) throws SupermarketStaffException {
        if (staff != null && staff.getStaffId() != null) {
            try {
                staff.setLastEditTime(new Date());
                int res = staffMapper.updateStaff(staff);
                if (res == 0) {
                    throw new SupermarketStaffException("信息更改失败");
                }
                return res;
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("信息更改失败");
            }
        } else {
            throw new SupermarketStaffException("信息为空");
        }
    }

    @Override
    public int updateStaffPosition(SupermarketStaff staff) throws SupermarketStaffException {
        if (staff != null && staff.getStaffId() != null && staff.getStaffPhone() != null 
                && staff.getStaffPosition() != null) {
            try {
                SupermarketStaff curStaff = staffMapper.queryStaffByPhone(staff.getStaffPhone());
                if (curStaff.getStaffPosition() == null) {
                    //插入职工职位表
                    StaffPositionRelation relation = new StaffPositionRelation();
                    relation.setStaffPositionStatus(1);
                    relation.setStaffPositionId(staff.getStaffPosition());
                    relation.setStaffId(staff.getStaffId());
                    int res1 = staffPositionRelationMapper.insert(relation);
                    if (res1 == 0) {
                        throw new SupermarketStaffException("更改职位失败");
                    }
                } else {
                    StaffPositionRelation relation = new StaffPositionRelation();
                    //删除职工职位行数据
                    relation.setStaffId(staff.getStaffId());
                    relation.setStaffPositionId(curStaff.getStaffPosition());
                    staffPositionRelationMapper.delete(relation);
                    //添加职工职位行数据
                    relation.setStaffPositionStatus(1);
                    relation.setStaffPositionId(staff.getStaffPosition());
                    int res1 = staffPositionRelationMapper.insert(relation);
                    if (res1 == 0) {
                        throw new SupermarketStaffException("更改职位失败");
                    }
                }
            } catch (SupermarketStaffException e) {
                throw new SupermarketStaffException("更改职位失败");
            }
        } else {
            throw new SupermarketStaffException("信息为空");
        }
        return 1;
    }

    @Override
    public SupermarketStaff staffLogin(String staffPhone, String staffPassword) throws SupermarketStaffException {
        // 获取Subject实例对象，用户实例
        Subject currentUser = SecurityUtils.getSubject();

        // 将用户名和密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(staffPhone, staffPassword);
        
        SupermarketStaff staff;
        
        //认证
        try {
            // 传到 MyShiroRealm 类中的方法进行认证
            currentUser.login(token);
            
            // 构建缓存用户信息返回给前端
            System.out.println("构建缓存用户信息返回给前端");
            staff = (SupermarketStaff) currentUser.getPrincipals().getPrimaryPrincipal();
            //设置TOKEN返回给前端
            staff.setToken(currentUser.getSession().getId().toString());
            
        } catch (UnknownAccountException e) {
            throw new SupermarketStaffException("账号不存在!");
        } catch (IncorrectCredentialsException e) {
            throw new SupermarketStaffException("密码不正确!");
        } catch (AuthenticationException e) {
            throw new SupermarketStaffException("用户验证失败!");
        }
        return staff;
    }

    @Override
    public List<SupermarketStaff> queryStaffByCondition(SupermarketStaff staffCondition, int pageIndex, int pageSize)
            throws SupermarketStaffException{
        if (staffCondition != null && pageIndex != -1000 && pageSize != -1000) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<SupermarketStaff> list = staffMapper.queryStaffByCondition(staffCondition, rowIndex, pageSize);
                return list;
            } catch (SupermarketStaffException staffException) {
                throw new SupermarketStaffException("查询失败");
            }
        } else {
            throw new SupermarketStaffException("信息不足，查询失败");
        }
    }

    @Override
    public void staffLogout(String staffToken) throws SupermarketStaffException {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
