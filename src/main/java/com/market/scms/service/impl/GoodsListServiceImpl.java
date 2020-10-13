package com.market.scms.service.impl;

import com.market.scms.dao.GoodsListDao;
import com.market.scms.entity.GoodsList;
import com.market.scms.exceptions.GoodsListException;
import com.market.scms.service.GoodsListService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/13 10:57
 */
@Service
public class GoodsListServiceImpl implements GoodsListService {
    
    @Resource
    private GoodsListDao goodsListDao;

    /**
     * 通过商品ID查询商品信息
     * 
     * @param goodsId
     * @return
     */
    @Override
    public GoodsList queryGoodsById(Long goodsId) throws GoodsListException {
        if (goodsId > 0) {
            try {
                GoodsList goodsList = goodsListDao.queryGoodsById(goodsId);
                return goodsList;
            } catch (GoodsListException e) {
                throw new GoodsListException("查询失败");
            }
        } else {
            throw new GoodsListException("商品代码有误");
        }
    }

    /**
     * 根据订单号查询相关商品单信息
     * 
     * @param goodsOrderId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<GoodsList> queryGoodsByOrderId(Long goodsOrderId, int pageIndex, int pageSize) 
            throws GoodsListException {
        if (goodsOrderId > 0 && pageIndex >= 0 && pageSize > 0) {
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            try {
                List<GoodsList> res = goodsListDao.queryGoodsByOrderId(goodsOrderId, rowIndex, pageSize);
                return res;
            } catch (GoodsListException e) {
                throw new GoodsListException("查询失败");
            }
        } else {
            throw new GoodsListException("订单代码有误");
        }
    }

    /**
     * 添加商品单信息
     * 
     * @param goods
     * @return
     */
    @Override
    public int insertGoods(GoodsList goods) throws GoodsListException {
        if (goods != null && goods.getGoodsName() != null && goods.getDateManufacture() != null && 
        goods.getGoodsCategory() != null && goods.getGoodsOrderId() != null && goods.getGoodsPrice() != null &&
        goods.getShelfLife() != null) {
            try {
                int res = goodsListDao.insertGoods(goods);
                if (res == 0) {
                    throw new GoodsListException("添加失败");
                }
                return res;
            } catch (GoodsListException e) {
                throw new GoodsListException("添加失败");
            }
        } else {
            throw new GoodsListException("信息不全，无法添加");
        }
    }

    /**
     * 更新商品信息
     * 
     * @param goods
     * @return
     */
    @Override
    public int updateGoods(GoodsList goods) throws GoodsListException {
        if (goods != null && goods.getGoodsId() != null) {
            try {
                int res = goodsListDao.updateGoods(goods);
                if (res == 0) {
                    throw new GoodsListException("更改失败");
                }
                return res;
            } catch (GoodsListException e) {
                throw new GoodsListException("更改失败");
            }
        } else {
            throw new GoodsListException("不具备更改条件");
        }
    }
}
