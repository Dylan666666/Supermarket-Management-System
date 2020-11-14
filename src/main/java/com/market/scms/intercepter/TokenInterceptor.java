package com.market.scms.intercepter;

import com.alibaba.fastjson.JSON;
import com.market.scms.util.HttpServletRequestUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 13:46
 */
public class TokenInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        //登录验证
//        HttpSession session = request.getSession();
//        
//        System.out.println("前端职工token：" + HttpServletRequestUtil.getString(request, "staffToken"));
//        System.out.println("前端供应商token：" + HttpServletRequestUtil.getString(request, "supplierToken"));
//
//        String staffToken = HttpServletRequestUtil.getString(request, "staffToken");
//        String supplierToken = HttpServletRequestUtil.getString(request, "supplierToken");
//        String token = (String)session.getAttribute("token");
//        
//        System.out.println("sessionToken：" + token);
//
//        
//        if ( staffToken == null && supplierToken == null ) {
//            Map<String, Object> modelMap = new HashMap<>(16);
//            modelMap.put("success", false);
//            modelMap.put("errMsg", "请先登录");
//            returnJson(response, JSON.toJSONString(modelMap));
//            return false;
//        } else if (!Objects.equals(staffToken, token) && !Objects.equals(supplierToken, token)) {
//            Map<String, Object> modelMap = new HashMap<>(16);
//            modelMap.put("success", false);
//            modelMap.put("errMsg", "登录已失效，请重新登录");
//            returnJson(response, JSON.toJSONString(modelMap));
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    /**
//     * 返回json数据
//     * 
//     * @param response
//     * @param json
//     * @throws Exception
//     */
//    private void returnJson(HttpServletResponse response, String json) throws Exception{
//        PrintWriter writer = null;
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=utf-8");
//        try {
//            writer = response.getWriter();
//            writer.print(json);
//        } catch (IOException e) {
//            
//        } finally {
//            if (writer != null) {
//                writer.close();
//            }
//        }
//    }
    
}
