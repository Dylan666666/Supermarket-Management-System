package com.market.scms.service;

import com.market.scms.entity.staff.DefaultPositionMenu;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 8:59
 */
public interface DefaultPositionMenuService {
    /**
     * 一件查询所有
     *
     * @return
     */
    List<DefaultPositionMenu> queryAll();

    /**
     * 通过职位id查询对应二级菜单
     *
     * @param positionId
     * @return
     */
    List<DefaultPositionMenu> queryByPositionId(Integer positionId);

    /**
     * 插入新数据
     *
     * @param menu
     * @return
     */
    int insert(DefaultPositionMenu menu);

    /**
     * 删除该行数据
     *
     * @param menu
     * @return
     */
    int delete(DefaultPositionMenu menu);
}
