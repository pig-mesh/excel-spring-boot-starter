package com.pig4cloud.plugin.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Date and string converter
 *
 * @author L.cm
 */
public enum LocalDateStringConverter implements Converter<LocalDate> {
	/**
	 * 实例
	 */
	INSTANCE;

	@Override
	public Class supportJavaTypeKey() {
		return LocalDate.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
									   GlobalConfiguration globalConfiguration) throws ParseException {
		if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
			return LocalDate.parse(cellData.getStringValue());
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat());
			return LocalDate.parse(cellData.getStringValue(), formatter);
		}
	}

	@Override
	public CellData convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
									   GlobalConfiguration globalConfiguration) {
		if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
			return new CellData(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat());
			return new CellData(value.format(formatter));
		}
	}
}
