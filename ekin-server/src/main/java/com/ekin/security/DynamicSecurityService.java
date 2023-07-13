package com.ekin.security;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * @author colin
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
