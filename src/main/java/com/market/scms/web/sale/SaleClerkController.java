package com.market.scms.web.sale;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.DeliveryGoods;
import com.market.scms.bean.DeliveryGoodsReturn;
import com.market.scms.bean.GoodsStockA;
import com.market.scms.entity.*;
import com.market.scms.entity.staff.Function;
import com.market.scms.enums.DeliveryRefundStatusStateEnum;
import com.market.scms.enums.DeliveryStatusStateEnum;
import com.market.scms.enums.RetailRefundStatusStateEnum;
import com.market.scms.exceptions.SaleException;
import com.market.scms.service.*;
import com.market.scms.util.DeliveryIdCreator;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.RetailRecordIdCreator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/27 19:01
 */
@CrossOrigin
@RestController
public class SaleClerkController {

    @Resource
    private StaffService staffService;

    @Resource
    private PrimaryMenuService primaryMenuService;

    @Resource
    private SecondaryMenuService secondaryMenuService;

    @Resource
    private FunctionService functionService;

    @Resource
    private StaffJurisdictionService staffJurisdictionService;

    @Resource
    private StaffPositionRelationService staffPositionRelationService;

    @Resource
    private StaffPositionService staffPositionService;

    @Resource
    private ExportBillService exportBillService;

    @Resource
    private CouponService couponService;

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @Resource
    private UnitService  unitService;

    @Resource
    private GoodsService goodsService;
    
    @Resource
    private StockService stockService;
    
    @Resource
    private DeliveryRecordService deliveryRecordService;
    
    @Resource
    private DeliveryService deliveryService;
    
    @Resource
    private RetailService retailService;
    
    @Resource
    private RetailRecordService retailRecordService;
    
    
    
