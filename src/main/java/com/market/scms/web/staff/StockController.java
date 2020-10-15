package com.market.scms.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.Stock;
import com.market.scms.exceptions.StockException;
import com.market.scms.service.StockService;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/15 9:52
 */
@RestController
@RequestMapping("/stock")
@CrossOrigin
public class StockController {
    @Resource
    private StockService stockService;

    @GetMapping("/queryById")
    public Map<String, Object> queryStockById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long stockId = HttpServletRequestUtil.getLong(request, "stockId");
        if (stockId != -1000) {
            try {
                Stock stock = stockService.queryStockById(stockId);
                modelMap.put("stock", stock);
                modelMap.put("success", true);
            } catch (StockException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输出错");
            return modelMap;
        }
        return modelMap;
    }

    @GetMapping("/queryByGoodsId")
    public Map<String, Object> queryStockByGoodsId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long stockGoodsId = HttpServletRequestUtil.getLong(request, "stockGoodsId");
        if (stockGoodsId != -1000) {
            try {
                Stock stock = stockService.queryStockByGoodsId(stockGoodsId);
                modelMap.put("stock", stock);
                modelMap.put("success", true);
            } catch (StockException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输出错");
            return modelMap;
        }
        return modelMap;
    }

    @PostMapping("/update")
    public Map<String, Object> updateStock(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String stockStr = HttpServletRequestUtil.getString(request, "stock");
        ObjectMapper mapper = new ObjectMapper();
        Stock stock = null;
        try {
            stock = mapper.readValue(stockStr, Stock.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据传输出错");
            return modelMap;
        }
        if (stock != null && stock.getStockId() != null && stock.getStockGoodsNum() >= 0) {
            int res = stockService.updateStock(stock);
            if (res == 0) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "更改失败");
                return modelMap;
            }
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "数据有错");
            return modelMap;
        }
        return modelMap;
    }
}
