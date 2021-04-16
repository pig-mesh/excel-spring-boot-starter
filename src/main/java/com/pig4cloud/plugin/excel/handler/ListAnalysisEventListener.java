package com.pig4cloud.plugin.excel.handler;

import com.alibaba.excel.event.AnalysisEventListener;

import java.util.List;

/**
 * list analysis EventListener
 *
 * @author L.cm
 */
public abstract class ListAnalysisEventListener<T> extends AnalysisEventListener<T> {

	/**
	 * 获取 excel 解析的对象列表
	 *
	 * @return 集合
	 */
	public abstract List<T> getList();

}
