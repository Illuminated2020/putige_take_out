package com.itheima.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义元数据对象处理器
 *
 *我们在MyMetaObjectHandler类中是不能直接获得HttpSession对象的，所以我们需要通过其他方式来获取登录用户id。
 *我们可以在LoginCheckFilter的doFilter方法中获取当前登录用户id，
 *并调用ThreadLocal的set方法来设置当前线程的线程局部变量的值（用户id），
 *然后在MyMetaObjectHandler的updateFill方法中调用ThreadLocal的get方法来获得当前线程所对应的线程局部变量的值（用户id）。
 *如果在后续的操作中,我们需要在Controller / Service中要使用当前登录用户的ID, 可以直接从ThreadLocal直接获取。
 * 实现步骤：
 *         1). 编写BaseContext工具类，基于ThreadLocal封装的工具类
 *
 *         2). 在LoginCheckFilter的doFilter方法中调用BaseContext来设置当前登录用户的id
 *
 *         3). 在MyMetaObjectHandler的方法中调用BaseContext获取登录用户的id
 */

@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
    /**
     * 插入操作，自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());


        Long id = BaseContext.getCurrentId();

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", id);
        metaObject.setValue("updateUser", id);
    }

    /**
     * 更新操作，自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        log.info(metaObject.toString());

        Long id = BaseContext.getCurrentId();

        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", id);
    }
}
