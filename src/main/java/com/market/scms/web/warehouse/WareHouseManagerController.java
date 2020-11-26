package com.market.scms.web.warehouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.DeliveryGoods;
import com.market.scms.bean.GoodsStockA;
import com.market.scms.bean.GoodsStockB;
import com.market.scms.dto.ImageHolder;
import com.market.scms.entity.*;
import com.market.scms.entity.staff.Function;
import com.market.scms.enums.DeliveryStatusStateEnum;
import com.market.scms.enums.ExportBillStatusStateEnum;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.service.*;
import com.market.scms.util.DoubleUtil;
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
    
    @Resource
    private DeliveryService deliveryService;
    
    @Resource
    private DeliveryRecordService deliveryRecordService;
    
    @Resource
    private RetailRecordService retailRecordService;
    
    @Resource
    private RetailService retailService;
    
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
            List<Goods> goodsList = goodsService.queryByCondition(new Goods(), pageIndex, pageSize);
            List<Goods> goodsList2 = goodsService.queryByCondition(new Goods(), 0, 10000);
            List<GoodsStockA> goodsStockAList = new ArrayList<>(goodsList.size());
            for (Goods goods : goodsList) {
                GoodsStockA goodsStockA = new GoodsStockA();
                Stock stock = stockService.queryByGoodsId(goods.getGoodsId());
                BeanUtils.copyProperties(goods, goodsStockA);
                BeanUtils.copyProperties(stock, goodsStockA);
                goodsStockAList.add(goodsStockA);
            }
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            modelMap.put("success", true);
            modelMap.put("categoryList", categoryList);
            modelMap.put("goodsStockAList", goodsStockAList);
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
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "补货失败");
                    return modelMap;
                }
                coupon.setCouponStaffId(staffId);
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
                Stock stock = stockService.queryByGoodsId(goods.getGoodsId());
                BeanUtils.copyProperties(stock, goodsStockB);
                goodsStockB.setGoodsCategoryName(categoryMap.get(goods.getGoodsCategoryId()));
                goodsStockB.setUnitName(unitMap.get(stock.getStockUnitId()));
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
                modelMap.put("success",false);
                modelMap.put("errMsg", "修改失败");
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
                modelMap.put("success",false);
                modelMap.put("errMsg", "提交失败");
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
                modelMap.put("success",false);
                modelMap.put("errMsg", "提交失败");
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
                modelMap.put("success",false);
                modelMap.put("errMsg", "提交入库失败");
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
                Stock stock = stockService.queryByGoodsId(delivery.getDeliveryStockGoodsId());
                Goods goods = goodsService.queryById(delivery.getDeliveryStockGoodsId());
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
            Stock stock = stockService.queryByGoodsId(deliveryStockGoodsId);
            Goods goods = goodsService.queryById(deliveryStockGoodsId);
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
            Retail retail = retailService.queryByGoodsId(retailId, retailStockGoodsId);
            if (retail == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该批发单不存在，查看失败");
                return modelMap;
            }
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
}
