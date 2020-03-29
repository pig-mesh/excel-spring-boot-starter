package com.pig4cloud.plugin.excel.handler;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.config.ExcelConfigProperties;
import com.pig4cloud.plugin.excel.kit.ExcelNameContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * @author lengleng
 * @date 2020/3/29
 */
@Component
@RequiredArgsConstructor
public class ManySheetWriteHandler implements SheetWriteHandler {
	private final ExcelConfigProperties configProperties;

	/**
	 * 只有元素是List 才返回true
	 *
	 * @param obj 返回对象
	 * @return
	 */
	@Override
	public boolean support(Object obj) {
		if (obj instanceof List) {
			List objList = (List) obj;
			if (objList.get(0) instanceof List) {
				return true;
			}
		}
		return false;
	}

	@Override
	@SneakyThrows
	public void export(Object obj, HttpServletResponse response, ResponseExcel responseExcel) {
		List objList = (List) obj;
		List eleList = (List) objList.get(0);
		String name = ExcelNameContextHolder.get();
		String fileName = String.format("%s%s", URLEncoder.encode(name, "UTF-8"), responseExcel.suffix().getValue());
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);

		ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream(), eleList.get(0)
			.getClass()).autoCloseStream(true).excelType(responseExcel.suffix()).inMemory(responseExcel.inMemory());

		if (StringUtils.hasText(responseExcel.password())) {
			writerBuilder.password(responseExcel.password());
		}

		if (responseExcel.include().length != 0) {
			writerBuilder.includeColumnFiledNames(Arrays.asList(responseExcel.include()));
		}

		if (responseExcel.exclude().length != 0) {
			writerBuilder.excludeColumnFiledNames(Arrays.asList(responseExcel.include()));
		}

		if (responseExcel.writeHandler().length != 0) {
			for (Class<? extends WriteHandler> clazz : responseExcel.writeHandler()) {
				writerBuilder.registerWriteHandler(clazz.newInstance());
			}
		}

		if (responseExcel.converter().length != 0) {
			for (Class<? extends Converter> clazz : responseExcel.converter()) {
				writerBuilder.registerConverter(clazz.newInstance());
			}
		}

		String[] sheets = responseExcel.sheet();
		if (StringUtils.hasText(responseExcel.template())) {
			ClassPathResource classPathResource = new ClassPathResource(configProperties.getTemplatePath()
				+ File.separator + responseExcel.template());
			InputStream inputStream = classPathResource.getInputStream();
			writerBuilder.withTemplate(inputStream);
		}

		ExcelWriter excelWriter = writerBuilder.build();


		for (int i = 0; i < sheets.length; i++) {
			//创建sheet
			WriteSheet sheet = null;
			if (StringUtils.hasText(responseExcel.template())) {
				sheet = EasyExcel.writerSheet(i).build();
			} else {
				sheet = EasyExcel.writerSheet(i, sheets[i]).build();
			}

			// 写入sheet
			excelWriter.write((List) objList.get(i), sheet);
		}
		excelWriter.finish();
	}
}
