package com.market.scms.service;

import com.market.scms.dto.ImageHolder;
import com.market.scms.entity.Goods;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 12:36
 */
public interface GoodsService {
    /**
     * 添加商品
     * 
     * @param goods
     * @param thumbnail
     * @return
     */
    int insertGoods(Goods goods, ImageHolder thumbnail);

    /**
     * 更改商品信息
     * 
     * @param goods
     * @param thumbnail
     * @return
     */
    int updateGoods(Goods goods, ImageHolder thumbnail);

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
