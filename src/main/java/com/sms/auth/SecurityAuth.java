//package com.sms.auth;
//
//import java.util.List;
//
//import javax.security.auth.login.LoginException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.ObjectUtils;
//
//import com.aia.dto.LoginDto;
//import com.aia.enums.RedisKeyEnums;
//import com.aia.util.SecurityFrameworkUtils;
//import com.sms.util.RedisUtils;
//
///** 
// * 自定义权限校验
//* @author yangxy
//* @version 创建时间：2023年7月26日 下午3:01:20 
//*/
//public class SecurityAuth {
//
//	@Autowired
//	private RedisUtils redisUtils;
//	
//	public boolean hasPermission(String permission) {
//		return hasAnyPermissions(permission);
//	}
//	
//	public boolean hasAnyPermissions(String... permissions) {
//		LoginDto loginUser = SecurityFrameworkUtils.getLoginUser();
//		if(ObjectUtils.isEmpty(loginUser)) {
//			throw new LoginException("Notloggedin");
//		}
//		if(loginUser.getIsAdmin() == 1) {//超级管理员不校验权限
//			return true;
//		}
//		List<?> allList = redisUtils.getAllList(RedisKeyEnums.LOGIN_ADMIN_AUTHORITY_KEY.key + loginUser.getUserName());
//		if (ObjectUtils.isEmpty(allList)){
//			return false;
//		}
//		List<String> authCods = (List<String>) allList;
//		for(String permission:permissions) {
//			if(authCods.contains(permission)) {
//				return true;
//			}
//		}
//		return false;
//    }
//	
//    public boolean hasRole(String role) {
//        return hasAnyRoles(role);
//    }
//
//    public boolean hasAnyRoles(String... roles) {
//    	LoginDto loginUser = SecurityFrameworkUtils.getLoginUser();
//    	if(ObjectUtils.isEmpty(loginUser)) {
//			throw new LoginException("Notloggedin");
//		}
//		if(loginUser.getIsAdmin() == 0) {//超级管理员不校验权限
//			return true;
//		}
//    	List<String> roleCodes = SecurityFrameworkUtils.getLoginUser().getRoleCodes();
//    	if(ObjectUtils.isEmpty(roleCodes)) {
//			return false;
//		}
//		for(String role:roles) {
//			if(roleCodes.contains(role)) {
//				return true;
//			}
//		}
//		return false;
//    }
//}
