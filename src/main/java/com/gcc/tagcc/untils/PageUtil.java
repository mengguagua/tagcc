package com.gcc.tagcc.untils;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串工具类
 * @author 
 *
 */
public class PageUtil {

	/**
	 *
	 * @param pageStart 默认开始项
	 * @param pageSize 每次加载项数
	 * @param pagination 当前页
	 * @param flag 1表示下一页，0表示上一页
	 * @param total 总项数
	 * @return
	 */
	public static Map<String,Object> getPage(int pageStart,int pageSize,String pagination,String flag,int total){
		String temp = pagination;
		int nextStep=0;//当前页的下一步操作
		if("".equals(pagination) || null==pagination){
			pagination="0";//默认第一页
		}else if("1".equals(flag)){//
			nextStep = Integer.parseInt(pagination) + 1;
		}else if("0".equals(flag)){
			nextStep = Integer.parseInt(pagination) - 1;
		}
		pageStart=nextStep*pageSize;
		pagination=String.valueOf(nextStep);
		if(pageStart<0){
			pageStart=0;
			pagination="0";
		}
		if(pageStart>total){
			int lastPageSize = total % pageSize;
			pageStart=total-lastPageSize;
			pagination=temp;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageStart",pageStart);
		map.put("pageSize",pageSize);
		map.put("pagination",pagination);
		return map;
	}
	
}
