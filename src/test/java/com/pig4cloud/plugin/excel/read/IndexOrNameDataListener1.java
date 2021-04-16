package com.pig4cloud.plugin.excel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 读取Excel 核心处理类
 *
 * @author lengleng
 * @date 2021/4/16
 */
@Slf4j
public class IndexOrNameDataListener1 extends AnalysisEventListener<IndexOrNameData1> {

	@Override
	public void invoke(IndexOrNameData1 data, AnalysisContext context) {
		log.info("解析到一条数据:{}", data);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		log.info("所有数据解析完成！");
	}

}
