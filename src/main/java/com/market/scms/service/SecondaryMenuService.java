package com.market.scms.service;

import com.market.scms.entity.staff.SecondaryMenu;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 16:07
 */
public interface SecondaryMenuService {
    /**
     * 添加二级菜单
     *
     * @param secondaryMenu
     * @return
     */
    int insert(SecondaryMenu secondaryMenu);

    /**
     * 通过二级菜单ID查询二级菜单
     *
     * @param secondaryMenuId
     * @return
     */
    SecondaryMenu queryById(int secondaryMenuId);

    /**
     * 更改二级菜单
     *
     * @param secondaryMenu
     * @return
     */
    int update(SecondaryMenu secondaryMenu);

    /**
     * 删除二级菜单
     *
     * @param secondaryMenuId
     * @return
     */
    int delete(int secondaryMenuId);
}
