package com.market.scms.mapper;

import com.market.scms.entity.Goods;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 11:50
 */
public interface GoodsMapper {
    /**
     * 添加商品
     * 
     * @param goods
     * @return
     */
    int insertGoods(Goods goods);

    /**
     * 更改商品信息
     * 
     * @param goods
     * @return
     */
    int updateGoods(Goods goods);

    /**
     * 查询所有商品列表
     * 
     * @return
     */
    List<Goods> queryAll();

    /**
     * 条件查询产品
     * 
     * @param goodsCondition
     * @return
     */
    List<Goods> queryByCondition(Goods goodsCondition);

    /**
     * 删除商品
     * 
     * @param goodsId
     * @return
     */
    int deleteGoods(Long goodsId);
}
