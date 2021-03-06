package com.market.scms.mapper;

import com.market.scms.entity.GoodsCategory;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 13:54
 */
public interface GoodsCategoryMapper {
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

    /**
     * 通过id查询
     * 
     * @param categoryId
     * @return
     */
    GoodsCategory queryById(int categoryId);

    /**
     * 更新类别信息
     * 
     * @param goodsCategory
     * @return
     */
    int update(GoodsCategory goodsCategory);

    /**
     * 通过id查询
     *
     * @param staffId
     * @return
     */
    List<GoodsCategory> queryByStaffId(int staffId);

    /**
     * 删除员工时调用，置空staffId
     *
     * @param stocktakingStaffId
     * @return
     */
    int updateByStaffId(int stocktakingStaffId);
}
