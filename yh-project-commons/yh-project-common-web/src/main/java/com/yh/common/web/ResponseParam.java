package com.yh.common.web;

import java.util.HashMap;

public class ResponseParam extends HashMap<String, Object> {
	private static final long serialVersionUID = 5220724940965784071L;

	public static final String SUCCESS_PARAM = "result" ;
	
	public static final String SUCCESS_PARAM_VALUE = "success" ;
	
	public static final String FAIL_PARAM = "result" ;
	
	public static final String FAIL_PARAM_VALUE = "fail" ;
	
	public static final String MESSAGE_PARAM = "message" ;
	
	private ResponseParam(){
		
	}
	
	public static ResponseParam getSuccessParam(){
		ResponseParam successParam = new ResponseParam() ;
		successParam.put(SUCCESS_PARAM, SUCCESS_PARAM_VALUE) ;
		return successParam ;
	}
	
	public static ResponseParam getFailParam(){
		ResponseParam successParam = new ResponseParam() ;
		successParam.put(FAIL_PARAM, FAIL_PARAM_VALUE) ;
		return successParam ;
	}
	/**
	 * 添加参数信息，key为MESSAGE，多次调用addMessage方法会替换相应的key值
	 * @param key
	 * @param value
	 * @return
	 */
	public ResponseParam addMessage(Object value){
		this.put(MESSAGE_PARAM, value) ;
		return this ;
	}
	/**
	 * 添加参数信息，如果key和SUCCESS_PARAM或FAIL_PARAM_PARAM相同，则会替换相应的值
	 * @param key
	 * @param value
	 * @return
	 */
	public ResponseParam addParam(String key , Object value){
		this.put(key, value) ;
		return this ;
	}
}
