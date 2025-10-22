package com.sms.base.handler;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/** 
 * 创建人、修改人、创建时间、修改时间自动填充
* @author yangxy
* @version 创建时间：2023年7月25日 下午5:06:33 
*/
@Component
public class MybatisHandler implements MetaObjectHandler{

	@Override
	public void insertFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		Object createdAt = getFieldValByName("createdAt", metaObject);
		if(ObjectUtils.isEmpty(createdAt)) {
			this.setFieldValByName("createdAt", new Date(), metaObject);
		}
		Object updatedAt = getFieldValByName("updatedAt", metaObject);
		if(ObjectUtils.isEmpty(updatedAt)) {
			this.setFieldValByName("updatedAt", new Date(), metaObject);
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		Object updatedAt = getFieldValByName("updatedAt", metaObject);
		if(ObjectUtils.isEmpty(updatedAt)) {
			this.setFieldValByName("updatedAt", new Date(), metaObject);
		}
	}

}
