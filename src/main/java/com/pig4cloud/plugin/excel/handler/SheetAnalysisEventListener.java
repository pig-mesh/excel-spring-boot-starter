package com.pig4cloud.plugin.excel.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lengleng
 * @date 2021/4/16
 */
@Slf4j
public class SheetAnalysisEventListener extends AnalysisEventListener<Object> {

	@Getter
	private List<Object> list = new ArrayList<>();

	@Override
	public void invoke(Object o, AnalysisContext analysisContext) {
		list.add(o);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		log.debug("execl read analysed");
	}

}
