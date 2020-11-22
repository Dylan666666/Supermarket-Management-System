package com.market.scms.web.warehouse;

import com.market.scms.bean.GoodsStockA;
import com.market.scms.entity.Goods;
import com.market.scms.entity.GoodsCategory;
import com.market.scms.entity.Stock;
import com.market.scms.entity.staff.Function;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.service.*;
import com.market.scms.util.DoubleUtil;
import com.market.scms.util.HttpServletRequestUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/19 9:04
 */
@CrossOrigin
@RestController
public class WareHouseManagerController {
    @Resource
    private GoodsService goodsService;
    
    @Resource
    private UnitService unitService;
    
    @Resource
    private GoodsCategoryService goodsCategoryService;
    
    @Resource
    private CouponService couponService;
    
    @Resource
    private ExportBillService exportBillService;
    
    @Resource
    private StockService stockService;
    
    @Resource
    private FunctionService functionService;

    @Resource
    private SecondaryMenuService secondaryMenuService;
    
    @PostMapping("/showinventory")
    @ResponseBody
    @RequiresPermissions("/showinventory")
    public Map<String,Object> showInventory(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 100;
        }
        try {
            int secondaryMenuId = secondaryMenuService.queryByUrl("/showinventory").getSecondaryMenuId();
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll(); 
            List<Goods> goodsList = goodsService.queryByCondition(new Goods(), pageIndex, pageSize);
            List<GoodsStockA> goodsStockAList = new ArrayList<>(goodsList.size());
            for (Goods goods : goodsList) {
                GoodsStockA goodsStockA = new GoodsStockA();
                Stock stock = stockService.queryByGoodsId(goods.getGoodsId());
                BeanUtils.copyProperties(goods, goodsStockA);
                BeanUtils.copyProperties(stock, goodsStockA);
                goodsStockAList.add(goodsStockA);
            }
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
//            Map<Integer, Object> functionMap =  new HashMap<>();
//            for (Function function : functionList) {
//                functionMap.put(function.getFunctionWeight(), function);
//            }
            modelMap.put("success", true);
            modelMap.put("categoryList", categoryList);
            modelMap.put("goodsStockAList", goodsStockAList);
            modelMap.put("functionList", functionList);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 修改库存
     * 
     * @param request
     * @return
     */
    @PostMapping("/showinventory/modifygoods")
    @ResponseBody
    @RequiresPermissions("/showinventory/modifygoods")
    public Map<String,Object> modifyGoods(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        Long stockGoodsId = HttpServletRequestUtil.getLong(request, "stockGoodsId");
        Double stockGoodsPrice = HttpServletRequestUtil.getDouble(request, "stockGoodsPrice");
        if (stockGoodsId == -1000L || stockGoodsPrice == -1000d) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息有误");
            return modelMap;
        }
        try {
            Stock stock = new Stock();
            stock.setStockGoodsId(stockGoodsId);
            stock.setStockGoodsPrice(DoubleUtil.get(stockGoodsPrice));
            int res = stockService.update(stock);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "更改失败");
                return modelMap;
            }
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }
    
}
