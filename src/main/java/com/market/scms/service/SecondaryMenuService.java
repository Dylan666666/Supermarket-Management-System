package com.market.scms.service;

import com.market.scms.entity.staff.SecondaryMenu;

import java.util.List;

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
     * 通过二级菜单URL查询二级菜单
     *
     * @param secondaryMenuUrl
     * @return
     */
    SecondaryMenu queryByUrl(String secondaryMenuUrl);

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

    /**
     * 一键查询
     *
     * @return
     */
    List<SecondaryMenu> queryAll();

    /**
     * 通过secondaryMenuId查询功能集合
     *
     * @param primaryMenuId
     * @return
     */
    List<SecondaryMenu> queryByPrimaryMenuId(int primaryMenuId);
}
