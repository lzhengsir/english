package com.gcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcc.common.Constants;
import com.gcc.common.ExceptionEnum;
import com.gcc.entity.SystemUser;
import com.gcc.entity.vo.SystemUserVo;
import com.gcc.exception.ProjectException;
import com.gcc.mapper.SystemUserMapper;
import com.gcc.service.SystemUserService;
import com.gcc.util.JwtUtils;
import com.gcc.util.Md5PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 系统管理员 服务实现类
 * </p>
 *
 * @author lzheng
 * @since 2022-11-22
 */
@Service
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(SystemUserVo systemUserVo) {
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", systemUserVo.getUsername());
        SystemUser systemUser = this.getOne(wrapper);
        if (systemUser == null) {
            log.error("管理员用户名不存在");
            throw new ProjectException(ExceptionEnum.USERNAME_OR_PWD_ERROR);
        } else if (!Md5PasswordUtils.decrypt(systemUserVo.getPwd(), systemUser.getPwd())) {
            log.error("管理员密码不正确");
            throw new ProjectException(ExceptionEnum.USERNAME_OR_PWD_ERROR);
        }
        //先删
        String key = Constants.USERID_NS + "administer" + Constants.COLON + systemUser.getId();
        Set<String> keys = redisTemplate.keys(key + "*");
        if (keys != null) {
            keys.forEach(item -> redisTemplate.delete(item));
        }
        //把用户信息存进redis中
        String uuid = UUID.randomUUID().toString();
        key = key + Constants.COLON + uuid.replaceAll("-", "");
        redisTemplate.boundValueOps(key).set(systemUser.getId(), JwtUtils.EXPIRE_HOUR, TimeUnit.HOURS);
        //把用户信息返回给前端
        Map<String, Object> map = new HashMap<>(16);
        map.put(Constants.SESSION_IDP_KEY_IN_JWT, key);
        return JwtUtils.getJwtToken(map);
    }

    @Override
    public void logout(String valueByJwtToken) {
        redisTemplate.delete(valueByJwtToken);
    }
}
