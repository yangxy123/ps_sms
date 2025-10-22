package com.sms.aop;

import javax.security.auth.login.LoginException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.sms.dto.LoginUserDto;
import com.sms.util.SecurityFrameworkUtils;

/** 
* @author yangxy
* @version 创建时间：2023年7月27日 下午5:15:42 
*/
@Aspect
@Order(-1)
@Component
public class HasLoginAop {
	@Around("@annotation(com.sms.annotations.HasLogin)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		LoginUserDto loginUser = SecurityFrameworkUtils.getLoginUser();
		if(ObjectUtils.isEmpty(loginUser)) {
			throw new LoginException("未登录");
		}
        return joinPoint.proceed();
    }
}
