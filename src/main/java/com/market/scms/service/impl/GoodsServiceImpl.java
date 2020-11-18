package com.market.scms.service.impl;

import com.market.scms.dto.ImageHolder;
import com.market.scms.entity.Goods;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.GoodsMapper;
import com.market.scms.service.GoodsService;
import com.market.scms.util.ImageUtil;
import com.market.scms.util.PathUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 12:37
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    
    @Resource
    private GoodsMapper goodsMapper;
    
    @Override
    public int insertGoods(Goods goods, ImageHolder thumbnail) throws WareHouseManagerException {
        isNull(goods);
        if (goods.getGoodsName() != null && goods.getGoodsBrand() != null && 
                goods.getGoodsCategoryId() != null && goods.getGoodsId() != null &&
                goods.getGoodsSpecifications() != null) {
            try {
                if (thumbnail != null) {
                    addThumbnail(goods, thumbnail);
                }
                int res = goodsMapper.insertGoods(goods);
                if (res != 1) {
                    throw new WareHouseManagerException("创建商品失败");
                }
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("创建商品失败");
            }   
        } else {
            throw new WareHouseManagerException("缺少必要信息");
        }
    }

    @Override
    public int updateGoods(Goods goods, ImageHolder thumbnail) throws WareHouseManagerException {
        isNull(goods);
        if (goods.getGoodsId() != null) {
            try {
                if (thumbnail != null) {
                    Goods cur = new Goods();
                    cur.setGoodsId(goods.getGoodsId());
                    cur = goodsMapper.queryByCondition(cur).get(0);
                    if (cur != null && cur.getGoodsPicture() != null) {
                        ImageUtil.deleteFileOrPath(cur.getGoodsPicture());
                    }
                    addThumbnail(goods, thumbnail);
                }
                int res = goodsMapper.updateGoods(goods);
                if (res != 1) {
                    throw new WareHouseManagerException("更新商品失败");
                }
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更新商品失败");
            }
        } else {
            throw new WareHouseManagerException("缺少必要信息");
        }
    }

    @Override
    public List<Goods> queryAll() throws WareHouseManagerException {
        try {
            List<Goods> res = goodsMapper.queryAll();
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("查询失败");
        }
    }

    @Override
    public List<Goods> queryByCondition(Goods goodsCondition) throws WareHouseManagerException {
        isNull(goodsCondition);
        try {
            List<Goods> res = goodsMapper.queryByCondition(goodsCondition);
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("查询失败");
        }
    }

    @Override
    public int deleteGoods(Long goodsId) throws WareHouseManagerException {
        try {
            if (goodsId != null) {
                int res = goodsMapper.deleteGoods(goodsId);
                if (res == 0) {
                    throw new WareHouseManagerException("删除失败");
                }
                return res;
            } else {
                throw new WareHouseManagerException("不具备删除条件");
            }
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("删除失败");
        }
    }
    
    private void isNull(Goods goods) throws WareHouseManagerException {
        if (goods == null) {
            throw new WareHouseManagerException("传入信息为空");
        }
    }

    /**
     * 添加缩略图
     * 
     * @param goods
     * @param thumbnail
     */
    private void addThumbnail(Goods goods, ImageHolder thumbnail) {
        String dest = PathUtil.getGoodsImagePath(goods.getGoodsCategoryId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        goods.setGoodsPicture(thumbnailAddr);
    }
}
