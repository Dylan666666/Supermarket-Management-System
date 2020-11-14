package com.market.scms.mapper;

import com.market.scms.entity.staff.Function;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:33
 */
public interface FunctionMapper {

    /**
     * 添加三级菜单
     * 
     * @param function
     * @return
     */
    int insert(@Param("function") Function function);

    /**
     * 删除三级菜单
     * 
     * @param functionId
     * @return
     */
    int delete(@Param("functionId") int functionId);

    /**
     * 通过功能id查询功能表
     * 
     * @param functionId
     * @return
     */
    Function queryById(@Param("functionId") int functionId);

    /**
     * 更改三级菜单信息
     * 
     * @param function
     * @return
     */
    int update(Function function);
}
