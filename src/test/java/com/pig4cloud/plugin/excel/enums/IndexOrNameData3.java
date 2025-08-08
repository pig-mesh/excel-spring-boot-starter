package com.pig4cloud.plugin.excel.enums;

import cn.idev.excel.annotation.ExcelProperty;
import com.pig4cloud.plugin.excel.annotation.DictTypeProperty;
import com.pig4cloud.plugin.excel.annotation.ExcelLine;
import lombok.Data;

/**
 * 指定列的下标或者列名测试实体
 *
 * @author lengleng
 * @date 2021/4/16
 */
@Data
public class IndexOrNameData3 {

	/**
	 * 读取第一列
	 */
	@ExcelProperty(value = "列1")
	@DictTypeProperty("sex_txype")
	private String sex;

	@ExcelLine
	private Long lineNum;

}
