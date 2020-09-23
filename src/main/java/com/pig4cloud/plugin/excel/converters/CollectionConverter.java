package com.pig4cloud.plugin.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 集合转换
 *
 * @author L.cm
 */
public enum CollectionConverter implements Converter<Collection<?>> {
	/**
	 * 实例
	 */
	INSTANCE(DefaultConversionService.getSharedInstance());

	private final ConversionService conversionService;

	CollectionConverter(ConversionService sharedInstance) {
		this.conversionService = sharedInstance;
	}

	@Override
	public Class<?> supportJavaTypeKey() {
		return Object[].class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public Collection<?> convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
		String[] value = StringUtils.delimitedListToStringArray(cellData.getStringValue(), ",");
		return (Collection<?>) conversionService.convert(value, TypeDescriptor.valueOf(String[].class), new TypeDescriptor(contentProperty.getField()));
	}

	@Override
	public CellData<String> convertToExcelData(Collection<?> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
		return new CellData<>(StringUtils.collectionToCommaDelimitedString(value));
	}

}
