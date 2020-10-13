package com.market.scms.dao;

import com.market.scms.entity.GoodsList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/12 21:17
 */
public interface GoodsListDao {

    /**
     * 通过商品代码查询商品
     * 
     * @param goodsId
     * @return
     */
    GoodsList queryGoodsById(@Param("goodsId") Long goodsId);

    /**
     * 根据订单号查询相关商品
     * 
     * @param goodsOrderId
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<GoodsList> queryGoodsByOrderId(@Param("goodsOrderId") Long goodsOrderId,
                                         @Param("rowIndex") int rowIndex,
                                         @Param("pageSize") int pageSize);

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
