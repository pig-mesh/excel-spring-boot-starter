package com.pig4cloud.plugin.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.pig4cloud.plugin.excel.annotation.DictTypeProperty;
import com.pig4cloud.plugin.excel.handler.DictDataProvider;
import com.pig4cloud.plugin.excel.kit.SpringContextKit;
import com.pig4cloud.plugin.excel.vo.DictEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * dict 转换器
 *
 * @author lengleng
 * @date 2024/08/30
 */
@Slf4j
public enum DictTypeConvert implements Converter<String> {

	/**
	 * 实例
	 */
	INSTANCE;

	/**
	 * 支持 Java Type Key
	 * @return {@link Class }
	 */
	@Override
	public Class supportJavaTypeKey() {
		return String.class;
	}

	/**
	 * 支持 Excel 键入键
	 * @return {@link CellDataTypeEnum }
	 */
	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	public static final Map<String, DictEnum[]> cache = new HashMap<>();

	/**
	 * 转换为 Java 数据
	 * @param cellData 单元格数据
	 * @param contentProperty content 属性
	 * @param globalConfiguration 全局配置
	 * @return {@link String }
	 * @throws Exception 异常
	 */
	@Override
	public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) throws Exception {
		if (contentProperty == null) {
			return cellData.getStringValue();
		}
		Field field = contentProperty.getField();

		DictTypeProperty declaredAnnotation = field.getDeclaredAnnotation(DictTypeProperty.class);
		if (Objects.isNull(declaredAnnotation)) {
			return cellData.getStringValue();
		}

		if (declaredAnnotation.enums().length != 0) {
			DictEnum[] enums = declaredAnnotation.enums()[0].getEnumConstants();
			return DictEnum.getValueByLabel(enums, cellData.getStringValue());
		}

		DictEnum[] cacheEnums = cache.get(declaredAnnotation.value());
		if (cacheEnums != null) {
			return DictEnum.getValueByLabel(cacheEnums, cellData.getStringValue());
		}

		Map<String, DictDataProvider> beansOfType = SpringContextKit.getBeansOfType(DictDataProvider.class);
		if (MapUtils.isEmpty(beansOfType)) {
			log.error("请实现 DictDataProvider 接口，提供字典数据: https://www.yuque.com/pig4cloud/ogf9nv/pkxq8xqe9r6ax4fg");
			return cellData.getStringValue();
		}

		DictDataProvider dictDataProvider = SpringContextKit.getBean(DictDataProvider.class);
		DictEnum[] dictEnums = dictDataProvider.getDict(declaredAnnotation.value());
		if (dictEnums == null) {
			log.warn("字典数据为空:{}，自动字典转换失败", declaredAnnotation.value());
			return cellData.getStringValue();
		}
		cache.put(declaredAnnotation.value(), dictEnums);
		return DictEnum.getValueByLabel(dictEnums, cellData.getStringValue());
	}

	/**
	 * 转换为 Excel 数据
	 * @param value 价值
	 * @param contentProperty content 属性
	 * @param globalConfiguration 全局配置
	 * @return {@link WriteCellData }<{@link String }>
	 */
	@Override
	public WriteCellData<String> convertToExcelData(String value, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) throws Exception {
		Field field = contentProperty.getField();

		DictTypeProperty declaredAnnotation = field.getDeclaredAnnotation(DictTypeProperty.class);
		if (Objects.isNull(declaredAnnotation)) {
			return new WriteCellData<>(value);
		}

		if (declaredAnnotation.enums().length != 0) {
			DictEnum[] enums = declaredAnnotation.enums()[0].getEnumConstants();
			return new WriteCellData<>(DictEnum.getLabelByValue(enums, value));
		}

		DictEnum[] cacheEnums = cache.get(declaredAnnotation.value());
		if (cacheEnums != null) {
			String labelByValue = DictEnum.getLabelByValue(cacheEnums, value);
			if (labelByValue != null) {
				return new WriteCellData<>(labelByValue);
			}
		}

		Map<String, DictDataProvider> beansOfType = SpringContextKit.getBeansOfType(DictDataProvider.class);
		if (MapUtils.isEmpty(beansOfType)) {
			log.error("请实现 DictDataProvider 接口，提供字典数据: https://www.yuque.com/pig4cloud/ogf9nv/pkxq8xqe9r6ax4fg");
			return new WriteCellData<>(value);
		}

		DictDataProvider dictDataProvider = SpringContextKit.getBean(DictDataProvider.class);
		DictEnum[] dictEnums = dictDataProvider.getDict(declaredAnnotation.value());

		if (dictEnums == null) {
			log.warn("字典数据为空:{}，自动字典转换失败", declaredAnnotation.value());
			return new WriteCellData<>(value);
		}

		String labelByValue = DictEnum.getLabelByValue(dictEnums, value);
		if (labelByValue != null) {
			cache.put(declaredAnnotation.value(), dictEnums);
			return new WriteCellData<>(labelByValue);
		}
		return new WriteCellData<>(value);
	}

}
