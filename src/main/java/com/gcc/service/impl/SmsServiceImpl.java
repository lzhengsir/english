package com.gcc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcc.common.Constants;
import com.gcc.common.ExceptionEnum;
import com.gcc.entity.AppUser;
import com.gcc.exception.ProjectException;
import com.gcc.service.AppUserService;
import com.gcc.service.SmsService;
import com.gcc.util.HttpUtils;
import com.gcc.util.ProjectUtils;
import com.gcc.util.RandomUtil;
import com.gcc.util.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @className: SmsServiceImpl
 * @author: lzheng
 * @date: 2022/8/26 15:57
 * @description:
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private AppUserService appUserService;

    @Override
    public boolean sendShortMessage(String phone) {
        //判断是否已注册
        QueryWrapper<AppUser> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        AppUser appUser = appUserService.getOne(wrapper);
        if (appUser != null) {
            throw new ProjectException(ExceptionEnum.USER_ALREADY_REGISTER);
        }
        //先查询是否已经发送过短信了
        String redisCode = redisTemplate.boundValueOps(Constants.REGISTER_NS + phone).get();
        if (redisCode != null) {
            throw new ProjectException(ExceptionEnum.ALREADY_SEND_MESSAGE);
        }
        return sendShortMessage(phone, Constants.REGISTER_NS + phone);
    }

    private boolean sendShortMessage(String phone, String key) {
        String host = "http://dingxin.market.alicloudapi.com";
        String path = "/dx/sendSms";
        String method = "POST";
        String appcode = SmsUtils.APPCODE;
        Map<String, String> headers = new HashMap<>(10);
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>(10);
        querys.put("mobile", phone);
        String code = RandomUtil.getSixBitRandom();
        querys.put("param", "code:" + code);
        querys.put("tpl_id", "TP1711063");
        redisTemplate.boundValueOps(key).set(code, 15, TimeUnit.MINUTES);
        log.info("手机验证码：" + code + ", 十五分钟内有效");
        Map<String, String> bodys = new HashMap<>(10);
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            String json = EntityUtils.toString(response.getEntity());
            log.info(json);
            Map<?, ?> map = ProjectUtils.parseObject(json, Map.class);
            return "00000".equals(map.get("return_code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
