package com.pig4cloud.plugin.excel.vo;

import java.util.Arrays;
import java.util.Objects;

/**
 * 数据字典枚举接口。系统字典枚举接口
 *
 * @author HouKunLin
 */
public interface DictEnum {

	/**
	 * 字典值
	 * @return 字典值
	 */
	String getValue();

	/**
	 * 字典文本
	 * @return 字典文本
	 */
	String getLabel();

	/**
	 * 判断字典值是否相等
	 * @param o 传入的值，可为当前的枚举对象
	 * @return 判断是否相等
	 */
	default boolean eq(Object o) {
		return this == o || Objects.equals(o, getValue());
	}

	/**
	 * 根据字典值获取字典文本
	 * @param enums 枚举类的所有枚举值
	 * @param value 字典值
	 * @return 对应的字典文本
	 */
	static <E extends DictEnum> String getLabelByValue(E[] enums, String value) {
		return Arrays.stream(enums)
			.filter(e -> Objects.equals(e.getValue(), value))
			.map(DictEnum::getLabel)
			.findFirst()
			.orElse(null);
	}

	/**
	 * 根据字典文本获取字典值
	 * @param enums 枚举类的所有枚举值
	 * @param label 字典文本
	 * @return 对应的字典值
	 */
	static <E extends DictEnum> String getValueByLabel(E[] enums, String label) {
		return Arrays.stream(enums)
			.filter(e -> Objects.equals(e.getLabel(), label))
			.map(DictEnum::getValue)
			.findFirst()
			.orElse(null);
	}

}
