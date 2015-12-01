package com.yh.common.jpa.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yh.common.jpa.model.BaseEntity;
import com.yh.common.utils.UtilDate;
import com.yh.common.utils.UtilReflections;

@SuppressWarnings("rawtypes")
public abstract class EntityUtils {
    private static final String SORT_FIELD="sortNo";
    private static final String GET_SORT_FIELD="getSortNo";
	/**
	 * 设置实体更新相关的信息
	 * 
	 * @param entity
	 */
	public static void doEntityValueForUpdate(Object entity) {
		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			baseEntity.setLastUpdateDatetime(UtilDate.getNowTime());
			String userId = StringUtils.EMPTY;
			baseEntity.setCreateUserId(userId);
		}

	}

	/**
	 * 设置实体保存相关的信息
	 * 
	 * @param entity
	 */
	public static void doEntityValueForSave(Object entity) {

		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			baseEntity.setCreateDatetime(UtilDate.getNowTime());
			String userId = StringUtils.EMPTY;
			baseEntity.setCreateUserId(userId);
		}
	}

	/**
	 * 设置实体保存相关的信息
	 * 
	 * @param entity
	 */
	public static void doEntityDeleteSign(Object entity) {

		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			baseEntity.setIsDeleted("1");
		}
	}

	/**
	 * UPDATE时设置对象中lastupdatedatetime和lastupdateuserid
	 * 
	 * @param object
	 *            更新的entity
	 */
	@SuppressWarnings("unchecked")
	public static void setUpdateRelated(Object object) {
		try {
			Date now = new Date();
			if (object instanceof java.util.Map) {
				Map map = (Map) object;
				map.put("lastUpdateDatetime", new Timestamp(now.getTime()));
			} else {
				UtilReflections.invokeSetter(object, "lastUpdateDatetime",
						new Timestamp(now.getTime()));
			}
		} catch (Exception e) {

		}
	}

	/**
	 * UPDATE时设置对象中createdatetime、createuserid、lastupdatedatetime、
	 * lastupdateuserid
	 * 
	 * @param object
	 *            更新的entity
	 */
	@SuppressWarnings("unchecked")
	public static void setSaveRelated(Object object) {
		try {
			Date now = new Date();
			if (object instanceof java.util.Map) {
				Map map = (Map) object;

				map.put("createDatetime", new Timestamp(now.getTime()));
				map.put("lastUpdateDatetime", new Timestamp(now.getTime()));

			} else {
				UtilReflections.invokeSetter(object, "createDatetime",
						new Timestamp(now.getTime()));
				UtilReflections.invokeSetter(object, "lastUpdateDatetime",
						new Timestamp(now.getTime()));
			}
		} catch (Exception e) {

		}
	}

	/**
	 * UPDATE时设置对象中createdatetime、createuserid、lastupdatedatetime、
	 * lastupdateuserid
	 * 
	 * @param object
	 * @param object
	 */
	public static void setSaveRelatedUp(Object object) {
		Date now = new Date();
		try {
			UtilReflections.invokeSetter(object, "lastUpdateDateTime",
					new Timestamp(now.getTime()));
		} catch (Exception e) {

		}
	}
	/**
	 * 属性赋值
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target){
		if(isSetValue(source)){
			UtilReflections.setFieldValue(target, SORT_FIELD, 0);
		}
		BeanUtils.copyProperties(source, target);
	}
	
	/**
	 * 判断是否重新设置对应的排序值
	 * @param entity
	 * @return
	 */
	private static boolean isSetValue(Object entity){
			Method method;
			try {
				method = entity.getClass().getDeclaredMethod(GET_SORT_FIELD);
				if(method!=null){
					Object result = null;
						try {
							result = method.invoke(entity, null);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						return result==null?true:false;
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}

		return false;
	}
}
