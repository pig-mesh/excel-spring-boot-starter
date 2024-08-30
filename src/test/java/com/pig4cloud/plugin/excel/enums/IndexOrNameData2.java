package com.pig4cloud.plugin.excel.enums;

import com.alibaba.excel.annotation.ExcelProperty;
import com.pig4cloud.plugin.excel.annotation.DictTypeProperty;
import lombok.Data;

/**
 * 指定列的下标或者列名测试实体
 *
 * @author lengleng
 * @date 2021/4/16
 */
@Data
public class IndexOrNameData2 {

	/**
	 * 读取第一列
	 */
	@ExcelProperty(value = "列1")
	// 指定对应的枚举类 （字符串）
	@DictTypeProperty(enums = SexEnum.class)
	private String sex;

}
