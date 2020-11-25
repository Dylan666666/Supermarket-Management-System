package com.market.scms.service.impl;

import com.market.scms.entity.staff.PrimaryMenu;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.mapper.PrimaryMenuMapper;
import com.market.scms.service.PrimaryMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 10:53
 */
@Service
public class PrimaryMenuServiceImpl implements PrimaryMenuService {
    
    @Resource
    private PrimaryMenuMapper primaryMenuMapper;
    
    @Override
    public int insert(PrimaryMenu primaryMenu) throws SupermarketStaffException {
        isNull(primaryMenu);
        try {
            int res = primaryMenuMapper.insert(primaryMenu);
            if (res == 0) {
                throw new SupermarketStaffException("添加失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("添加失败");
        }
    }

    @Override
    public PrimaryMenu queryById(int primaryMenuId) throws SupermarketStaffException {
        try {
            PrimaryMenu primaryMenu = primaryMenuMapper.queryById(primaryMenuId);
            return primaryMenu;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("查询失败");
        }
    }

    @Override
    public int update(PrimaryMenu primaryMenu) throws SupermarketStaffException {
        isNull(primaryMenu);
        try {
            int res = primaryMenuMapper.update(primaryMenu);
            if (res == 0) {
                throw new SupermarketStaffException("更改失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("更改失败");
        }
    }

    @Override
    public int delete(int primaryMenuId) throws SupermarketStaffException {
        try {
            int res = primaryMenuMapper.delete(primaryMenuId);
            if (res == 0) {
                throw new SupermarketStaffException("删除失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("删除失败");
        }
    }

    @Override
    public List<PrimaryMenu> queryAll() throws SupermarketStaffException {
        try {
            List<PrimaryMenu> primaryMenuList = primaryMenuMapper.queryAll();
            return primaryMenuList;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("删除失败");
        }
    }

    private void isNull(PrimaryMenu primaryMenu) {
        if (primaryMenu == null) {
            throw new SupermarketStaffException("信息不能为空"); 
        }
    }
}
