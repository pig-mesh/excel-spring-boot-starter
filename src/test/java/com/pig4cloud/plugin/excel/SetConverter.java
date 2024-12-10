package com.pig4cloud.plugin.excel;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * 集合转换
 *
 * @author L.cm
 */
public class SetConverter implements Converter<Set<?>> {

	private final ConversionService conversionService;

	SetConverter() {
		this.conversionService = DefaultConversionService.getSharedInstance();
	}

	@Override
	public Class<?> supportJavaTypeKey() {
		return Set.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public Set<?> convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		String[] value = StringUtils.delimitedListToStringArray(cellData.getStringValue(), ",");
		return (Set<?>) conversionService.convert(value, TypeDescriptor.valueOf(String[].class),
				new TypeDescriptor(contentProperty.getField()));
	}

	@Override
	public WriteCellData<String> convertToExcelData(Set<?> value, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		return new WriteCellData<>(StringUtils.collectionToCommaDelimitedString(value));
	}

}
