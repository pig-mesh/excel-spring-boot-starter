package com.pig4cloud.plugin.excel.read;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 读取Excel 核心处理类
 *
 * @author lengleng
 * @date 2021/4/16
 */
@Slf4j
public class IndexOrNameDataListener extends AnalysisEventListener<IndexOrNameData> {

	@Override
	public void invoke(IndexOrNameData data, AnalysisContext context) {
		log.info("解析到一条数据:{}", data);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		log.info("所有数据解析完成！");
	}

}
