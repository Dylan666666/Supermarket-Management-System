package com.market.scms.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.StaffA;
import com.market.scms.bean.StockingGoods;
import com.market.scms.entity.*;
import com.market.scms.entity.staff.*;
import com.market.scms.exceptions.SupermarketStaffException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.service.*;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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
    private StocktakingService stocktakingService;
    
    @Resource
    private StocktakingRecordService stocktakingRecordService;
    
    @Resource
    private StockService stockService;
    
    @Resource
    private RetailRecordService retailRecordService;

    /**
     * 1.1 职工注册
     * 
     * @param request
     * @return
     */
    @PostMapping("/staff/insert")
    @ResponseBody
    public Map<String,Object> insertStaff(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String staffStr = HttpServletRequestUtil.getString(request, "staff");
        ObjectMapper mapper = new ObjectMapper();
        SupermarketStaff staff = null;
        try {
            staff = mapper.readValue(staffStr, SupermarketStaff.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "注册出错-01");
            return modelMap;
        }
        try {
            int res = staffService.insertStaff(staff);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "注册失败");
                return modelMap;
            }
            modelMap.put("success", true);
        } catch (SupermarketStaffException staffException) {
            modelMap.put("success",false);
            modelMap.put("errMsg", staffException.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 1.2 职工登录
     * 
     * @param request
     * @return
     */
    @PostMapping("/staff/login")
    @ResponseBody
    @Transactional
    public Map<String,Object> staffLogin(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>(16);
        String staffAStr = HttpServletRequestUtil.getString(request, "staffA");
        ObjectMapper mapper = new ObjectMapper();
        StaffA staffA = null;
        if (staffAStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "传入信息为空,登录失败");
            return modelMap;
        }
        try {
            staffA = mapper.readValue(staffAStr, StaffA.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "登录失败-01");
            return modelMap;
        }
        if (staffA != null && staffA.getStaffPhone() != null && staffA.getStaffPassword() != null) {
            try {
                SupermarketStaff staff = staffService.staffLogin(staffA.getStaffPhone(), staffA.getStaffPassword());
                if (staff == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "登录失败");
                    return modelMap;
                }
                List<StaffJurisdiction> staffJurisdictionList = staffJurisdictionService.queryById(staff.getStaffId());
                Set<Integer> secondaryIdSet = new HashSet<>();
                Set<Integer> primaryIdSet = new HashSet<>();
                List<SecondaryMenu> secondaryMenuList = new ArrayList<>();
                List<PrimaryMenu> primaryMenuList = new ArrayList<>();
                for (StaffJurisdiction staffJurisdiction : staffJurisdictionList) {
                    Function function = functionService.queryById(staffJurisdiction.getFunctionId());
                    SecondaryMenu curSe = secondaryMenuService.queryById(function.getSecondaryMenuId());
                    if (secondaryIdSet.contains(curSe.getSecondaryMenuId())) {
                        continue;
                    } else {
                        secondaryIdSet.add(curSe.getSecondaryMenuId());
                        secondaryMenuList.add(curSe);
                        PrimaryMenu curPri =primaryMenuService.queryById(curSe.getPrimaryMenuId());
                        if (!primaryIdSet.contains(curPri.getPrimaryMenuId())) {
                            primaryIdSet.add(curPri.getPrimaryMenuId());
                            primaryMenuList.add(curPri);
                        }
                    }
                }
                StaffA staffA1 = new StaffA();
                List<StaffPositionRelation> relationList = staffPositionRelationService.queryById(staff.getStaffId());
                if (relationList.size() > 0) {
                    StaffPosition staffPosition = staffPositionService.queryById(relationList.get(0).getStaffPositionId());
                    BeanUtils.copyProperties(staffPosition, staffA1);
                    BeanUtils.copyProperties(relationList.get(0), staffA1);
                }
                BeanUtils.copyProperties(staff, staffA1);
                modelMap.put("staffA", staffA1);
                modelMap.put("staffToken", staff.getToken());
                modelMap.put("primaryMenuList", primaryMenuList);
                modelMap.put("secondaryMenuList", secondaryMenuList);
                modelMap.put("success",true);
            } catch (SupermarketStaffException staffException) {
                modelMap.put("success",false);
                modelMap.put("errMsg", staffException.getMessage());
                return modelMap;
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "登录失败");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "登录信息不完整");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 1.3职工信息更新
     *
     * @param request
     * @return
     */
    @PostMapping("/staff/update")
    @ResponseBody
    @Transactional
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
     * 1.4 职工退出
     * 
     * @param request
     */
    @PostMapping("/logout")
    @ResponseBody
    public void staffLogout(HttpServletRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        } catch (Exception e) {}
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
            List<ExportBill> exportBillListMax = exportBillService.queryAll(0, 10000);
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
                modelMap.put("success",false);
                modelMap.put("errMsg", "提交失败");
                return modelMap;
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
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll();
            
            List<Stocktaking> stocktakingList = stocktakingService.queryAll(pageIndex, pageSize);
            List<StockingGoods> stockingGoodsList = new ArrayList<>(stocktakingList.size());
            for (Stocktaking stocktaking : stocktakingList) {
                StockingGoods stockingGoods = new StockingGoods();
                Goods goods = goodsService.queryById(stocktaking.getStocktakingStockGoodsId());
                Stock stock = stockService.queryByGoodsId(stocktaking.getStocktakingStockGoodsId());
                BeanUtils.copyProperties(stocktaking, stockingGoods);
                BeanUtils.copyProperties(goods, stockingGoods);
                BeanUtils.copyProperties(stock, stockingGoods);
                stockingGoodsList.add(stockingGoods);
            }
            List<Stocktaking> stocktakingList2 = stocktakingService.queryAll(0, 10000);
            modelMap.put("stockingGoodsList", stockingGoodsList);
            modelMap.put("recordSum", stocktakingList2.size());
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
            //TODO
            Stock stock = stockService.queryByGoodsId(stocktaking.getStocktakingStockGoodsId());
            Goods goods = goodsService.queryById(stocktaking.getStocktakingStockGoodsId());
            GoodsCategory category = goodsCategoryService.queryById(goods.getGoodsCategoryId());
            Unit unit = unitService.queryById(stock.getStockUnitId());
            modelMap.put("stock", stock);
            modelMap.put("stocktaking", stocktaking);
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
}
