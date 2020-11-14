package com.market.scms.mapper;

import com.market.scms.entity.staff.DefaultPositionMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:33
 */
public interface DefaultPositionMenuMapper {

    /**
     * 一件查询所有
     * 
     * @return
     */
    List<DefaultPositionMenu> queryAll();
    
    /**
     * 通过职位id查询对应二级菜单
     * 
     * @param positionId
     * @return
     */
    List<DefaultPositionMenu> queryByPositionId(@Param("positionId") Integer positionId);

    /**
     * 插入新数据
     * 
     * @param menu
     * @return
     */
    int insert(@Param("menu")DefaultPositionMenu menu);

    /**
     * 删除该行数据
     * 
     * @param menu
     * @return
     */
    int delete(@Param("menu")DefaultPositionMenu menu);
    
}
