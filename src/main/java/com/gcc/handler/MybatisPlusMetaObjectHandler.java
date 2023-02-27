package com.gcc.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gcc.common.ThreadLocalHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: lzhen
 * @since: 2022/11/19 23:40
 * @description:
 */
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("deleted", 0, metaObject);
        this.setFieldValByName("version", 1, metaObject);
        this.setFieldValByName("createBy", ThreadLocalHolder.USERID.get(), metaObject);
        this.setFieldValByName("updateBy", ThreadLocalHolder.USERID.get(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", ThreadLocalHolder.USERID.get(), metaObject);
    }

}
