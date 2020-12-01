package com.market.scms.web.warehouse;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.*;
import com.market.scms.dto.ImageHolder;
import com.market.scms.entity.*;
import com.market.scms.entity.staff.Function;
import com.market.scms.enums.DeliveryStatusStateEnum;
import com.market.scms.enums.ExportBillStatusStateEnum;
import com.market.scms.enums.StocktakingAllStatusStateEnum;
import com.market.scms.enums.StocktakingStatusEnum;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.service.*;
import com.market.scms.util.DoubleUtil;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.StocktakingIdCreator;
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
import java.util.*;

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
    
    @Resource
    private DeliveryService deliveryService;
    
    @Resource
    private DeliveryRecordService deliveryRecordService;
    
    @Resource
    private RetailRecordService retailRecordService;
    
    @Resource
    private RetailService retailService;
    
    @Resource
    private StocktakingService stocktakingService;
    
    @Resource
    private StocktakingRecordService stocktakingRecordService;
    
    /**
     * 3.1库房管理员 查库存
     * 
     * @param request
     * @return
     */
    @PostMapping("/showinventory")
    @ResponseBody
    @RequiresPermissions("/showinventory")
    public Map<String,Object> showInventory(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        if (secondaryMenuId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备请求条件，请求失败");
            return modelMap;
        }
        try {
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll(); 
            List<Goods> goodsList2 = goodsService.queryAll();
            List<Goods> goodsList = goodsService.queryByCondition(new Goods(), pageIndex, pageSize);
            List<GoodsStockNum> goodsStockNumList = new ArrayList<>(goodsList.size());
            for (Goods goods : goodsList) {
                GoodsStockNum goodsStockNum = new GoodsStockNum();
                BeanUtils.copyProperties(goods, goodsStockNum);
                int inventory = 0;
                List<Stock> stockList = stockService.queryByGoodsId(goods.getGoodsId());
                for (Stock stock : stockList) {
                    inventory += stock.getStockInventory();
                }
                goodsStockNum.setStockInventoryNum(inventory);
                goodsStockNumList.add(goodsStockNum);
            }
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            modelMap.put("success", true);
            modelMap.put("categoryList", categoryList);
            modelMap.put("goodsStockNumList", goodsStockNumList);
            modelMap.put("recordSum", goodsList2.size());
            modelMap.put("functionList", functionList);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }
    
    /**
     * 3.2库房管理员 查库存 查看
     *
     * @param request
     * @return
     */
    @PostMapping("/showinventory/examine")
    @ResponseBody
    @RequiresPermissions("/showinventory/examine")
    public Map<String,Object> examine(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        Long goodsId = HttpServletRequestUtil.getLong(request, "goodsId");
        if (goodsId == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查看失败-01");
            return modelMap;
        }
        try {
            List<Unit> unitList = unitService.queryAll();
            List<Stock> stockList = stockService.queryByGoodsId(goodsId);
            modelMap.put("unitList", unitList);
            modelMap.put("stockList", stockList);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查看失败");
            return modelMap;
        }
        return modelMap;
    }
    
    /**
     * 3.2 修改库存
     * 
     * @param request
     * @return
     */
    @PostMapping("/showinventory/modifygoods")
    @ResponseBody
    @RequiresPermissions("/showinventory/modifygoods")
    public Map<String,Object> modifyGoods(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String stockStr = HttpServletRequestUtil.getString(request, "stock");
        Double stockGoodsPrice = HttpServletRequestUtil.getDouble(request, "stockGoodsPrice");
        if (stockStr == null || stockGoodsPrice == -1000d) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息有误");
            return modelMap;
        }
        Stock stock = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            stock = mapper.readValue(stockStr, Stock.class);
        } catch (Exception e) {
            
        }
        try {
            stock.setStockGoodsPrice(DoubleUtil.get(stockGoodsPrice));
            int res = stockService.update(stock);
            if (res == 0) {
                throw new WareHouseManagerException("更改失败");
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
     * 3.3 新货补充
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
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        if (staffId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "请求信息有误, 提交失败");
            return modelMap;
        }
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
                    throw new WareHouseManagerException("提交失败");
                }
                coupon.setCouponStaffId(staffId);
                res = couponService.insert(coupon);
                if (res == 0) {
                    throw new WareHouseManagerException("提交失败");
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
            Stock stock = stockService.queryById(stockGoodsId);
            Goods cur = new Goods();
            cur.setGoodsId(stock.getGoodsStockId());
            List<Goods> goodsList = goodsService.queryByCondition(cur, 0, 10);
            if (goodsList.size() == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
            Goods goods = goodsList.get(0);
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
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
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
                List<Stock> stockList = stockService.queryByGoodsId(goods.getGoodsId());
                int inventory = 0;
                for (Stock stock : stockList) {
                    inventory += stock.getStockGoodsBatchNumber();
                }
                goodsStockB.setStockInventory(inventory);
                goodsStockB.setGoodsCategoryName(categoryMap.get(goods.getGoodsCategoryId()));
                goodsStockB.setUnitName(unitMap.get(stockList.get(0).getStockUnitId()));
                goodsStockBList.add(goodsStockB);
            }
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            modelMap.put("functionList", functionList);
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
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        if (staffId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息有误,提交失败");
            return modelMap;
        }
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
            exportBill.setExportBillCouponId(coupon.getCouponId());
            exportBill.setExportConfirmStaffId(staffId);
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
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        if (secondaryMenuId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息有误，访问失败");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            List<Coupon> couponList = couponService.queryAll(pageIndex, pageSize);
            List<Coupon> couponList2 = couponService.queryAll(0, 10000);
            modelMap.put("functionList", functionList);
            modelMap.put("recordSum", couponList2.size());
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
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.10 采购入库单
     *
     * @param request
     * @return
     */
    @PostMapping("/purchaselist")
    @ResponseBody
    @RequiresPermissions("/purchaselist")
    public Map<String,Object> purchaseList(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        if (secondaryMenuId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，访问失败");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            List<ExportBill> exportBillList = exportBillService.queryAll(pageIndex, pageSize);
            List<ExportBill> exportBillList2 = exportBillService.queryAll(0, 10000);
            modelMap.put("functionList", functionList);
            modelMap.put("recordSum", exportBillList2.size());
            modelMap.put("success", true);
            modelMap.put("exportBillList", exportBillList);
            modelMap.put("exportBillCount", exportBillList.size());
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.11 采购入库单 查看
     *
     * @param request
     * @return
     */
    @PostMapping("/purchaselist/purchasedetails")
    @ResponseBody
    @RequiresPermissions("/purchaselist/purchasedetails")
    public Map<String,Object> purchasedetails(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String exportBillId = HttpServletRequestUtil.getString(request, "exportBillId");
        if (exportBillId == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "传入信息有误，查询失败");
            return modelMap;
        }
        try {
            ExportBill exportBill = exportBillService.queryByBillId(exportBillId);
            if (exportBill == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "传入信息有误，查询失败");
                return modelMap;
            }
            Coupon coupon = couponService.queryByCouponId(exportBill.getExportBillCouponId());
            if (coupon == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "传入信息有误，查询失败");
                return modelMap;
            }
            Goods goods = goodsService.queryById(coupon.getCouponGoodsId());
            if (goods == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "传入信息有误，查询失败");
                return modelMap;
            }
            GoodsCategory category = goodsCategoryService.queryById(goods.getGoodsCategoryId());
            Unit unit = unitService.queryById(coupon.getCouponUnitId());
            modelMap.put("exportBill", exportBill);
            modelMap.put("coupon", coupon);
            modelMap.put("goods", goods);
            modelMap.put("category", category);
            modelMap.put("unit", unit);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.12 采购入库单 查看 修改
     *
     * @param request
     * @return
     */
    @PostMapping("/purchaselist/purchasedetails/modify")
    @ResponseBody
    @RequiresPermissions("/purchaselist/purchasedetails/modify")
    public Map<String,Object> modify(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String exportBillStr = HttpServletRequestUtil.getString(request, "exportBill");
        ObjectMapper mapper = new ObjectMapper();
        ExportBill exportBill = null;
        try {
            exportBill = mapper.readValue(exportBillStr, ExportBill.class);
            if (exportBill == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "传入信息有误，修改失败");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息有误，修改失败");
            return modelMap;
        }
        try {
            int res = exportBillService.update(exportBill);
            if (res == 0) {
                throw new WareHouseManagerException("修改失败");
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
     * 3.13 采购入库单 确认（入库）
     *
     * @param request
     * @return
     */
    @PostMapping("/purchaselist/confirm")
    @ResponseBody
    @RequiresPermissions("/purchaselist/confirm")
    public Map<String,Object> confirm(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String exportBillId = HttpServletRequestUtil.getString(request, "exportBillId");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        if (exportBillId == null || staffId <= 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败");
            return modelMap;
        }
        try {
            ExportBill exportBill = exportBillService.queryByBillId(exportBillId);
            if (exportBill == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该入库单不存在");
                return modelMap;
            }
            exportBill.setExportBillStatus(ExportBillStatusStateEnum.TO_STOCK.getState());
            exportBill.setExportConfirmStaffId(staffId);
            int res = exportBillService.update(exportBill);
            if (res == 0) {
                throw new WareHouseManagerException("提交失败");
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
     * 3.14 采购入库单 拒收
     *
     * @param request
     * @return
     */
    @PostMapping("/purchaselist/rejection")
    @ResponseBody
    @RequiresPermissions("/purchaselist/rejection")
    public Map<String,Object> rejection(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String exportBillId = HttpServletRequestUtil.getString(request, "exportBillId");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        if (exportBillId == null || staffId <= 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败");
            return modelMap;
        }
        try {
            ExportBill exportBill = exportBillService.queryByBillId(exportBillId);
            if (exportBill == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该入库单不存在");
                return modelMap;
            }
            exportBill.setExportBillStatus(ExportBillStatusStateEnum.WAREHOUSE_FAILURE.getState());
            exportBill.setExportConfirmStaffId(staffId);
            int res = exportBillService.update(exportBill);
            if (res == 0) {
                throw new WareHouseManagerException("提交失败");
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
     * 3.15 批发出库单
     *
     * @param request
     * @return
     */
    @PostMapping("/wholesaledeliverylist")
    @ResponseBody
    @RequiresPermissions("/wholesaledeliverylist")
    public Map<String,Object> wholeSaleDeliveryList(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        if (secondaryMenuId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，访问失败");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
           List<DeliveryRecord> deliveryRecordList = deliveryRecordService.queryAll(pageIndex, pageSize);
           List<DeliveryRecord> deliveryRecordList2 = deliveryRecordService.queryAll(0, 10000);
           List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
           List<SupermarketStaff> supermarketStaffList = staffService
                   .queryStaffByCondition(new SupermarketStaff(), 0, 100);
           Map<Integer, String> staffMap = new HashMap<>(supermarketStaffList.size());
            for (SupermarketStaff staff : supermarketStaffList) {
                staffMap.put(staff.getStaffId(), staff.getStaffName());
            }
            modelMap.put("deliveryRecordList", deliveryRecordList);
            modelMap.put("functionList", functionList);
            modelMap.put("staffMap", staffMap);
            modelMap.put("recordSum", deliveryRecordList2.size());
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.16 批发出库单 确认出库
     *
     * @param request
     * @return
     */
    @PostMapping("/wholesaledeliverylist/confirmwarehousing")
    @ResponseBody
    @RequiresPermissions("/wholesaledeliverylist/confirmwarehousing")
    public Map<String,Object> confirmWarehousing(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        String deliveryId = HttpServletRequestUtil.getString(request, "deliveryId");
        if (staffId < 0 || deliveryId == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息有误，提交失败");
            return modelMap;
        }
        try {
            DeliveryRecord deliveryRecord = deliveryRecordService.queryByDeliveryId(deliveryId);
            if (deliveryRecord == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "传入信息有误，提交失败");
                return modelMap;
            }
            deliveryRecord.setDeliveryLaunchedStaffId(staffId);
            deliveryRecord.setDeliveryStatus(DeliveryStatusStateEnum.SUCCESS.getState());
            int res = deliveryRecordService.update(deliveryRecord);
            if (res == 0) {
                throw new WareHouseManagerException("提交失败");
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
     * 3.17 批发出库单 查看
     *
     * @param request
     * @return
     */
    @PostMapping("/wholesaledeliverylist/warehousingdetails")
    @ResponseBody
    @RequiresPermissions("/wholesaledeliverylist/warehousingdetails")
    public Map<String,Object> warehousingDetails(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String deliveryId = HttpServletRequestUtil.getString(request, "deliveryId");
        if (deliveryId == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息有误，查看失败");
            return modelMap;
        }
        try {
            List<Delivery> deliveryList = deliveryService.queryByDeliveryId(deliveryId);
            if (deliveryList == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "传入信息有误，查看失败");
                return modelMap;
            }
            List<DeliveryGoods> deliveryGoodsList = new ArrayList<>(deliveryList.size());
            for (Delivery delivery : deliveryList) {
                if (delivery == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "传入信息有误，查看失败");
                    return modelMap;
                }
                Stock stock = stockService.queryById(delivery.getDeliveryStockGoodsId());
                Goods goods = goodsService.queryById(stock.getGoodsStockId());
                if (stock == null || goods == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "数据有误，查看失败");
                    return modelMap;
                }
                GoodsCategory goodsCategory = goodsCategoryService.queryById(goods.getGoodsCategoryId());
                Unit unit = unitService.queryById(stock.getStockUnitId());
                DeliveryGoods deliveryGoods = new DeliveryGoods();
                BeanUtils.copyProperties(delivery, deliveryGoods);
                BeanUtils.copyProperties(stock, deliveryGoods);
                BeanUtils.copyProperties(goods, deliveryGoods);
                BeanUtils.copyProperties(goodsCategory, deliveryGoods);
                BeanUtils.copyProperties(unit, deliveryGoods);
                deliveryGoodsList.add(deliveryGoods);
            }
            modelMap.put("deliveryGoodsList", deliveryGoodsList);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查看失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.18库房管理员 批发出库单 查看 查看详情
     *
     * @param request
     * @return
     */
    @PostMapping("/wholesaledeliverylist/warehousinggoodsdetails")
    @ResponseBody
    @RequiresPermissions("/wholesaledeliverylist/warehousinggoodsdetails")
    public Map<String,Object> wareHousingGoodsDetails(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String deliveryId = HttpServletRequestUtil.getString(request, "deliveryId");
        Long deliveryStockGoodsId = HttpServletRequestUtil.getLong(request, "deliveryStockGoodsId");
        if (deliveryId == null || deliveryStockGoodsId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息不足，查看失败");
            return modelMap;
        }
        try {
            Delivery delivery = deliveryService.queryByGoodsId(deliveryId, deliveryStockGoodsId);
            DeliveryRecord deliveryRecord = deliveryRecordService.queryByDeliveryId(deliveryId);
            if (delivery == null || deliveryRecord == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该单不存在");
                return modelMap;
            }
            SupermarketStaff staff = staffService.queryById(deliveryRecord.getDeliveryLaunchedStaffId());
            Stock stock = stockService.queryById(deliveryStockGoodsId);
            Goods goods = goodsService.queryById(stock.getGoodsStockId());
            GoodsCategory category = goodsCategoryService.queryById(goods.getGoodsCategoryId());
            Unit unit = unitService.queryById(stock.getStockUnitId());
            System.out.println("no 3------------");
            modelMap.put("success", true);
            modelMap.put("staff", staff);
            modelMap.put("goods", goods);
            modelMap.put("category", category);
            modelMap.put("unit", unit);
            modelMap.put("delivery", delivery);
            modelMap.put("deliveryRecord", deliveryRecord);
            modelMap.put("stock", stock);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查看失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.19库房管理员 零售出库单
     *
     * @param request
     * @return
     */
    @PostMapping("/retaildeliverylist")
    @ResponseBody
    @RequiresPermissions("/retaildeliverylist")
    public Map<String,Object> retailDeliveryList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        if (secondaryMenuId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，访问失败");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            List<RetailRecord> retailRecordList = retailRecordService.queryAll(pageIndex, pageSize);
            List<RetailRecord> retailRecordList2 = retailRecordService.queryAll(0, 10000);
            List<SupermarketStaff> staffList = staffService
                    .queryStaffByCondition(new SupermarketStaff(), 0, 10000);
            modelMap.put("functionList", functionList);
            modelMap.put("staffList", staffList);
            modelMap.put("retailRecordList", retailRecordList);
            modelMap.put("recordSum", retailRecordList2.size());
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "访问失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.20库房管理员 零售出库单 查看
     *
     * @param request
     * @return
     */
    @PostMapping("/retaildeliverylist/retaildetails")
    @ResponseBody
    @RequiresPermissions("/retaildeliverylist/retaildetails")
    public Map<String,Object> retaildetails(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String retailId = HttpServletRequestUtil.getString(request, "retailId");
        if (request == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，访问失败");
            return modelMap;
        }
        try {
            List<Retail> retailList = retailService.queryByRetailId(retailId);
            modelMap.put("retailList", retailList);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查看失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.21库房管理员 零售出库单 查看 查看详情
     *
     * @param request
     * @return
     */
    @PostMapping("/retaildeliverylist/retailgoodsdetails")
    @ResponseBody
    @RequiresPermissions("/retaildeliverylist/retailgoodsdetails")
    public Map<String,Object> retailGoodsDetails(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String retailId = HttpServletRequestUtil.getString(request, "retailId");
        Long retailStockGoodsId = HttpServletRequestUtil.getLong(request, "retailStockGoodsId");
        if (retailId == null || retailStockGoodsId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，查看失败");
            return modelMap;
        }
        try {
            System.out.println("开始查询");
            Retail retail = retailService.queryByGoodsId(retailId, retailStockGoodsId);
            RetailRecord retailRecord = retailRecordService.queryByRetailId(retailId);
            if (retail == null || retailRecord == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该批发单不存在，查看失败");
                return modelMap;
            }
            Stock stock = stockService.queryById(retailStockGoodsId);
            SupermarketStaff staff = staffService.queryById(retailRecord.getRetailCollectionStaffId());
            Goods goods = goodsService.queryById(stock.getGoodsStockId());
            GoodsCategory category = goodsCategoryService.queryById(goods.getGoodsCategoryId());
            Unit unit = unitService.queryById(stock.getStockUnitId());
            System.out.println("都过了");
            modelMap.put("retail", retail);
            modelMap.put("retailRecord", retailRecord);
            modelMap.put("stock", stock);
            modelMap.put("staff", staff);
            modelMap.put("goods", goods);
            modelMap.put("category", category);
            modelMap.put("unit", unit);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查看失败");
            return modelMap;
        }
        return modelMap;
    }
    
    /**
     * 3.25库房管理员订货请求
     *
     * @param request
     * @return
     */
    @PostMapping("/purchase/order")
    @ResponseBody
    @RequiresPermissions("/purchase/order")
    public Map<String,Object> purchaseOrder(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String couponStr = HttpServletRequestUtil.getString(request, "coupon");
        Coupon coupon = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            coupon = objectMapper.readValue(couponStr, Coupon.class);
            if (coupon == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败-01");
            return modelMap;
        }
        try {
            int res = couponService.insert(coupon);
            if (res == 0) {
                throw new WareHouseManagerException("提交失败");
            }
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.26库房管理员补货请求
     *
     * @param request
     * @return
     */
    @PostMapping("/purchase/replenish")
    @ResponseBody
    @RequiresPermissions("/purchase/replenish")
    public Map<String,Object> purchaseReplenish(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String couponStr = HttpServletRequestUtil.getString(request, "coupon");
        Coupon coupon = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            coupon = objectMapper.readValue(couponStr, Coupon.class);
            if (coupon == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "补货失败-01");
            return modelMap;
        }
        try {
            int res = couponService.insert(coupon);
            if (res == 0) {
                throw new WareHouseManagerException("补货失败");
            }
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "补货失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.27订货单模糊查询
     *
     * @param request
     * @return
     */
    @PostMapping("/purchase/queryOrder")
    @ResponseBody
    @RequiresPermissions("/purchase/queryOrder")
    public Map<String,Object> purchaseQueryOrder(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String couponStr = HttpServletRequestUtil.getString(request, "coupon");
        Coupon coupon = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            coupon = objectMapper.readValue(couponStr, Coupon.class);
            if (coupon == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败-01");
            return modelMap;
        }
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<Coupon> couponList = couponService.queryByCondition(coupon, pageIndex, pageSize);
            modelMap.put("couponList", couponList);         
            modelMap.put("couponListCount", couponList.size());         
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败 ");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.28入库单模糊查询
     *
     * @param request
     * @return
     */
    @PostMapping("/purchase/queryExportBill")
    @ResponseBody
    @RequiresPermissions("/purchase/queryExportBill")
    public Map<String,Object> purchaseQueryExportBill(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String exportBillStr = HttpServletRequestUtil.getString(request, "exportBill");
        ExportBill exportBill = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            exportBill = objectMapper.readValue(exportBillStr, ExportBill.class);
            if (exportBill == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询失败-01");
            return modelMap;
        }
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<ExportBill> billList = exportBillService.queryByCondition(exportBill, pageIndex, pageSize);
            modelMap.put("billList", billList);
            modelMap.put("billListCount", billList.size());
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败 ");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.29入库单信息填写
     *
     * @param request
     * @return
     */
    @PostMapping("/purchase/insertExportBill")
    @ResponseBody
    @RequiresPermissions("/purchase/insertExportBill")
    public Map<String,Object> purchaseInsertExportBill(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String exportBillStr = HttpServletRequestUtil.getString(request, "exportBill");
        ExportBill exportBill = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            exportBill = objectMapper.readValue(exportBillStr, ExportBill.class);
            if (exportBill == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "提交失败-01");
            return modelMap;
        }
        try {
            int res = exportBillService.update(exportBill);
            if (res == 0) {
                throw new WareHouseManagerException("提交失败");
            }
            List<ExportBill> billList = exportBillService.queryAll(0, 10000);
            modelMap.put("billList", billList);
            modelMap.put("billListCount", billList.size());
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.29入库单信息填写
     *
     * @param request
     * @return
     */
    @PostMapping("/purchase/updateBillState")
    @ResponseBody
    @RequiresPermissions("/purchase/updateBillState")
    public Map<String,Object> purchaseUpdateBillState(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String exportBillStr = HttpServletRequestUtil.getString(request, "exportBill");
        ExportBill exportBill = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            exportBill = objectMapper.readValue(exportBillStr, ExportBill.class);
            if (exportBill == null || exportBill.getExportBillCouponId() == null || exportBill.getExportBillStatus() == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "提交失败-01");
            return modelMap;
        }
        try {
            ExportBill cur = new ExportBill();
            exportBill.setExportBillCouponId(exportBill.getExportBillCouponId());
            exportBill.setExportBillStatus(exportBill.getExportBillStatus());
            int res = exportBillService.update(cur);
            if (res == 0) {
                throw new WareHouseManagerException("更改失败");
            }
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 6.1库房管理员 盘点管理
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking")
    @ResponseBody
    @RequiresPermissions("/stocktaking")
    public Map<String,Object> stocktaking(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        if (secondaryMenuId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，访问失败");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            List<SupermarketStaff> staffList = staffService
                    .queryStaffByCondition(new SupermarketStaff(), pageIndex, pageSize);
            List<StocktakingRecord> stocktakingRecordList2 = stocktakingRecordService.queryAll(0, 10000);
            List<StocktakingRecord> stocktakingRecordList = stocktakingRecordService.queryAll(pageIndex, pageSize);
            modelMap.put("recordSum", stocktakingRecordList2.size());
            modelMap.put("stocktakingRecordList", stocktakingRecordList);
            modelMap.put("functionList", functionList);
            modelMap.put("staffList", staffList);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 6.2库房管理员 盘点管理 查看
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/stocktakinggoodslist")
    @ResponseBody
    @RequiresPermissions("/stocktaking/stocktakinggoodslist")
    public Map<String,Object> stocktakingGoodsList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        Long stocktakingId = HttpServletRequestUtil.getLong(request, "stocktakingId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (stocktakingId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败-01");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            Stocktaking cur = new Stocktaking();
            cur.setStocktakingId(stocktakingId);
            List<Stocktaking> stocktakingList = stocktakingService.queryByCondition(cur, pageIndex, pageSize);
            List<Stocktaking> stocktakingList2 = stocktakingService.queryByCondition(cur, 0, 10000);
            List<StockingGoods> stockingGoodsList = new ArrayList<>(stocktakingList.size());
            for (Stocktaking stocktaking : stocktakingList) {
                StockingGoods stockingGoods = new StockingGoods();
                Stock stock = stockService.queryById(stocktaking.getStocktakingStockGoodsId());
                Goods goods = goodsService.queryById(stock.getGoodsStockId());
                GoodsCategory category = goodsCategoryService.queryById(goods.getGoodsCategoryId());
                BeanUtils.copyProperties(stocktaking, stockingGoods);
                BeanUtils.copyProperties(goods, stockingGoods);
                BeanUtils.copyProperties(stock, stockingGoods);
                BeanUtils.copyProperties(category, stockingGoods);
                stockingGoodsList.add(stockingGoods);
            }
            modelMap.put("stockingGoodsList", stockingGoodsList);
            modelMap.put("recordSum", stocktakingList2.size());
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 6.3库房管理员 盘点管理 查看 查看详情
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/stocktakinggoodsdetails")
    @ResponseBody
    @RequiresPermissions("/stocktaking/stocktakinggoodsdetails")
    public Map<String,Object> stocktakingGoodsDetails(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        Long stocktakingId = HttpServletRequestUtil.getLong(request, "stocktakingId");
        Long stocktakingStockGoodsId = HttpServletRequestUtil.getLong(request, "stocktakingStockGoodsId");
        if (stocktakingId < 0 || stocktakingStockGoodsId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败-01");
            return modelMap;
        }
        try {
            Stocktaking stocktaking = stocktakingService.queryById(stocktakingId, stocktakingStockGoodsId);
            if (stocktaking == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该盘点单不存在");
                return modelMap;
            }
            SupermarketStaff staff = staffService.queryById(stocktaking.getStocktakingStaffId());
            Stock stock = stockService.queryById(stocktakingStockGoodsId);
            Goods goods = goodsService.queryById(stock.getGoodsStockId());
            GoodsCategory category = goodsCategoryService.queryById(goods.getGoodsCategoryId());
            Unit unit = unitService.queryById(stock.getStockUnitId());
            modelMap.put("stocktaking", stocktaking);
            modelMap.put("staff", staff);
            modelMap.put("stock", stock);
            modelMap.put("goods", goods);
            modelMap.put("category", category);
            modelMap.put("unit", unit);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 6.4库房管理员 盘点管理 查看 修改
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/stocktakinggoodsremind")
    @ResponseBody
    @RequiresPermissions("/stocktaking/stocktakinggoodsremind")
    public Map<String,Object> stocktakingGoodsRemind(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String stocktakingStr = HttpServletRequestUtil.getString(request, "stocktaking");
        Stocktaking stocktaking = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            stocktaking = mapper.readValue(stocktakingStr, Stocktaking.class);
            if (stocktaking == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "修改失败-01");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "修改失败-01");
            return modelMap;
        }
        try {
            int res = stocktakingService.update(stocktaking);
            if (res == 0) {
                throw new WareHouseManagerException("修改失败");
            }
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "修改失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 6.5库房管理员 盘点设置（ 职工信息查询和职工盘点类别）
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/viewStocktakingRules")
    @ResponseBody
    @RequiresPermissions("/stocktaking/viewStocktakingRules")
    public Map<String,Object> viewStocktakingRules(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        try {
            List<SupermarketStaff> staffList = staffService.queryStaffByCondition(new SupermarketStaff(), 0, 10000);
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll();
            modelMap.put("staffList", staffList);
            modelMap.put("categoryList", categoryList);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "访问失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 6.6库房管理员 盘点设置 确认修改（修改职工盘点类别规则）
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/modifyStocktakingRules")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/stocktaking/modifyStocktakingRules")
    public Map<String,Object> modifyStocktakingRules(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String categoryListStr = HttpServletRequestUtil.getString(request, "categoryList");
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, GoodsCategory.class);
        List<GoodsCategory> goodsCategoryList = null;
        try {
            goodsCategoryList = mapper.readValue(categoryListStr, javaType);
            if (goodsCategoryList == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "修改失败-01");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "修改失败-01");
            return modelMap;
        }
        try {
            for (GoodsCategory category : goodsCategoryList) {
                int res = goodsCategoryService.update(category);
                if (res == 0) {
                    throw new WareHouseManagerException("修改失败");
                }
            }
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "修改失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 6.7库房管理员 盘点管理 选取需盘点商品列表
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/selectStocktakingGoods")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/stocktaking/selectStocktakingGoods")
    public Map<String,Object> selectStocktakingGoods(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll();
            List<Stock> stockList = stockService.queryAll(pageIndex, pageSize);
            List<Stock> stockList2 = stockService.queryAll(0, 10000);
            List<GoodsStockA> goodsStockAList = new ArrayList<>(stockList.size());
            for (Stock stock : stockList) {
                GoodsStockA goodsStockA = new GoodsStockA();
                Goods goods = goodsService.queryById(stock.getGoodsStockId());
                BeanUtils.copyProperties(stock, goodsStockA);
                BeanUtils.copyProperties(goods, goodsStockA);
                goodsStockAList.add(goodsStockA);
            }
            modelMap.put("goodsStockAList", goodsStockAList);
            modelMap.put("recordSum", stockList2.size());
            modelMap.put("categoryList", categoryList);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 6.8库房管理员 盘点管理 发起盘点
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/initiateStocktaking")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/stocktaking/initiateStocktaking")
    public Map<String,Object> initiateStocktaking(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        String stockGoodsIdListStr = HttpServletRequestUtil.getString(request, "stockGoodsIdListStr");
        if (staffId <= 0 || stockGoodsIdListStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "发起失败-01");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Long.class);
        List<Long> stockGoodsIdList = null;
        try {
            stockGoodsIdList = mapper.readValue(stockGoodsIdListStr, javaType);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "发起失败-01");
            return modelMap;
        }
        try {
            int count = stocktakingRecordService.queryStocktakingCount(StocktakingAllStatusStateEnum.START.getState());
            if (count > 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "发起盘点失败-有其他盘点操作正在进行中，请稍后。。。");
                return modelMap;
            }
            Long stocktakingId = StocktakingIdCreator
                    .get(stocktakingService.getCount(StocktakingIdCreator.getDateString()));
            StocktakingRecord stocktakingRecord = new StocktakingRecord();
            stocktakingRecord.setStocktakingId(stocktakingId);
            stocktakingRecord.setStocktakingLaunchedStaffId(Long.valueOf(staffId));
            stocktakingRecord.setStocktakingAllStatus(StocktakingAllStatusStateEnum.START.getState());
            stocktakingRecord.setStocktakingLaunchedDate(new Date());          
            int res = stocktakingRecordService.insert(stocktakingRecord);
            if (res == 0) {
                throw new WareHouseManagerException("发起失败");
            }
            for (Long stockGoodsId : stockGoodsIdList) {
                Stock stock = stockService.queryById(stockGoodsId);
                Goods goods = goodsService.queryById(stock.getGoodsStockId());
                GoodsCategory category = goodsCategoryService.queryById(goods.getGoodsCategoryId());
                Stocktaking stocktaking = new Stocktaking();
                stocktaking.setStocktakingId(stocktakingId);
                stocktaking.setStocktakingStockGoodsId(stockGoodsId);
                stocktaking.setStockNum(stock.getStockGoodsBatchNumber());
                stocktaking.setStocktakingStaffId(category.getStocktakingStaffId());
                stocktaking.setStocktakingStatus(StocktakingStatusEnum.START.getState());
                stocktaking.setStocktakingPrice(stock.getStockGoodsPrice());
                res = stocktakingService.insert(stocktaking);
                if (res == 0) {
                    throw new WareHouseManagerException("发起失败"); 
                }
            }
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "发起失败");
            return modelMap;    
        }
        return modelMap;
    }

    /**
     * 6.12库房管理员 盘点管理 查看 库房管理员提交总盘点
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/submitStocktaking")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/stocktaking/submitStocktaking")
    public Map<String,Object> submitStocktaking(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        Long stocktakingId = HttpServletRequestUtil.getLong(request, "stocktakingId");
        if (stocktakingId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "盘点提交失败-01");
            return modelMap;
        }
        try {
            StocktakingRecord stocktakingRecord = stocktakingRecordService.queryById(stocktakingId);
            if (stocktakingRecord == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该单号不存在，盘点提交失败");
                return modelMap;
            }
            List<Stocktaking> stocktakingList = stocktakingService.queryByStocktakingId(stocktakingId);
            Double money = 0.0;
            for (Stocktaking stocktaking : stocktakingList) {
                if (stocktaking.getStocktakingProfitLossStatus() == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "盘盈亏状态未填完，盘点提交失败");
                    return modelMap;
                }
                money += (stocktaking.getStockNum() - stocktaking.getStocktakingNum())
                        * stocktaking.getStocktakingPrice();
                stocktaking.setStocktakingStatus(StocktakingStatusEnum.FINISH.getState());
                int res = stocktakingService.update(stocktaking);
                if (res == 0) {
                    throw new WareHouseManagerException("提交失败");
                }
            }
            stocktakingRecord.setStocktakingProfitLossPrice(money);
            stocktakingRecord.setStocktakingCommitDate(new Date());
            stocktakingRecord.setStocktakingAllStatus(StocktakingAllStatusStateEnum.FINISH.getState());
            int res = stocktakingRecordService.update(stocktakingRecord);
            if (res == 0) {
                throw new WareHouseManagerException("提交失败");
            }
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "盘点提交失败");
            return modelMap;
        }
        return modelMap;
    }
}
