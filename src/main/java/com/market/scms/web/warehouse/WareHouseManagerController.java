package com.market.scms.web.warehouse;

import com.market.scms.entity.Goods;
import com.market.scms.entity.GoodsCategory;
import com.market.scms.entity.staff.Function;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.service.*;
import com.market.scms.util.HttpServletRequestUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public Map<String,Object> insertStaff(HttpServletRequest request) {
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
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            Map<Integer, Object> functionMap =  new HashMap<>();
            for (Function function : functionList) {
                functionMap.put(function.getFunctionWeight(), function);
            }
            modelMap.put("success", true);
            modelMap.put("categoryList", categoryList);
            modelMap.put("goodsList", goodsList);
            modelMap.put("functionMap", functionMap);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }
    
}
