package com.market.scms.mapper;

import com.market.scms.entity.log.LoggingEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/13 17:59
 */
public interface LoggingEventMapper {

    /**
     * 一键查询日志记录
     * 
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<LoggingEvent> queryAll(@Param("rowIndex") int rowIndex,
                                @Param("pageSize") int pageSize);


    /**
     * 模糊查询日志记录
     * 
     * @param loggingEventCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<LoggingEvent> queryByCondition(@Param("loggingEventCondition") LoggingEvent loggingEventCondition,
                                        @Param("rowIndex") int rowIndex,
                                        @Param("pageSize") int pageSize);

    /**
     * 删除日志
     * 
     * @param eventId
     * @return
     */
    int delete(Long eventId);
}