    /**
     * 4.1营业员 批发收银
     *
     * @param request
     * @return
     */
    @PostMapping("/deliverycashier")
    @ResponseBody
    @RequiresPermissions("/deliverycashier")
    public Map<String,Object> deliveryCashier(HttpServletRequest request) {
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
            List<Stock> stockList = stockService.queryAll(pageIndex, pageSize);
            List<Stock> stockList2 = stockService.queryAll(0, 10000);
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll();
            List<Unit> unitList = unitService.queryAll();
            List<GoodsStockA> goodsStockAList = new ArrayList<>(stockList.size());
            for (Stock stock : stockList) {
                GoodsStockA goodsStockA = new GoodsStockA();
                Goods goods = goodsService.queryById(stock.getGoodsStockId());
                BeanUtils.copyProperties(goods, goodsStockA);
                BeanUtils.copyProperties(stock, goodsStockA);
                goodsStockAList.add(goodsStockA);
            }
            modelMap.put("goodsStockAList", goodsStockAList);
            modelMap.put("categoryList", categoryList);
            modelMap.put("unitList", unitList);
            modelMap.put("functionList", functionList);
            modelMap.put("recordSum", stockList2.size());
            modelMap.put("success", true);
        } catch (SaleException e) {
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
     * 4.2营业员 批发收银 批发出库单提交
     *
     * @param request
     * @return
     */
    @PostMapping("/deliverycashier/commit")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/deliverycashier/commit")
    public Map<String,Object> deliveryCashierCommit(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String deliveryRecordStr = HttpServletRequestUtil.getString(request, "deliveryRecord");
        String deliveryListStr = HttpServletRequestUtil.getString(request, "deliveryList");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        ObjectMapper mapper = new ObjectMapper();
        DeliveryRecord deliveryRecord = null;
        List<Delivery> deliveryList = null;
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Delivery.class);
        if (deliveryListStr == null || deliveryRecordStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败-01");
            return modelMap;
        }
        try {
            deliveryRecord = mapper.readValue(deliveryRecordStr, DeliveryRecord.class);
            deliveryList = mapper.readValue(deliveryListStr, javaType);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败-01");
            return modelMap;
        }
        try {
            String deliveryId = DeliveryIdCreator.get(deliveryRecord.getDeliveryLaunchedStaffId());
            Double money = 0D;
            for (Delivery delivery : deliveryList) {
                delivery.setDeliveryId(deliveryId);
                int res = deliveryService.insert(delivery);
                if (res == 0) {
                    throw new SaleException("提交失败");
                }
                money += delivery.getDeliveryPrice() * delivery.getDeliveryNum();
            }
            deliveryRecord.setDeliveryId(deliveryId);
            deliveryRecord.setDeliveryStatus(DeliveryStatusStateEnum.START.getState());
            deliveryRecord.setDeliveryTotalPrice(money);
            int res = deliveryRecordService.insert(deliveryRecord);
            if (res == 0) {
                throw new SaleException("提交失败");
            }
            modelMap.put("success", true);
        } catch (SaleException e) {
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
     * 4.3营业员 零售收银
     *
     * @param request
     * @return
     */
    @PostMapping("/retailcashier")
    @ResponseBody
    @RequiresPermissions("/retailcashier")
    public Map<String,Object> retailCashier(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        if (secondaryMenuId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，查询失败");
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
            List<Stock> stockList = stockService.queryAll(pageIndex, pageSize);
            List<Stock> stockList2 = stockService.queryAll(0, 10000);
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll();
            List<Unit> unitList = unitService.queryAll();
            List<GoodsStockA> goodsStockAList = new ArrayList<>(stockList.size());
            for (Stock stock : stockList) {
                GoodsStockA goodsStockA = new GoodsStockA();
                Goods goods = goodsService.queryById(stock.getGoodsStockId());
                BeanUtils.copyProperties(goods, goodsStockA);
                BeanUtils.copyProperties(stock, goodsStockA);
                goodsStockAList.add(goodsStockA);
            }
            modelMap.put("goodsStockAList", goodsStockAList);
            modelMap.put("categoryList", categoryList);
            modelMap.put("unitList", unitList);
            modelMap.put("functionList", functionList);
            modelMap.put("recordSum", stockList2.size());
            modelMap.put("success", true);
        } catch (SaleException e) {
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
     * 4.4营业员 零售收银 零售订单提交
     *
     * @param request
     * @return
     */
    @PostMapping("/retailcashier/commit")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/retailcashier/commit")
    public Map<String,Object> retailCashierCommit(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String retailRecordStr = HttpServletRequestUtil.getString(request, "retailRecord");
        String retailListStr = HttpServletRequestUtil.getString(request, "retailList");
        ObjectMapper mapper = new ObjectMapper();
        RetailRecord retailRecord = null;
        List<Retail> retailList = null;
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Retail.class);
        if (retailRecordStr == null || retailListStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败-01");
            return modelMap;
        }
        try {
            retailRecord = mapper.readValue(retailRecordStr, RetailRecord.class);
            retailList = mapper.readValue(retailListStr, javaType);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败-01");
            return modelMap;
        }
        try {
            String retailId = RetailRecordIdCreator.get(retailRecord.getRetailCollectionStaffId());
            retailRecord.setRetailId(retailId);
            retailRecord.setRetailRefundStatus(RetailRefundStatusStateEnum.NO_REFUND.getState());
            int res = retailRecordService.insert(retailRecord);
            if (res == 0) {
                throw new SaleException("提交失败");
            }
            for (Retail retail : retailList) {
                retail.setRetailId(retailId);
                res = retailService.insert(retail);
                if (res == 0) {
                    throw new SaleException("提交失败");
                }
            }
            modelMap.put("success", true);
        } catch (SaleException e) {
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
     * 4.5营业员 批发退货
     *
     * @param request
     * @return
     */
    @PostMapping("/deliveryreturn")
    @ResponseBody
    @RequiresPermissions("/deliveryreturn")
    public Map<String,Object> deliveryReturn(HttpServletRequest request) {
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
            DeliveryRecord deliveryRecord = new DeliveryRecord();
            deliveryRecord.setDeliveryRefundStatus(DeliveryRefundStatusStateEnum.NO_REFUND.getState());
            List<DeliveryRecord> deliveryRecordList = deliveryRecordService
                    .queryByCondition(deliveryRecord, pageIndex, pageSize);
            List<DeliveryRecord> deliveryRecordList2 = deliveryRecordService
                    .queryByCondition(deliveryRecord, 0, 10000);
            modelMap.put("deliveryRecordList", deliveryRecordList);
            modelMap.put("recordSum", deliveryRecordList2.size());
            modelMap.put("functionList", functionList);
            modelMap.put("success", true);
        } catch (SaleException e) {
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
     * 4.6营业员 批发退货 查看详情
     *
     * @param request
     * @return
     */
    @PostMapping("/deliveryreturn/deliverydetails")
    @ResponseBody
    @RequiresPermissions("/deliveryreturn/deliverydetails")
    public Map<String,Object> deliveryReturnDeliveryDetails(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String deliveryId = HttpServletRequestUtil.getString(request, "deliveryId");
        if (deliveryId == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "退货失败-01");
            return modelMap;
        }
        try {
            
            DeliveryRecord deliveryRecord = deliveryRecordService.queryByDeliveryId(deliveryId);
            deliveryRecord.setDeliveryRefundStatus(DeliveryRefundStatusStateEnum.REFUND.getState());
            int res = deliveryRecordService.update(deliveryRecord);
            if (res == 0) {
                throw new SaleException("退货失败");
            }
            List<Delivery> deliveryList = deliveryService.queryByDeliveryId(deliveryId);
            for (Delivery delivery : deliveryList) {
                Stock stock = stockService.queryByGoodsId(delivery.getDeliveryStockGoodsId());
                //TODO STOCKNUMBER
                res = stockService.update(stock);
                if (res == 0) {
                    throw new SaleException("退货失败");
                }
            }
            List<Delivery> deliveryListSuc = deliveryService.queryByDeliveryId(deliveryId);
            List<DeliveryGoodsReturn> deliveryGoodsReturnList = new ArrayList<>(deliveryList.size());
            for (Delivery delivery : deliveryList) {
                DeliveryGoodsReturn deliveryGoods = new DeliveryGoodsReturn();
                Stock stock = stockService.queryByGoodsId(delivery.getDeliveryStockGoodsId());
                Goods goods = goodsService.queryById(delivery.getDeliveryStockGoodsId());
                GoodsCategory goodsCategory = goodsCategoryService.queryById(goods.getGoodsCategoryId());
                Unit unit = unitService.queryById(stock.getStockUnitId());
                //TODO Refund
                BeanUtils.copyProperties(stock, deliveryGoods);
                BeanUtils.copyProperties(delivery, deliveryGoods);
                BeanUtils.copyProperties(goods, deliveryGoods);
                BeanUtils.copyProperties(goodsCategory, deliveryGoods);
                BeanUtils.copyProperties(unit, deliveryGoods);
                deliveryGoodsReturnList.add(deliveryGoods);
            }
            modelMap.put("deliveryGoodsReturnList", deliveryGoodsReturnList);
            modelMap.put("success",true);
        } catch (SaleException e) {
            List<Delivery> deliveryList = deliveryService.queryByDeliveryId(deliveryId);
            List<DeliveryGoods> deliveryGoodsList = new ArrayList<>(deliveryList.size());
            for (Delivery delivery : deliveryList) {
                DeliveryGoods deliveryGoods = new DeliveryGoods();
                Stock stock = stockService.queryByGoodsId(delivery.getDeliveryStockGoodsId());
                Goods goods = goodsService.queryById(delivery.getDeliveryStockGoodsId());
                GoodsCategory goodsCategory = goodsCategoryService.queryById(goods.getGoodsCategoryId());
                Unit unit = unitService.queryById(stock.getStockUnitId());
                BeanUtils.copyProperties(stock, deliveryGoods);
                BeanUtils.copyProperties(delivery, deliveryGoods);
                BeanUtils.copyProperties(goods, deliveryGoods);
                BeanUtils.copyProperties(goodsCategory, deliveryGoods);
                BeanUtils.copyProperties(unit, deliveryGoods);
                deliveryGoodsList.add(deliveryGoods);
            }
            modelMap.put("deliveryGoodsList", deliveryGoodsList);
            modelMap.put("success",true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "退货失败");
            return modelMap;
        }
        return modelMap;
    }
}
