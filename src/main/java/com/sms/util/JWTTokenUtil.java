package com.sms.util;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.config.JWTConfig;
import com.sms.dto.LoginUserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT工具类
 * @Author Sans
 * @CreateTime 2019/10/2 7:42
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 私有化构造器
     */
    private JWTTokenUtil(){}

    /**
     * 生成Token
     * @Author Sans
     * @CreateTime 2019/10/2 12:16
     * @Param  selfUserEntity 用户安全实体
     * @Return Token
     */
    public static String createAccessToken(LoginUserDto loginUser){
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                .setId(loginUser.getUserId().toString())
                // 主题
                .setSubject(JSON.toJSONString(loginUser))
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("aia")
                // 自定义属性 放入用户拥有权限
//                .claim("authorities", JSON.toJSONString(loginUser.getAuthCods()))
//                .claim("roles", JSON.toJSONString(loginUser.getRoleCodes()))
                // 失效时间
//                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
                .compact();
        return token;
    }
    
    /**
     * 解析token
    * @author yangxy
    * @version 创建时间：2024年6月6日 下午1:38:48 
    * @return
     */
    public static LoginUserDto decodeToke(String token) {
    	try {
			Claims claims = Jwts.parser().setSigningKey(JWTConfig.secret).parseClaimsJws(token).getBody();
			// 获取用户名
			String userJsonString = claims.getSubject();
			if (!StringUtils.isEmpty(userJsonString)) {
				LoginUserDto loginUser = JSONObject.toJavaObject(JSON.parseObject(userJsonString), LoginUserDto.class);
				return loginUser;
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
    }
}