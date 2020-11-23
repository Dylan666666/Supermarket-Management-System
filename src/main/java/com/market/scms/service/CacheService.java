package com.market.scms.service;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/23 14:04
 */
public interface CacheService {
    /**
     * 依据 key前缀删除匹配该模式下的所有key-value
     * @param keyPrefix
     * @return
     */
    void removeFromCache(String keyPrefix);
}
