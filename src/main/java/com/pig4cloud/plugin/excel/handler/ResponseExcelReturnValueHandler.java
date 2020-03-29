package com.pig4cloud.plugin.excel.handler;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 处理@ResponseExcel 返回值
 *
 * @author lengleng
 */
@Slf4j
public class ResponseExcelReturnValueHandler implements HandlerMethodReturnValueHandler {

	/**
	 * 只处理@ResponseExcel 声明的方法
	 *
	 * @param parameter 方法签名
	 * @return 是否处理
	 */
	@Override
	public boolean supportsReturnType(MethodParameter parameter) {
		return parameter.getMethodAnnotation(ResponseExcel.class) != null;
	}

	/**
	 * 处理逻辑
	 *
	 * @param o                返回参数
	 * @param parameter        方法签名
	 * @param mavContainer     上下文容器
	 * @param nativeWebRequest 上下文
	 * @throws Exception 处理异常
	 */
	@Override
	public void handleReturnValue(Object o, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest) throws Exception {
		/* check */
		HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
		Assert.state(response != null, "No HttpServletResponse");
		ResponseExcel responseExcel = parameter.getMethodAnnotation(ResponseExcel.class);
		Assert.state(responseExcel != null, "No @ResponseExcel");
		mavContainer.setRequestHandled(true);

		/* return value check */
		if (!(o instanceof List)) {
			String msg = "return value is null or not support type, can not build excel";
			log.warn(msg);
			response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			response.getWriter().write(msg);
			response.getWriter().flush();
			return;
		}
		List list = (List) o;
		String fileName = String.format("%s_%s%s"
			, URLEncoder.encode(responseExcel.name(), "UTF-8")
			, LocalDateTime.now(), responseExcel.suffix());
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);

		// 若是多sheet List<List<>>
		if (list.get(0) instanceof List) {
			ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()
				, ((List) list.get(0)).get(0).getClass()).build();
			String[] sheets = responseExcel.sheet();
			for (int i = 0; i < sheets.length; i++) {
				//创建sheet
				WriteSheet sheet = EasyExcel.writerSheet(i, sheets[i]).build();
				// 写入sheet
				excelWriter.write((List) list.get(i), sheet);
			}
			excelWriter.finish();
		} else {
			EasyExcel.write(response.getOutputStream(), list.get(0)
				.getClass()).sheet(responseExcel.sheet()[0]).doWrite(list);
		}
	}
}
