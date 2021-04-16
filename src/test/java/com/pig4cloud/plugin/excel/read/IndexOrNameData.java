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
public class IndexOrNameData {

	/**
	 * 读取第一列
	 */
	@ExcelProperty(index = 0)
	private String a;

	/**
	 * 读取第二列
	 */
	@ExcelProperty(index = 1)
	private String b;

	/**
	 * 读取第三列
	 */
	@ExcelProperty(index = 2)
	private String c;

}
