package com.market.scms.web.both;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.OrderForm;
import com.market.scms.exceptions.OrderFormException;
import com.market.scms.service.OrderFomService;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/12 15:39
 */
@RestController
@RequestMapping("/orderForm")
@CrossOrigin
public class OrderFormController {
    
    @Resource
    private OrderFomService orderFomService;

    @PostMapping("/insert")
    public Map<String, Object> insertOrderForm(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String goodsName = HttpServletRequestUtil.getString(request, "goodsName");
        String goodsCategory = HttpServletRequestUtil.getString(request, "goodsCategory");
        String goodsDetailedDescription = HttpServletRequestUtil.getString(request, "goodsDetailedDescription");
        int goodsNum = HttpServletRequestUtil.getInt(request, "goodsNum");
        if (goodsName != null && goodsCategory != null && goodsDetailedDescription != null && goodsNum > 0) {
            try {
                OrderForm orderForm = new OrderForm();
                orderForm.setGoodsName(goodsName);
                orderForm.setGoodsCategory(goodsCategory);
                orderForm.setGoodsNum(goodsNum);
                orderForm.setGoodsDetailedDescription(goodsDetailedDescription);
                int res = orderFomService.insertOrderForm(orderForm);
                if (res == 0) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "添加订单失败");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (OrderFormException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请务必正确输入商品名，商品类别,商品详细描述以及商品数量");
        }
        return modelMap;
    }

    @GetMapping("/query")
    public Map<String, Object> queryOrderForm(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String orderFormStr = HttpServletRequestUtil.getString(request, "orderForm");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        ObjectMapper mapper = new ObjectMapper();
        OrderForm orderForm = null;
        try {
            orderForm = mapper.readValue(orderFormStr, OrderForm.class);
            if (orderForm == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "添加失败");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输出错");
            return modelMap;
        }
        try {
            if (pageIndex == -1000) {
                pageIndex = 0;
            }
            if (pageSize == -1000) {
                pageSize = 100;
            }
            List<OrderForm> list = orderFomService.queryFormByCondition(orderForm, pageIndex, pageSize);
            modelMap.put("list", list);
            modelMap.put("count", list.size());
            modelMap.put("success", true);
        } catch (OrderFormException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }
    
    @PostMapping("/update")
    public Map<String, Object> updateOrderForm(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String orderFormStr = HttpServletRequestUtil.getString(request, "orderForm");
        ObjectMapper mapper = new ObjectMapper();
        OrderForm orderForm = null;
        try {
            orderForm = mapper.readValue(orderFormStr, OrderForm.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输失败" + e.getMessage());
            return modelMap;
        }
        if (orderForm != null && orderForm.getOrderStatus() != null) {
            try {
                int res = orderFomService.updateOrderForm(orderForm);
                if (res == 0) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "更改失败");
                    return modelMap;
                }
                modelMap.put("success", true);
            } catch (OrderFormException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "不具备更新条件");
        }
        return modelMap;
    }

}
