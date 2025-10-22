package com.sms.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.sms.config.JWTConfig;
import com.sms.dto.LoginUserDto;
import com.sms.enums.RedisKeyEnums;
import com.sms.util.JWTTokenUtil;
import com.sms.util.RedisUtils;

import io.netty.util.internal.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT接口请求校验拦截器
 * 请求接口时会进入这里验证Token是否合法和过期
 * @Author Sans
 * @CreateTime 2019/10/5 16:41
 */
@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {
	private RedisUtils redisUtils;
	
    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager,RedisUtils redisUtils) {
        super(authenticationManager);
        this.redisUtils = redisUtils;
    }

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 获取请求头中JWT的Token
        String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
        if(!StringUtils.isEmpty(tokenHeader)) {
        	if(tokenHeader.startsWith(JWTConfig.tokenPrefix)) {
        		String token = tokenHeader.replace(JWTConfig.tokenPrefix, "");
            	LoginUserDto loginDto = JWTTokenUtil.decodeToke(token);
            	String keySuff = null;
            	
            	if(loginDto.getType() == 1) {//1客户端用户
            		keySuff = RedisKeyEnums.CLIENT_LOGIN_TOKEN.key;
            	}else if(loginDto.getType() == 2) {//2管理端用户
            		keySuff = RedisKeyEnums.ADMIN_LOGIN_TOKEN.key;
            	}
            	if(!ObjectUtils.isEmpty(loginDto)) {
            		if (redisUtils.hasKey(keySuff+loginDto.getTelPhone())){
						String token1 = (String) redisUtils.get(keySuff+loginDto.getTelPhone());
						if(token.equals(token1)) {
							if(redisUtils.getExpire(redisUtils+loginDto.getTelPhone()) <= 30*60) {//过期时间小于等于半小时自动续期
								redisUtils.expire(redisUtils+loginDto.getTelPhone(), 2*60*60);
							}
							UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDto, loginDto.getUserId(), Collections.emptyList());
							SecurityContextHolder.getContext().setAuthentication(authentication);
						}
						
					} 
            	}
        	}
        }
		
        filterChain.doFilter(request, response);
    }
}