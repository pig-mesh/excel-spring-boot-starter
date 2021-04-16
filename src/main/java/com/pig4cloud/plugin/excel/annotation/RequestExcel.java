package com.pig4cloud.plugin.excel.annotation;

import com.pig4cloud.plugin.excel.handler.DefaultAnalysisEventListener;
import com.pig4cloud.plugin.excel.handler.ListAnalysisEventListener;

import java.lang.annotation.*;

/**
 * 导入excel
 *
 * @author lengleng
 * @author L.cm
 * @date 2021/4/16
 */
@Documented
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestExcel {

	/**
	 * 读取的监听器类
	 * @return readListener
	 */
	Class<? extends ListAnalysisEventListener<?>> readListener() default DefaultAnalysisEventListener.class;

}
