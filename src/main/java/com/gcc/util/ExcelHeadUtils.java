package com.gcc.util;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: lzhen
 * @since: 2022/12/1 17:37
 * @description:
 */
@Component
@Setter
@ConfigurationProperties(prefix = "excel.import")
public class ExcelHeadUtils implements InitializingBean {

    private Map<Integer, String> columnMap;

    private Integer columns;
    public static Map<Integer, String> COLUMN_MAP;
    public static Integer COLUMNS;

    @Override
    public void afterPropertiesSet() {
        COLUMN_MAP = this.columnMap;
        COLUMNS = this.columns;
    }
}
