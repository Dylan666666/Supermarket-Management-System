package com.market.scms.service.impl;

import com.market.scms.entity.log.LoggingEvent;
import com.market.scms.exceptions.LoggingEventException;
import com.market.scms.mapper.LoggingEventMapper;
import com.market.scms.service.LoggingEventService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/16 14:42
 */
@Service
public class LoggingEventServiceImpl implements LoggingEventService {
    
    @Resource
    private LoggingEventMapper loggingEventMapper;

    @Override
    public List<LoggingEvent> queryAll(int pageIndex, int pageSize) throws LoggingEventException {
        try {
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            List<LoggingEvent> loggingEventList = loggingEventMapper.queryAll(rowIndex, pageSize);
            return loggingEventList;
        } catch (LoggingEventException e) {
            throw new LoggingEventException("查询日志失败");
        }
    }

    @Override
    public List<LoggingEvent> queryByCondition(LoggingEvent loggingEventCondition, int pageIndex, int pageSize) 
            throws LoggingEventException {
        try {
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            List<LoggingEvent> loggingEventList = loggingEventMapper
                    .queryByCondition(loggingEventCondition, rowIndex, pageSize);
            return loggingEventList;
        } catch (LoggingEventException e) {
            throw new LoggingEventException("查询日志失败");
        }
    }

    @Override
    public int delete(Long eventId) throws LoggingEventException {
        try {
            int res = loggingEventMapper.delete(eventId);
            if (res == 0) {
                throw new LoggingEventException("删除日志失败");
            }
            return res;
        } catch (LoggingEventException e) {
            throw new LoggingEventException("删除日志失败");
        }
    }
}
