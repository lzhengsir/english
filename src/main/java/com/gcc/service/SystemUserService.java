package com.gcc.service;

import com.gcc.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gcc.entity.vo.SystemUserVo;

/**
 * <p>
 * 系统管理员 服务类
 * </p>
 *
 * @author lzheng
 * @since 2022-11-22
 */
public interface SystemUserService extends IService<SystemUser> {

    /**
     * 登录
     *
     * @param systemUserVo
     * @return
     */
    String login(SystemUserVo systemUserVo);

    /**
     * 退出登录
     *
     * @param valueByJwtToken
     */
    void logout(String valueByJwtToken);
}
