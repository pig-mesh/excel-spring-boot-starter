package com.pig4cloud.plugin.excel.read;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 指定列的下标或者列名测试实体
 *
 * @author lengleng
 * @date 2021/4/16
 */
@Data
public class IndexOrNameData1 {

	/**
	 * 读取第一列
	 */
	@ExcelProperty("列1")
	private String a;

	/**
	 * 读取第二列
	 */
	@ExcelProperty("列2")
	private String b;

	/**
	 * 读取第三列
	 */
	@ExcelProperty("列3")
	private String c;

}
