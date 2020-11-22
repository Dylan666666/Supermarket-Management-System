package com.market.scms.web.warehouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.GoodsStockA;
import com.market.scms.bean.GoodsStockB;
import com.market.scms.dto.ImageHolder;
import com.market.scms.entity.*;
import com.market.scms.entity.staff.Function;
import com.market.scms.enums.ExportBillStatusStateEnum;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.service.*;
import com.market.scms.util.DoubleUtil;
import com.market.scms.util.ExportBillIdCreator;
import com.market.scms.util.HttpServletRequestUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    
    @Resource
    private StaffService staffService;
    
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

    /**
     * 新货补充
     *
     * @param request
     * @return
     */
    @PostMapping("/showinventory/newgoods")
    @ResponseBody
    @RequiresPermissions("/showinventory/newgoods")
    public Map<String,Object> newGoods(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        try {
            List<Unit> unitList = unitService.queryAll();
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll();
            modelMap.put("unitList", unitList);
            modelMap.put("categoryList", categoryList);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.4新货补充提交
     *
     * @param request
     * @return
     */
    @PostMapping("/showinventory/newgoodscommit")
    @ResponseBody
    @RequiresPermissions("/showinventory/newgoodscommit")
    public Map<String,Object> newGoodsCommit(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String goodsStr = HttpServletRequestUtil.getString(request, "goods");
        String couponStr = HttpServletRequestUtil.getString(request, "coupon");
        ObjectMapper mapper = new ObjectMapper();
        Goods goods = null;
        Coupon coupon = null;
        try {
            goods = mapper.readValue(goodsStr, Goods.class);
            coupon = mapper.readValue(couponStr, Coupon.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "请求信息有误");
            return modelMap;
        }
        if (goods != null && coupon != null) {
            ImageHolder thumbnail = null;
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //若请求种有文件流，则取出相关的文件(包括缩略图和详情图）
            try {
                if (multipartResolver.isMultipart(request)) {
                    thumbnail = handleImage(request, thumbnail);
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
            try {
                int res = goodsService.insertGoods(goods, thumbnail);
                if (res == 0) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "补货失败");
                    return modelMap;
                }
                res = couponService.insert(coupon);
                if (res == 0) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "补货失败");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (WareHouseManagerException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "请求信息有误");
            return modelMap;
        }
        return modelMap;
    }

    private ImageHolder handleImage(HttpServletRequest request,ImageHolder thumbnail) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        //取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest
                .getFile("goodsPicture");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
        }
        return thumbnail;
    }

    /**
     * 3.5 商品详情查看
     *
     * @param request
     * @return
     */
    @PostMapping("/showinventory/goodsdetails")
    @ResponseBody
    @RequiresPermissions("/showinventory/goodsdetails")
    public Map<String,Object> goodsDetails(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        Long stockGoodsId = HttpServletRequestUtil.getLong(request, "stockGoodsId");
        if (stockGoodsId == -1000L) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "请求有误");
            return modelMap;
        }
        try {
            Goods cur = new Goods();
            cur.setGoodsId(stockGoodsId);
            List<Goods> goodsList = goodsService.queryByCondition(cur, 0, 10);
            if (goodsList.size() == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
            Goods goods = goodsList.get(0);
            Stock stock = stockService.queryByGoodsId(stockGoodsId);
            if (stock == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
            GoodsStockA goodsStockA = new GoodsStockA();
            BeanUtils.copyProperties(goods, goodsStockA);
            BeanUtils.copyProperties(stock, goodsStockA);
            modelMap.put("goodsStockA", goodsStockA);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.6 补货申请
     *
     * @param request
     * @return
     */
    @PostMapping("/replenishmentapplication")
    @ResponseBody
    @RequiresPermissions("/replenishmentapplication")
    public Map<String,Object> replenishmentApplication(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        try {
            List<Goods> goodsList = goodsService.queryAll();
            List<GoodsCategory> goodsCategoryList = goodsCategoryService.queryAll();
            Map<Integer, String> categoryMap = new HashMap<>(goodsCategoryList.size());
            for (GoodsCategory category : goodsCategoryList) {
                categoryMap.put(category.getCategoryId(), category.getCategoryName());
            }
            List<Unit> unitList = unitService.queryAll();
            Map<Integer, String> unitMap = new HashMap<>(unitList.size());
            for (Unit unit : unitList) {
                unitMap.put(unit.getUnitId(), unit.getUnitName());
            }
            List<GoodsStockB> goodsStockBList = new ArrayList<>(goodsList.size());
            for (Goods goods : goodsList) {
                GoodsStockB goodsStockB = new GoodsStockB();
                BeanUtils.copyProperties(goods, goodsStockB);
                Stock stock = stockService.queryByGoodsId(goods.getGoodsId());
                BeanUtils.copyProperties(stock, goodsStockB);
                goodsStockB.setGoodsCategoryName(categoryMap.get(goods.getGoodsCategoryId()));
                goodsStockB.setUnitName(unitMap.get(stock.getStockUnitId()));
                goodsStockBList.add(goodsStockB);
            }
            modelMap.put("goodsStockBList", goodsStockBList);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "初始化失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.7 补货提交
     *
     * @param request
     * @return
     */
    @PostMapping("/replenishmentapplication/replenishmentcommit")
    @ResponseBody
    @RequiresPermissions("/replenishmentapplication/replenishmentcommit")
    @Transactional
    public Map<String,Object> replenishmentCommit(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String couponStr = HttpServletRequestUtil.getString(request, "coupon");
        ObjectMapper mapper = new ObjectMapper();
        Coupon coupon = null;
        try {
            coupon = mapper.readValue(couponStr, Coupon.class);
            if (coupon == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "传入信息有误");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息有误");
            return modelMap;
        }
        try {
            couponService.insert(coupon);
            ExportBill exportBill = new ExportBill();
            exportBillService.insert(exportBill, coupon.getCouponGoodsId());
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.8 订单信息
     *
     * @param request
     * @return
     */
    @PostMapping("/orderInformation")
    @ResponseBody
    @RequiresPermissions("/orderInformation")
    public Map<String,Object> orderInformation(HttpServletRequest request) {
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
            List<Coupon> couponList = couponService.queryAll(pageIndex, pageSize);
            modelMap.put("couponList", couponList);
            modelMap.put("couponCount", couponList.size());
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.9 订单信息 查看(订单详细信息)
     *
     * @param request
     * @return
     */
    @PostMapping("/orderInformation/orderdetails")
    @ResponseBody
    @RequiresPermissions("/orderInformation/orderdetails")
    public Map<String,Object> orderdetails(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        Long couponId = HttpServletRequestUtil.getLong(request, "couponId");
        try {
            Coupon coupon = couponService.queryByCouponId(couponId);
            if (coupon == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
            Goods goods = goodsService.queryById(coupon.getCouponGoodsId());
            Unit unit = unitService.queryById(coupon.getCouponUnitId());
            SupermarketStaff staff = staffService.queryById(coupon.getCouponStaffId()); 
            if (goods == null || unit == null || staff == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
            GoodsCategory goodsCategory = goodsCategoryService.queryById(goods.getGoodsCategoryId());
            modelMap.put("goods", goods);
            modelMap.put("coupon", coupon);
            modelMap.put("staff", staff);
            modelMap.put("unit", unit);
            modelMap.put("category", goodsCategory);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }
}