package com.gcc.util;

import com.gcc.exception.ProjectException;
import io.jsonwebtoken.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @className: JwtUtils
 * @author: lzheng
 * @date: 2022/8/26 15:13
 * @description:
 */
@ConfigurationProperties(prefix = "word.jwt")
@Slf4j
@Component
@Setter
public class JwtUtils implements InitializingBean {
    private static long EXPIRE;
    public static long EXPIRE_HOUR;
    private static String APP_SECRET;
    public static String HEADER;
    private static String TYP;
    private static String ALG;
    private static String SUBJECT;

    private Long expireHour;
    private String appSecret;
    private String header;
    private String typ;
    private String alg;
    private String subject;

    /**
     * 获取字符串
     *
     * @param params
     * @return
     */
    public static String getJwtToken(Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            throw new ProjectException("jwt参数不能为空");
        }
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam("typ", TYP)
                .setHeaderParam("alg", ALG)
                .setSubject(SUBJECT)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APP_SECRET);
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        entries.forEach(item -> {
            jwtBuilder.claim(item.getKey(), item.getValue());
        });
        return jwtBuilder.compact();
    }

    /**
     * 判断token是否存在与有效
     *
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if (!StringUtils.hasLength(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     *
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader(HEADER);
            if (!StringUtils.hasLength(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     *
     * @param request
     * @param key
     * @return
     */
    public static String getKeyByJwtToken(HttpServletRequest request, String key) {
        String jwtToken = request.getHeader(HEADER);
        if (!StringUtils.hasLength(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get(key);
    }

    @Override
    public void afterPropertiesSet() {
        EXPIRE = expireHour * 60 * 60 * 1000;
        EXPIRE_HOUR = expireHour;
        APP_SECRET = appSecret;
        HEADER = header;
        TYP = typ;
        ALG = alg;
        SUBJECT = subject;
    }
}
