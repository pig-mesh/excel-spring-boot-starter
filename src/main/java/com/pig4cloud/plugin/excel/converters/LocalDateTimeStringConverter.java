package com.pig4cloud.plugin.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date and string converter
 *
 * @author L.cm
 */
public enum LocalDateTimeStringConverter implements Converter<LocalDateTime> {
	/**
	 * 实例
	 */
	INSTANCE;

	@Override
	public Class supportJavaTypeKey() {
		return LocalDateTime.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
										   GlobalConfiguration globalConfiguration) throws ParseException {
		if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
			return LocalDateTime.parse(cellData.getStringValue());
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat());
			return LocalDateTime.parse(cellData.getStringValue(), formatter);
		}
	}

	@Override
	public CellData convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
									   GlobalConfiguration globalConfiguration) {
		if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
			return new CellData(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat());
			return new CellData(value.format(formatter));
		}
	}
}
