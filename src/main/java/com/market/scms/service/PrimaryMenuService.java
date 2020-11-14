package com.market.scms.service;

import com.market.scms.entity.staff.PrimaryMenu;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 10:53
 */
public interface PrimaryMenuService {
    /**
     * 添加一级菜单
     *
     * @param primaryMenu
     * @return
     */
    int insert(PrimaryMenu primaryMenu);

    /**
     * 通过一级菜单ID删除一级菜单
     *
     * @param primaryMenuId
     * @return
     */
    PrimaryMenu queryById(int primaryMenuId);

    /**
     * 更改一级菜单
     *
     * @param primaryMenu
     * @return
     */
    int update(PrimaryMenu primaryMenu);

    /**
     * 删除一级菜单
     *
     * @param primaryMenuId
     * @return
     */
    int delete(int primaryMenuId);
}
