package com.market.scms.web.both;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.GodownEntry;
import com.market.scms.entity.GoodsList;
import com.market.scms.entity.OrderForm;
import com.market.scms.exceptions.GodownEntryException;
import com.market.scms.exceptions.GoodsListException;
import com.market.scms.service.GodownEntryService;
import com.market.scms.service.GoodsListService;
import com.market.scms.service.OrderFomService;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/14 9:06
 */
@RestController
@RequestMapping("/goodsList")
@CrossOrigin
public class GoodsListController {
    
    @Resource
    private GoodsListService goodsListService;
    
    @Resource
    private GodownEntryService godownEntryService;
    
    @Resource 
    private OrderFomService orderFomService;
    
    @PostMapping("/insert")
    public Map<String, Object> insertGoodsList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String goodsListStr = HttpServletRequestUtil.getString(request, "goodsList");
        Long supplierId = HttpServletRequestUtil.getLong(request, "supplierId");
        ObjectMapper mapper = new ObjectMapper();
        GoodsList goodsList = null;
        try {
            goodsList = mapper.readValue(goodsListStr, GoodsList.class);
            if (goodsList == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "传入数据为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输出错");
            return modelMap;
        }
        if (goodsList != null && goodsList.getGoodsOrderId() != null) {
            try {
                 int res = goodsListService.insertGoods(goodsList);
                 if (res == 0) {
                     modelMap.put("success", false);
                     modelMap.put("errMsg", "添加失败");
                     return modelMap;
                 }
                 try {
                     OrderForm orderForm = orderFomService.queryFormById(goodsList.getGoodsOrderId());
                     GodownEntry godownEntry = getGodownEntry(goodsList, orderForm, supplierId);
                     int curRes = godownEntryService.insertEntry(godownEntry);
                     if (curRes == 0) {
                         modelMap.put("success", false);
                         modelMap.put("errMsg", "添加入库失败");
                         return modelMap;
                     }
                 } catch (GodownEntryException e) {
                     modelMap.put("success", false);
                     modelMap.put("errMsg", "添加入库失败");
                     return modelMap;
                 }
                 modelMap.put("success", true);
            } catch (GoodsListException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "添加失败");
                return modelMap;
            }
        }
        return modelMap;
    }
    
    @GetMapping("/queryById")
    public Map<String, Object> queryGoodsListById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long goodsId = HttpServletRequestUtil.getLong(request, "goodsId");
        if (goodsId > 0) {
            try {
                GoodsList goodsList = goodsListService.queryGoodsById(goodsId);
                modelMap.put("goodsList", goodsList);
                modelMap.put("success", true);
            } catch (GoodsListException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询信息出错");
            return modelMap;
        }
        return modelMap;
    }

    @GetMapping("/queryByOrder")
    public Map<String, Object> queryGoodsListByOrderId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long goodsOrderId = HttpServletRequestUtil.getLong(request, "goodsOrderId");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        if (pageIndex == -1000) {
            pageIndex = 0;
        }
        if (pageSize == -1000) {
            pageSize = 100;
        }
        if (goodsOrderId > 0) {
            try {
                List<GoodsList> list = goodsListService.queryGoodsByOrderId(goodsOrderId, pageIndex, pageSize);
                modelMap.put("list", list);
                modelMap.put("success", true);
            } catch (GoodsListException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "查询失败");
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询信息出错");
            return modelMap;
        }
        return modelMap;
    }

    @PostMapping("/update")
    public Map<String, Object> updateGoodsList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String goodsListStr = HttpServletRequestUtil.getString(request, "goodsList");
        ObjectMapper mapper = new ObjectMapper();
        GoodsList goodsList = null;
        try {
            goodsList = mapper.readValue(goodsListStr, GoodsList.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输失败" + e.getMessage());
            return modelMap;
        }
        if (goodsList != null && goodsList.getGoodsId() != null) {
            try {
                int res = goodsListService.updateGoods(goodsList);
                if (res == 0) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "更改信息失败");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (GoodsListException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "更改信息失败" + e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "不具备更改条件" );
            return modelMap;
        }
        
        return modelMap;
    }

    /**
     * 用于生成入库单信息
     * 
     * @param goodsList
     * @param supplierId
     * @return
     */
    private GodownEntry getGodownEntry(GoodsList goodsList, OrderForm orderForm, Long supplierId) {
        GodownEntry godownEntry = new GodownEntry();
        godownEntry.setGodownEntryDate(new Date());
        godownEntry.setGodownOrderId(orderForm.getOrderId());
        godownEntry.setGodownEntrySupplierId(supplierId);
        godownEntry.setGodownEntryPaid(0D);
        godownEntry.setGodownEntryNum(orderForm.getGoodsNum());
        godownEntry.setGodownEntryGoodsPrice(goodsList.getGoodsPrice());
        godownEntry.setGodownEntryGoodsId(goodsList.getGoodsId());
        godownEntry.setGodownEntryStatus(1);
        return godownEntry;
    }
    
}
