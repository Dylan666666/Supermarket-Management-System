package com.market.scms.mapper;

import com.market.scms.entity.staff.SecondaryMenu;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:34
 */
public interface SecondaryMenuMapper {

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
