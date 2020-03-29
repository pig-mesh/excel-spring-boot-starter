package com.pig4cloud.plugin.excel;

import java.lang.annotation.*;

/**
 * `@ResponseExcel 注解`
 *
 * @author lengleng
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseExcel {

	String sheetName() default "default";

	String title() default "";

	String[] columnNames() default {};

	String[] classFieldNames();

	String fileName() default "";

	String fileSuffix() default ".xlsx";

}
