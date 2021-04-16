package com.pig4cloud.plugin.excel.annotation;

import java.lang.annotation.*;

/**
 * 导入excel
 *
 * @author lengleng
 * @date 2021/4/16
 */
@Documented
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestExcel {

}
