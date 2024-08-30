package com.pig4cloud.plugin.excel.enums;

import com.pig4cloud.plugin.excel.vo.DictEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author lengleng
 * @date 2024/8/30
 */
@Getter
@RequiredArgsConstructor
// 必须继承 DictEnum
public enum SexEnum implements DictEnum {

	MALE("0", "男"),
	FEMALE("1", "女");

	// 必须有的字段
	private final String value;
	// 必须有的字段
	private final String label;
}
