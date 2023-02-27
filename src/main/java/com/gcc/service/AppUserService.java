package com.gcc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gcc.entity.AppUser;
import com.gcc.entity.vo.AppUserLogin;
import com.gcc.entity.vo.AppUserRegister;
import com.gcc.entity.vo.AppUserVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lzheng
 * @since 2022-11-21
 */
public interface AppUserService extends IService<AppUser> {

    /**
     * 注册
     *
     * @param appUserRegister
     */
    void register(AppUserRegister appUserRegister);

    /**
     * 登录
     *
     * @param appUserLogin
     * @return
     */
    String login(AppUserLogin appUserLogin);

    /**
     * 退出登录
     *
     * @param valueByJwtToken
     */
    void logout(String valueByJwtToken);

    /**
     * 获取用户信息
     *
     * @return
     */
    AppUserVo getUserInfo();
}
