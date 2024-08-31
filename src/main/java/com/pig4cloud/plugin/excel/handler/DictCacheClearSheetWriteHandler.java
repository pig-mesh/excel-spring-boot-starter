package com.pig4cloud.plugin.excel.handler;

import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.pig4cloud.plugin.excel.converters.DictTypeConvert;

/**
 * dict 缓存清空
 *
 * @author lengleng
 * @date 2024/08/31
 */
public class DictCacheClearSheetWriteHandler implements WorkbookWriteHandler {

	/**
	 * Called after all operations on the workbook have been completed
	 * @param context
	 */
	@Override
	public void afterWorkbookDispose(WorkbookWriteHandlerContext context) {
		DictTypeConvert.cache.clear();
	}

}
