package com.market.scms.service;

import com.market.scms.entity.GoodsList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/13 10:53
 */
public interface GoodsListService {

    /**
     * 通过商品代码查询商品
     *
     * @param goodsId
     * @return
     */
    GoodsList queryGoodsById(Long goodsId);

    /**
     * 根据订单号查询相关商品
     *
     * @param goodsOrderId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<GoodsList> queryGoodsByOrderId(Long goodsOrderId, int pageIndex, int pageSize);

    /**
     * 添加新商品信息
     *
     * @param goods
     * @return
     */
    int insertGoods(GoodsList goods);

    /**
     * 更改商品表信息
     *
     * @param goods
     * @return
     */
    int updateGoods(GoodsList goods);
}
