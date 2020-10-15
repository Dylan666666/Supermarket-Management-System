package com.market.scms.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.entity.StockJournal;
import com.market.scms.exceptions.StockJournalException;
import com.market.scms.service.StockJournalService;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/15 9:53
 */
@RestController
@RequestMapping("/stockJournal")
@CrossOrigin
public class StockJournalController {
    
    @Resource
    private StockJournalService stockJournalService;

    /**
     * 通过入库流水编号查询入库流水账表
     * 
     * @param request
     * @return
     */
    @GetMapping("/queryById")
    public Map<String, Object> queryStockJournalById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long stockJournalId = HttpServletRequestUtil.getLong(request, "stockJournalId");
        if (stockJournalId > 0) {
            try {
                StockJournal stockJournal = stockJournalService.queryStockJournalById(stockJournalId);
                modelMap.put("stockJournal", stockJournal);
                modelMap.put("success", true);
            } catch (StockJournalException e) {
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

    /**
     * 查询所有的入库流水账表的集合
     * 
     * @param request
     * @return
     */
    @GetMapping("/queryList")
    public Map<String, Object> queryStockJournalList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            List<StockJournal> list = stockJournalService.queryStockJournalList();
            modelMap.put("list", list);
            modelMap.put("success", true);
        } catch (StockJournalException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 条件查询入库流水账表集合
     * 
     * @param request
     * @return
     */
    @GetMapping("/queryByCondition")
    public Map<String, Object> queryStockJournalListByCondition(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        String conditionStr = HttpServletRequestUtil.getString(request, "stockJournal");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        ObjectMapper mapper = new ObjectMapper();
        StockJournal stockJournal = null;
        try {
            stockJournal = mapper.readValue(conditionStr, StockJournal.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "传入信息有错，查询失败");
            return modelMap;
        }
        if (stockJournal != null) {
            try {
                if (pageIndex == -1000) {
                    pageIndex = 0;
                }
                if (pageSize == -1000) {
                    pageSize = 100;
                }
                List<StockJournal> list = stockJournalService
                        .queryStockJournalListByCondition(stockJournal, pageIndex, pageSize);
                modelMap.put("list", list);
                modelMap.put("success", true);
            } catch (StockJournalException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "传入信息有错，查询失败");
            return modelMap;
        }
        return modelMap;
    }
}
