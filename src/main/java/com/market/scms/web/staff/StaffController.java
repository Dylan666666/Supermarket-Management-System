package com.market.scms.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.StaffA;
import com.market.scms.bean.StockingGoods;
import com.market.scms.entity.*;
import com.market.scms.entity.staff.*;
import com.market.scms.enums.ExportBillStatusStateEnum;
import com.market.scms.enums.StocktakingAllStatusStateEnum;
import com.market.scms.enums.StocktakingStatusEnum;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.service.*;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.PageCalculator;
import com.market.scms.util.PasswordHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 14:33
 */
@CrossOrigin
@RestController
public class StaffController {

    @Resource
    private StaffService staffService;
    
    @Resource
    private FunctionService functionService;
    
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
    private StocktakingService stocktakingService;
    
    @Resource
    private StocktakingRecordService stocktakingRecordService;
    
    @Resource
    private StockService stockService;
    
    @Resource
    private StaffJurisdictionService staffJurisdictionService;

    /**
     * 1.3职工信息更新
     *
     * @param request
     * @return
     */
    @PostMapping("/staff/update")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/staff/update")
    public Map<String,Object> staffUpdate(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String staffAStr = HttpServletRequestUtil.getString(request, "staffA");
        if (staffAStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息为空，提交修改失败");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        StaffA staffA = null;
        try {
            staffA = mapper.readValue(staffAStr, StaffA.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息更改失败-01");
            return modelMap;
        }
        try {
            SupermarketStaff staff = staffService.queryById(staffA.getStaffId());
            if (staff == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该用户不存在");
                return modelMap;
            }
            if (staffA.getStaffName() == null || staffA.getStaffPhone() == null
                    || staffA.getStaffPassword() == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "必要信息不能为空");
                return modelMap;
            }
            if (!staffA.getStaffPhone().equals(staff.getStaffPhone())) {
                SupermarketStaff cur = staffService.queryStaffByPhone(staffA.getStaffPhone());
                if (cur != null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "该手机号已被注册,修改失败");
                    return modelMap;
                }
                staff.setStaffPhone(staffA.getStaffPhone());
                staff.setSalt(ByteSource.Util.bytes(staff.getStaffPhone()).toString());
            }
            if (!staffA.getStaffPassword().equals(staff.getStaffPassword())) {
                staff.setStaffPassword(staffA.getStaffPassword());
                new PasswordHelper().encryptPassword(staff);
            }
            if (staffA.getStaffName() != null) {
                staff.setStaffName(staffA.getStaffName());
            }
            int res = staffService.updateStaff(staff);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "修改失败");
                return modelMap;
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
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
     * 1.5 职工密码更改
     * 
     * @param request
     * @return
     */
    @PostMapping("/changePassword")
    @ResponseBody
    @Transactional
    @RequiresPermissions("/staff/changePassword")
    public Map<String,Object> staffChangePassword(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String staffPhone = HttpServletRequestUtil.getString(request, "staffPhone");
        String staffPassword = HttpServletRequestUtil.getString(request, "staffPassword");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        if (staffPassword != null && staffPhone != null && newPassword != null) {
            try {
                SupermarketStaff staff = staffService.queryStaffByPhone(staffPhone);
                if (staff == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "手机号不存在");
                    return modelMap;
                }
                PasswordHelper helper = new PasswordHelper();
                if (!helper.encryptPassword(staffPhone, staffPassword).equals(staff.getStaffPassword())) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "密码输入错误");
                    return modelMap;
                }
                staff.setStaffPassword(newPassword);
                helper.encryptPassword(staff);
                staff.setSalt(ByteSource.Util.bytes(staff.getStaffPhone()).toString());
                int res = staffService.updateStaff(staff);
                if (res == 0) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "密码更改失败");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (SupermarketStaffException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "密码更改失败");
                return modelMap;
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "提交失败");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "信息不能为空");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 3.22职工 入库检查
     *
     * @param request
     * @return
     */
    @PostMapping("/exportinspect")
    @ResponseBody
    @RequiresPermissions("/exportinspect")
    public Map<String,Object> exportInspect(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
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
            List<StaffJurisdiction> staffJurisdictionList = staffJurisdictionService.queryById(staffId);
            List<Function> functionList = new ArrayList<>();
            for (StaffJurisdiction staffJurisdiction : staffJurisdictionList) {
                Function function = functionService.queryById(staffJurisdiction.getFunctionId());
                if (function.getSecondaryMenuId().equals(secondaryMenuId)) {
                    functionList.add(function);
                }
            }
            ExportBill exportBill = new ExportBill();
            exportBill.setExportBillStatus(ExportBillStatusStateEnum.WAREHOUSE_FIRST.getState());
            List<ExportBill> exportBillList = exportBillService.queryByCondition(exportBill, pageIndex, pageSize);
            List<ExportBill> exportBillListMax = exportBillService.queryByCondition(exportBill, 0, 10000);
            modelMap.put("exportBillList", exportBillList);
            modelMap.put("exportBillListCount", exportBillList.size());
            modelMap.put("functionList", functionList);
            modelMap.put("recordSum", exportBillListMax.size());
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
     * 3.23职工 入库检查 确认信息
     *
     * @param request
     * @return
     */
    @PostMapping("/exportinspect/confirm")
    @ResponseBody
    @RequiresPermissions("/exportinspect/confirm")
    public Map<String,Object> exportInspectConfirm(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String exportBillId = HttpServletRequestUtil.getString(request, "exportBillId");
        if (exportBillId == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，访问失败");
            return modelMap;
        }
        try {
            ExportBill exportBill = exportBillService.queryByBillId(exportBillId);
            if (exportBill == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该入库单不存在");
                return modelMap;
            }
            Coupon coupon = couponService.queryByCouponId(exportBill.getExportBillCouponId());
            Goods goods = goodsService.queryById(coupon.getCouponGoodsId());
            GoodsCategory category = goodsCategoryService.queryById(goods.getGoodsCategoryId());
            Unit unit = unitService.queryById(coupon.getCouponUnitId());
            modelMap.put("exportBill", exportBill);
            modelMap.put("coupon", coupon);
            modelMap.put("goods", goods);
            modelMap.put("category", category);
            modelMap.put("unit", unit);
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
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
     * 3.24职工 入库检查 模糊查询
     *
     * @param request
     * @return
     */
    @PostMapping("/exportinspectByConditions")
    @ResponseBody
    @RequiresPermissions("/exportinspectByConditions")
    public Map<String,Object> exportInspectByConditions(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        String exportBillStr = HttpServletRequestUtil.getString(request, "exportBill");
        ExportBill exportBill = null;
        ObjectMapper mapper = new ObjectMapper();
        if (secondaryMenuId < 0 || exportBillStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，访问失败-01");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            exportBill = mapper.readValue(exportBillStr, ExportBill.class);
            if (exportBill == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "不具备访问条件，访问失败-01");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，访问失败-01");
            return modelMap;
        }
        try {
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            List<ExportBill> exportBillList = exportBillService.queryByCondition(exportBill, pageIndex, pageSize);
            List<ExportBill> exportBillListMax = exportBillService.queryByCondition(exportBill, pageIndex, pageSize);
            modelMap.put("exportBillList", exportBillList);
            modelMap.put("exportBillListCount", exportBillList.size());
            modelMap.put("functionList", functionList);
            modelMap.put("recordSum", exportBillListMax.size());
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
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
     * 3.24职工 入库检查 提交
     *
     * @param request
     * @return
     */
    @PostMapping("/exportinspect/submit")
    @ResponseBody
    @RequiresPermissions("/exportinspect/submit")
    public Map<String,Object> submit(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String exportBillStr = HttpServletRequestUtil.getString(request, "exportBill");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        if (exportBillStr == null || staffId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，提交失败");
            return modelMap;
        }
        ExportBill exportBill = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            exportBill = mapper.readValue(exportBillStr, ExportBill.class);
            if (exportBill == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "提交失败-01");
            return modelMap;
        }
        try {
            int res = exportBillService.update(exportBill);
            if (res == 0) {
                throw new SupermarketStaffException("提交失败");
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
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
     * 6.9职工 货品盘点
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/viewStocktakingGoodsList")
    @ResponseBody
    @RequiresPermissions("/stocktaking/viewStocktakingGoodsList")
    public Map<String,Object> viewStocktakingGoodsList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        if (secondaryMenuId < 0 || staffId < 0) {
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
            List<StaffJurisdiction> staffJurisdictionList = staffJurisdictionService.queryById(staffId);
            List<Function> functionList = new ArrayList<>();
            for (StaffJurisdiction staffJurisdiction : staffJurisdictionList) {
                Function function = functionService.queryById(staffJurisdiction.getFunctionId());
                if (function.getSecondaryMenuId().equals(secondaryMenuId)) {
                    functionList.add(function);
                }
            }
            
            int count = stocktakingRecordService.queryStocktakingCount(StocktakingAllStatusStateEnum.START.getState());
            if (count == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "无待盘点单");
                return modelMap;
            }
            List<GoodsCategory> categoryList = goodsCategoryService.queryByStaffId(staffId);
            Map<Integer, Integer> categoryMap = new HashMap<>();
            for (GoodsCategory category : categoryList) {
                categoryMap.put(category.getCategoryId(), 1);
            }
            List<Stocktaking> stocktakingList = stocktakingService.queryAll(0, 10000);
            List<StockingGoods> stockingGoodsList = new ArrayList<>(stocktakingList.size());
            for (Stocktaking stocktaking : stocktakingList) {
                StockingGoods stockingGoods = new StockingGoods();
                Stock stock = stockService.queryById(stocktaking.getStocktakingStockGoodsId());
                Goods goods = goodsService.queryById(stock.getGoodsStockId());
                if (categoryMap.getOrDefault(goods.getGoodsCategoryId(), -1) == -1) {
                    continue;
                }
                BeanUtils.copyProperties(stocktaking, stockingGoods);
                BeanUtils.copyProperties(goods, stockingGoods);
                BeanUtils.copyProperties(stock, stockingGoods);
                stockingGoodsList.add(stockingGoods);
            }
            int recordSum = stockingGoodsList.size();
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            int rightIndex = rowIndex + pageSize;
            if (recordSum < rightIndex) {
                rightIndex = recordSum;
            }
            List<StockingGoods> res = stockingGoodsList.subList(rowIndex, rightIndex);
            modelMap.put("stockingGoodsList", res);
            modelMap.put("recordSum", recordSum);
            modelMap.put("categoryList", categoryList);
            modelMap.put("functionList", functionList);
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
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
     * 6.10职工 货品盘点 模糊查询
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/viewStocktakingGoodsListByConditions")
    @ResponseBody
    @RequiresPermissions("/stocktaking/viewStocktakingGoodsListByConditions")
    public Map<String,Object> viewStocktakingGoodsListByConditions(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        int userId = HttpServletRequestUtil.getInt(request, "userId");
        String stockingGoodsStr = HttpServletRequestUtil.getString(request, "stockingGoods");
        StockingGoods stockingGoods = null;
        ObjectMapper mapper = new ObjectMapper();
        if (secondaryMenuId < 0 || stockingGoodsStr == null || userId < 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "不具备访问条件，访问失败-01");
            return modelMap;
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            stockingGoods = mapper.readValue(stockingGoodsStr, StockingGoods.class);
            if (stockingGoods == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "不具备访问条件，访问失败-01");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "不具备访问条件，访问失败-01");
            return modelMap;
        }
        try {
            List<Function> functionList = functionService.querySecondaryMenuId(secondaryMenuId);
            int count = stocktakingRecordService.queryStocktakingCount(StocktakingAllStatusStateEnum.START.getState());
            if (count == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "无待盘点单");
                return modelMap;
            }

            //商品类别
            Map<Integer, Integer> categoryMap = new HashMap<>();
            List<GoodsCategory> categoryList = null;
            if (stockingGoods.getGoodsCategoryId() != null) {
                categoryMap.put(stockingGoods.getGoodsCategoryId(), 1);
            } else {
                categoryList = goodsCategoryService.queryByStaffId(userId);
                for (GoodsCategory category : categoryList) {
                    categoryMap.put(category.getCategoryId(), 1);
                }
            }

            //商品名字
            Map<Long, Integer> goodsMap = new HashMap<>();
            List<Goods> goodsList = null;
            if (stockingGoods.getGoodsName() != null) {
                Goods goods = new Goods();
                goods.setGoodsName(stockingGoods.getGoodsName());
                goodsList = goodsService.queryByCondition(goods, 0, 10000);
                for (Goods goods1 : goodsList) {
                    goodsMap.put(goods1.getGoodsId(), 1);
                }
            }
            
            //商品编号和盘点状态
            Stocktaking stocktakingCondition = new Stocktaking();
            if (stockingGoods.getStocktakingStatus() != null) {
                stocktakingCondition.setStocktakingStatus(stockingGoods.getStocktakingStatus());   
            }
            if (stockingGoods.getStockGoodsId() != null) {
                stocktakingCondition.setStocktakingStockGoodsId(stockingGoods.getStockGoodsId());
            }
            
            List<Stocktaking> stocktakingList = stocktakingService
                    .queryByCondition(stocktakingCondition, 0, 10000);
            List<StockingGoods> stockingGoodsList = new ArrayList<>(stocktakingList.size());
            for (Stocktaking stocktaking : stocktakingList) {
                StockingGoods stockingGoodsCur = new StockingGoods();
                Stock stock = stockService.queryById(stocktaking.getStocktakingStockGoodsId());
                Goods goods = goodsService.queryById(stock.getGoodsStockId());
                if (stockingGoods.getGoodsCategoryId() != null && 
                        categoryMap.getOrDefault(goods.getGoodsCategoryId(), -1) == -1) {
                    continue;
                }
                if (stockingGoods.getGoodsName() != null && 
                        goodsMap.getOrDefault(goods.getGoodsId(), -1) == -1) {
                    continue;
                }
                BeanUtils.copyProperties(stocktaking, stockingGoodsCur);
                BeanUtils.copyProperties(goods, stockingGoodsCur);
                BeanUtils.copyProperties(stock, stockingGoodsCur);
                stockingGoodsList.add(stockingGoodsCur);
            }
            int recordSum = stockingGoodsList.size();
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            int rightIndex = rowIndex + pageSize;
            if (recordSum < rightIndex) {
                rightIndex = recordSum;
            }
            List<StockingGoods> res = stockingGoodsList.subList(rowIndex, rightIndex);

            modelMap.put("stockingGoodsList", res);
            modelMap.put("recordSum", recordSum);
            modelMap.put("categoryList", categoryList);
            modelMap.put("functionList", functionList);
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询失败");
            return modelMap;
        }
        return modelMap;
    }
    
    /**
     * 6.10职工 货品盘点 盘点(详情查看填写页面)
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/stocktaking")
    @ResponseBody
    @RequiresPermissions("/stocktaking/stocktaking")
    public Map<String,Object> stocktakingStocktaking(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        Long stocktakingId = HttpServletRequestUtil.getLong(request, "stocktakingId");
        Long stockGoodsId = HttpServletRequestUtil.getLong(request, "stockGoodsId");
        if (stockGoodsId < 0 || stocktakingId < 0) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "访问失败-01");
            return modelMap;
        }
        try {
            Stocktaking stocktaking = stocktakingService.queryById(stocktakingId, stockGoodsId);
            if (stocktaking == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该盘点单不存在，访问失败");
                return modelMap;
            }
            StocktakingRecord stocktakingRecord = stocktakingRecordService.queryById(stocktakingId);
            Stock stock = stockService.queryById(stocktaking.getStocktakingStockGoodsId());
            Goods goods = goodsService.queryById(stock.getGoodsStockId());
            GoodsCategory category = goodsCategoryService.queryById(goods.getGoodsCategoryId());
            Unit unit = unitService.queryById(stock.getStockUnitId());
            modelMap.put("stock", stock);
            modelMap.put("stocktaking", stocktaking);
            modelMap.put("stocktakingRecord", stocktakingRecord);
            modelMap.put("goods", goods);
            modelMap.put("category", category);
            modelMap.put("unit", unit);
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
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
     * 6.11职工 货品盘点 盘点提交
     *
     * @param request
     * @return
     */
    @PostMapping("/stocktaking/submitStocktakingGood")
    @ResponseBody
    @RequiresPermissions("/stocktaking/submitStocktakingGood")
    public Map<String,Object> submitStocktakingGood(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        String stocktakingStr = HttpServletRequestUtil.getString(request, "stocktaking");
        if (staffId < 0 || stocktakingStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "访问失败-01");
            return modelMap;
        }
        Stocktaking stocktaking = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            stocktaking = mapper.readValue(stocktakingStr, Stocktaking.class);
            if (stocktaking == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "访问失败-01");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "访问失败-01");
            return modelMap;
        }
        try {
            Stocktaking stocktakingCur = stocktakingService
                    .queryById(stocktaking.getStocktakingId(), stocktaking.getStocktakingStockGoodsId());
            if (stocktakingCur == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该盘点单不存在，无法提交");
                return modelMap;
            }
            if (!stocktakingCur.getStocktakingStatus().equals(StocktakingStatusEnum.START.getState())) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "该盘点单无法提交");
                return modelMap;
            }
            stocktaking.setStocktakingStatus(StocktakingStatusEnum.SECOND.getState());
            int res = stocktakingService.update(stocktaking);
            if (res == 0) {
                throw new SupermarketStaffException("提交失败");
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException e) {
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
