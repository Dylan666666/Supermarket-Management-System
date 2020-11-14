package com.market.scms.service.impl;

import com.market.scms.entity.staff.DefaultPositionMenu;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.mapper.DefaultPositionMenuMapper;
import com.market.scms.service.DefaultPositionMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 9:00
 */
@Service
public class DefaultPositionMenuServiceImpl implements DefaultPositionMenuService {
    
    @Resource
    private DefaultPositionMenuMapper menuMapper;
    
    @Override
    public List<DefaultPositionMenu> queryAll() throws SupermarketStaffException {
        try {
            List<DefaultPositionMenu> res = menuMapper.queryAll();
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("查询失败");
        }
    }

    @Override
    public List<DefaultPositionMenu> queryByPositionId(Integer positionId) throws SupermarketStaffException {
        try {
            List<DefaultPositionMenu> res = menuMapper.queryByPositionId(positionId);
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("查询失败");
        }
    }

    @Override
    public int insert(DefaultPositionMenu menu) throws SupermarketStaffException {
        isNull(menu);
        try {
            int res = menuMapper.insert(menu);
            if (res == 0) {
                throw new SupermarketStaffException("添加失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("添加失败");
        }
    }

    @Override
    public int delete(DefaultPositionMenu menu) throws SupermarketStaffException {
        isNull(menu);
        try {
            int res = menuMapper.delete(menu);
            if (res == 0) {
                throw new SupermarketStaffException("删除失败");
            }
            return res;
        } catch (SupermarketStaffException e) {
            throw new SupermarketStaffException("删除失败");
        }
    }
    
    private void isNull(DefaultPositionMenu menu) {
        if (menu == null) {
            throw new SupermarketStaffException("信息不能为空");
        }
    }
}
