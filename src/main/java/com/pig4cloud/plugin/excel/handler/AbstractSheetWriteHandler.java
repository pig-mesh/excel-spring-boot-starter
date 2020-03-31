package com.pig4cloud.plugin.excel.handler;

import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.kit.ExcelException;
import com.pig4cloud.plugin.excel.kit.ExcelNameContextHolder;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author lengleng
 * @date 2020/3/31
 */
public abstract class AbstractSheetWriteHandler implements SheetWriteHandler {

	@Override
	public void check(ResponseExcel responseExcel) {
		if (!StringUtils.hasText(responseExcel.name())) {
			throw new ExcelException("@ResponseExcel name 配置不合法");
		}

		if (responseExcel.sheet().length == 0) {
			throw new ExcelException("@ResponseExcel sheet 配置不合法");
		}
	}

	@Override
	@SneakyThrows
	public void export(Object o, HttpServletResponse response, ResponseExcel responseExcel) {
		if (support(o)) {
			check(responseExcel);
			String name = ExcelNameContextHolder.get();
			String fileName = String.format("%s%s", URLEncoder.encode(name, "UTF-8"), responseExcel.suffix().getValue());
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("utf-8");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
			write(o, response, responseExcel);
		}
	}
}
