package com.market.scms.service;

import com.market.scms.entity.log.LoggingEvent;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/16 14:41
 */
public interface LoggingEventService {
    /**
     * 一键查询日志记录
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<LoggingEvent> queryAll(int pageIndex, int pageSize);


    /**
     * 模糊查询日志记录
     *
     * @param loggingEventCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<LoggingEvent> queryByCondition(LoggingEvent loggingEventCondition, int pageIndex, int pageSize);

    /**
     * 删除日志
     *
     * @param eventId
     * @return
     */
    int delete(Long eventId);
}
