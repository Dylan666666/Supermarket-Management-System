package com.market.scms.service;

import com.market.scms.entity.GoodsCategory;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 16:26
 */
public interface GoodsCategoryService {
    /**
     * 查询销售单位列表
     *
     * @return
     */
    List<GoodsCategory> queryAll();

    /**
     * 添加销售单位
     *
     * @param goodsCategory
     * @return
     */
    int insert(GoodsCategory goodsCategory);
}
