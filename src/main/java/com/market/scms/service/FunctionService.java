package com.market.scms.service;

import com.market.scms.entity.staff.Function;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 9:42
 */
public interface FunctionService {
    /**
     * 添加三级菜单
     *
     * @param function
     * @return
     */
    int insert(Function function);

    /**
     * 删除三级菜单
     *
     * @param functionId
     * @return
     */
    int delete(int functionId);

    /**
     * 通过功能id查询功能表
     *
     * @param functionId
     * @return
     */
    Function queryById(int functionId);

    /**
     * 更改三级菜单信息
     *
     * @param function
     * @return
     */
    int update(Function function);

    /**
     * 通过二级菜单查询三级功能表集合
     * 
     * @param secondaryMenuId
     * @return
     */
    List<Function> querySecondaryMenuId(int secondaryMenuId);

    /**
     * 一键查询
     *
     * @return
     */
    List<Function> queryAll();
}
