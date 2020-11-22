package com.market.scms.service.impl;

import com.market.scms.entity.GoodsCategory;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.GoodsCategoryMapper;
import com.market.scms.service.GoodsCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 16:27
 */
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
    
    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;
    
    @Override
    public List<GoodsCategory> queryAll() throws WareHouseManagerException {
        try {
            List<GoodsCategory> res = goodsCategoryMapper.queryAll();
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("查询产品类别失败");
        }
    }

    @Override
    public int insert(GoodsCategory goodsCategory) throws WareHouseManagerException {
        if (goodsCategory == null && goodsCategory.getCategoryId() != null &&
        goodsCategory.getCategoryName() != null) {
            throw new WareHouseManagerException("传入信息不完整");
        } else {
            try {
                int res = goodsCategoryMapper.insert(goodsCategory);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加产品类别失败");
            }   
        }
    }

    @Override
    public GoodsCategory queryById(int categoryId) throws WareHouseManagerException {
        if (categoryId > 0) {
            GoodsCategory goodsCategory = goodsCategoryMapper.queryById(categoryId);
            return goodsCategory;
        } else {
            throw new WareHouseManagerException("查询产品类别失败");
        }
    }
}
