package com.market.scms.web.maintain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.bean.GoodsStockA;
import com.market.scms.dto.ImageHolder;
import com.market.scms.entity.*;
import com.market.scms.entity.staff.Function;
import com.market.scms.entity.staff.StaffJurisdiction;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.service.*;
import com.market.scms.util.HttpServletRequestUtil;
import com.market.scms.util.PageCalculator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/14 14:39
 */
@CrossOrigin
@RestController
public class MaintainController {
    
    @Resource
    private FunctionService functionService;
    
    @Resource
    private UnitService unitService;
    
    @Resource
    private GoodsCategoryService goodsCategoryService;
    
    @Resource
    private GoodsService goodsService;
    
    @Resource
    private StaffJurisdictionService staffJurisdictionService;
    
    @Resource
    private StaffService staffService;
    
    @Resource
    private StockService stockService;

    /**
     * 8.1商品信息维护
     *
     * @param request
     * @return
     */
    @PostMapping("/goodsinformation")
    @ResponseBody
    @RequiresPermissions("/goodsinformation")
    public Map<String,Object> goodsInformation(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
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
            List<Goods> goodsListMax = goodsService.queryByCondition(new Goods(), 0 ,10000);
            List<Goods> goodsList = goodsService.queryByCondition(new Goods(), pageIndex ,pageSize);
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll();
            
            modelMap.put("success", true);
            modelMap.put("goodsList", goodsList);
            modelMap.put("categoryList", categoryList);
            modelMap.put("functionList", functionList);
            modelMap.put("recordSum", goodsListMax.size());
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
     * 8.2商品信息维护 模糊查询
     *
     * @param request
     * @return
     */
    @PostMapping("/goodsinformation/findByConditions")
    @ResponseBody
    @RequiresPermissions("/goodsinformation/findByConditions")
    public Map<String,Object> findByConditions(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        String goodsStr = HttpServletRequestUtil.getString(request, "goods");
        Goods goods = null;
        ObjectMapper mapper = new ObjectMapper();
        if (goodsStr == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "不具备访问条件，访问失败-01");
            return modelMap;
        }
        try {
            goods = mapper.readValue(goodsStr, Goods.class);
            if (goods == null) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "不具备访问条件，访问失败-01");
                return modelMap;
            }
        } catch (Exception e) {
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
            List<Goods> goodsList = goodsService.queryByCondition(goods, 0, 10000);
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll();

            List<GoodsStockA> goodsStockAList = new ArrayList<>();
            for (Goods goods1 : goodsList) {
                List<Stock> stockList = stockService.queryByGoodsId(goods1.getGoodsId());
                for (Stock stock : stockList) {
                    GoodsStockA goodsStockA = new GoodsStockA();
                    BeanUtils.copyProperties(goods1, goodsStockA);
                    BeanUtils.copyProperties(stock, goodsStockA);
                    goodsStockAList.add(goodsStockA);
                }
            }

            int recordSum = goodsStockAList.size();
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            int rightIndex = rowIndex + pageSize;
            if (recordSum < rightIndex) {
                rightIndex = recordSum;
            }
            List<GoodsStockA> res = goodsStockAList.subList(rowIndex, rightIndex);

            modelMap.put("goodsStockAList", res);
            modelMap.put("recordSum", recordSum);
            modelMap.put("categoryList", categoryList);
            modelMap.put("success", true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "访问失败");
            return modelMap;
        }
        return modelMap;
    }
    
    /**
     * 8.2商品信息维护 修改
     *
     * @param request
     * @return
     */
    @PostMapping("/goodsinformation/modify")
    @ResponseBody
    @RequiresPermissions("/goodsinformation/modify")
    public Map<String,Object> goodsInformationModify(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String goodsStr = HttpServletRequestUtil.getString(request, "goods");
        ObjectMapper mapper = new ObjectMapper();
        Goods goods = null;
        if (goodsStr != null) {
            try {
                goods = mapper.readValue(goodsStr, Goods.class);
                if (goods == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "更改失败-01");
                    return modelMap;
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "更改失败-01");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "更改失败-01");
            return modelMap;
        }
        try {
            int res = goodsService.updateGoods(goods, null);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "更改失败");
                return modelMap;
            }
            modelMap.put("success",true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "更改失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 8.3商品信息维护 新增
     *
     * @param request
     * @return
     */
    @PostMapping("/goodsinformation/add")
    @ResponseBody
    @RequiresPermissions("/goodsinformation/add")
    public Map<String,Object> goodsInformationAdd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String goodsStr = HttpServletRequestUtil.getString(request, "goods");
        ObjectMapper mapper = new ObjectMapper();
        Goods goods = null;
        if (goodsStr != null) {
            try {
                goods = mapper.readValue(goodsStr, Goods.class);
                if (goods == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "添加失败-01");
                    return modelMap;
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "添加失败-01");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "添加失败-01");
            return modelMap;
        }
        ImageHolder thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //若请求种有文件流，则取出相关的文件(缩略图）
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "添加图片失败");
            return modelMap;
        }
        try {
            int res = goodsService.insertGoods(goods, thumbnail);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "添加失败");
                return modelMap;
            }
            modelMap.put("success",true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "添加失败");
            return modelMap;
        }
        
        return modelMap;
    }

    /**
     * 8.4类别信息维护
     *
     * @param request
     * @return
     */
    @PostMapping("/categoryinformation")
    @ResponseBody
    @RequiresPermissions("/categoryinformation")
    public Map<String,Object> categoryInformation(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize <= 0) {
            pageSize = 10000;
        }
        try {
            List<StaffJurisdiction> staffJurisdictionListPre = staffJurisdictionService.queryAll();
            Function functionPre = functionService.queryByUrl("/stocktaking/submitStocktakingGood");
            Set<Integer> staffIdSet = new HashSet<>();
            for (StaffJurisdiction staffJurisdiction : staffJurisdictionListPre) {
                if (staffJurisdiction.getFunctionId().equals(functionPre.getFunctionId())) {
                    staffIdSet.add(staffJurisdiction.getStaffId());
                }
            }
            List<SupermarketStaff> staffListAll = staffService
                    .queryStaffByCondition(new SupermarketStaff(), 0, 10000);
            List<SupermarketStaff> staffList = new ArrayList<>(staffIdSet.size());
            for (SupermarketStaff staff : staffListAll) {
                if (staffIdSet.contains(staff.getStaffId())) {
                    staffList.add(staff);
                }
            }
            
            List<StaffJurisdiction> staffJurisdictionList = staffJurisdictionService.queryById(staffId);
            List<Function> functionList = new ArrayList<>();
            for (StaffJurisdiction staffJurisdiction : staffJurisdictionList) {
                Function function = functionService.queryById(staffJurisdiction.getFunctionId());
                if (function.getSecondaryMenuId().equals(secondaryMenuId)) {
                    functionList.add(function);
                }
            }
            List<GoodsCategory> categoryList = goodsCategoryService.queryAll();
            int recordSum = categoryList.size();
            
            
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            int rightIndex = rowIndex + pageSize;
            if (recordSum < rightIndex) {
                rightIndex = recordSum;
            }
            List<GoodsCategory> res = categoryList.subList(rowIndex, rightIndex);
            
            modelMap.put("success", true);
            modelMap.put("staffList", staffList);
            modelMap.put("categoryList", res);
            modelMap.put("recordSum", recordSum);
            modelMap.put("functionList", functionList);
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
     * 8.5类别信息维护 修改
     *
     * @param request
     * @return
     */
    @PostMapping("/categoryinformation/modify")
    @ResponseBody
    @RequiresPermissions("/categoryinformation/modify")
    public Map<String,Object> categoryInformationModify(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String categoryStr = HttpServletRequestUtil.getString(request, "category");
        ObjectMapper mapper = new ObjectMapper();
        GoodsCategory goodsCategory = null;
        if (categoryStr != null) {
            try {
                goodsCategory = mapper.readValue(categoryStr, GoodsCategory.class);
                if (goodsCategory == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "更改失败-01");
                    return modelMap;
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "更改失败-01");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "更改失败-01");
            return modelMap;
        }
        try {
            int res = goodsCategoryService.update(goodsCategory);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "更改失败");
                return modelMap;
            }
            modelMap.put("success",true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "更改失败");
            return modelMap;
        }
        return modelMap;
    }
    
    /**
     * 8.6类别信息维护 新增
     *
     * @param request
     * @return
     */
    @PostMapping("/categoryinformation/add")
    @ResponseBody
    @RequiresPermissions("/categoryinformation/add")
    public Map<String,Object> categoryInformationAdd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String categoryStr = HttpServletRequestUtil.getString(request, "category");
        ObjectMapper mapper = new ObjectMapper();
        GoodsCategory goodsCategory = null;
        if (categoryStr != null) {
            try {
                goodsCategory = mapper.readValue(categoryStr, GoodsCategory.class);
                if (goodsCategory == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "添加失败-01");
                    return modelMap;
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "添加失败-01");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "添加失败-01");
            return modelMap;
        }
        try {
            int res = goodsCategoryService.insert(goodsCategory);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "添加失败");
                return modelMap;
            }
            modelMap.put("success",true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "添加失败");
            return modelMap;
        }

        return modelMap;
    }
    
    /**
     * 8.7销售单位维护
     *
     * @param request
     * @return
     */
    @PostMapping("/unitinformation")
    @ResponseBody
    @RequiresPermissions("/unitinformation")
    public Map<String,Object> unitInformation(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        int secondaryMenuId = HttpServletRequestUtil.getInt(request, "secondaryMenuId");
        int staffId = HttpServletRequestUtil.getInt(request, "staffId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
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

            List<Unit> unitList = unitService.queryAll();
            int recordSum = unitList.size();
            
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            int rightIndex = rowIndex + pageSize;
            if (recordSum < rightIndex) {
                rightIndex = recordSum;
            }
            List<Unit> res = unitList.subList(rowIndex, rightIndex);

            modelMap.put("success", true);
            modelMap.put("unitList", res);
            modelMap.put("recordSum", recordSum);
            modelMap.put("functionList", functionList);
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
     * 8.8销售单位维护 修改
     *
     * @param request
     * @return
     */
    @PostMapping("/unitinformation/modify")
    @ResponseBody
    @RequiresPermissions("/unitinformation/modify")
    public Map<String,Object> unitInformationModify(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String unitStr = HttpServletRequestUtil.getString(request, "unit");
        ObjectMapper mapper = new ObjectMapper();
        Unit unit = null;
        if (unitStr != null) {
            try {
                unit = mapper.readValue(unitStr, Unit.class);
                if (unit == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "更改失败-01");
                    return modelMap;
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "更改失败-01");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "更改失败-01");
            return modelMap;
        }
        try {
            int res = unitService.update(unit);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "更改失败");
                return modelMap;
            }
            modelMap.put("success",true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "更改失败");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 8.9销售单位维护 增加
     *
     * @param request
     * @return
     */
    @PostMapping("/unitinformation/add")
    @ResponseBody
    @RequiresPermissions("/unitinformation/add")
    public Map<String,Object> unitInformationAdd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>(16);
        String unitStr = HttpServletRequestUtil.getString(request, "unit");
        ObjectMapper mapper = new ObjectMapper();
        Unit unit = null;
        if (unitStr != null) {
            try {
                unit = mapper.readValue(unitStr, Unit.class);
                if (unit == null) {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", "添加失败-01");
                    return modelMap;
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "添加失败-01");
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "添加失败-01");
            return modelMap;
        }
        try {
            int res = unitService.insert(unit);
            if (res == 0) {
                modelMap.put("success",false);
                modelMap.put("errMsg", "添加失败");
                return modelMap;
            }
            modelMap.put("success",true);
        } catch (WareHouseManagerException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "添加失败");
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
    
}
