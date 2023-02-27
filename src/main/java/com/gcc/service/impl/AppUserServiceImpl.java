package com.gcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcc.common.Constants;
import com.gcc.common.ExceptionEnum;
import com.gcc.common.ThreadLocalHolder;
import com.gcc.entity.AppUser;
import com.gcc.entity.vo.AppUserLogin;
import com.gcc.entity.vo.AppUserRegister;
import com.gcc.entity.vo.AppUserVo;
import com.gcc.exception.ProjectException;
import com.gcc.mapper.AppUserMapper;
import com.gcc.service.AppUserService;
import com.gcc.util.JwtUtils;
import com.gcc.util.Md5PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lzheng
 * @since 2022-11-21
 */
@Service
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void register(AppUserRegister appUserRegister) {
        String verifyCode = redisTemplate.boundValueOps(Constants.REGISTER_NS + appUserRegister.getPhone()).get();
        if (verifyCode == null || !verifyCode.equals(appUserRegister.getVerifyCode())) {
            throw new ProjectException(ExceptionEnum.VERIFY_CODE_ERROR);
        }
        //检查两次密码是否相同
        if (!appUserRegister.getPwd().equals(appUserRegister.getSurePwd())) {
            throw new ProjectException(ExceptionEnum.TWICE_PWD_NOT_SAME);
        }
        //检查用户是否已注册
        QueryWrapper<AppUser> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", appUserRegister.getPhone()).or().eq("username", appUserRegister.getUsername());
        AppUser appUser = this.getOne(wrapper);
        if (appUser != null) {
            throw new ProjectException("用户名已被注册");
        }
        appUserRegister.setPwd(Md5PasswordUtils.dynamicEncryption(appUserRegister.getPwd()));
        appUser = new AppUser();
        BeanUtils.copyProperties(appUserRegister, appUser);
        this.save(appUser);
        //注册成功后删除redis中的验证码
        redisTemplate.delete(Constants.REGISTER_NS + appUserRegister.getPhone());
    }

    @Override
    public String login(AppUserLogin appUserLogin) {
        QueryWrapper<AppUser> wrapper = new QueryWrapper<>();
        String usernameOrPhone = appUserLogin.getUsernameOrPhone();
        wrapper.eq("username", usernameOrPhone).or().eq("phone", usernameOrPhone);
        AppUser appUser = this.getOne(wrapper);
        if (appUser == null || !Md5PasswordUtils.decrypt(appUserLogin.getPwd(), appUser.getPwd())) {
            throw new ProjectException(ExceptionEnum.USERNAME_OR_PWD_ERROR);
        }
        //先删
        String key = Constants.USERID_NS + "client" + Constants.COLON + appUser.getId();
        Set<String> keys = redisTemplate.keys(key + "*");
        if (keys != null) {
            for (String k : keys) {
                Boolean delete = redisTemplate.delete(k);
                if (Boolean.TRUE.equals(delete)) {
                    log.info("有用户登录，把已登录的用户挤下线了");
                }
            }
        }
        String uuid = UUID.randomUUID().toString();
        key = key + Constants.COLON + uuid.replaceAll("-", "");
        //把用户信息存进redis中
        redisTemplate.boundValueOps(key).set(appUser.getId(), JwtUtils.EXPIRE_HOUR, TimeUnit.HOURS);
        //把用户信息返回给前端
        HashMap<String, Object> map = new HashMap<>(16);
        map.put(Constants.SESSION_IDP_KEY_IN_JWT, key);
        return JwtUtils.getJwtToken(map);
    }

    @Override
    public void logout(String valueByJwtToken) {
        redisTemplate.delete(valueByJwtToken);
    }

    @Override
    public AppUserVo getUserInfo() {
        AppUser appUser = this.getById(ThreadLocalHolder.USERID.get());
        AppUserVo appUserVo = new AppUserVo();
        BeanUtils.copyProperties(appUser, appUserVo);
        return appUserVo;
    }
}
