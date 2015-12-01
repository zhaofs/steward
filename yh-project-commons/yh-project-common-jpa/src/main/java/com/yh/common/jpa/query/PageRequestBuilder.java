package com.yh.common.jpa.query;

import javax.servlet.ServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.yh.common.utils.UtilStr;


public abstract class PageRequestBuilder {
	/**
	 * 根据请求参数构建PageRequest对象
	 * @param request
	 * @return
	 */
	public static PageRequest buildPageRequest(ServletRequest request){
		int pageNumber=UtilStr.getNotNullIntValue(request.getParameter("pageNumber"),1);
		int pageSize=UtilStr.getNotNullIntValue(request.getParameter("pageSize"),10);
		String sortType=request.getParameter("sortType");
		return buildPageRequest(pageNumber,pageSize,sortType);
	}
	
	/**
	 * 根据请求参数构建PageRequest对象
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	public static PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
}
