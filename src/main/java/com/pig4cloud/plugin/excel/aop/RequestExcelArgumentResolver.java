package com.pig4cloud.plugin.excel.aop;

import com.alibaba.excel.EasyExcel;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.handler.SheetAnalysisEventListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 上传excel 解析注解
 *
 * @author lengleng
 * @date 2021/4/16
 */
@Slf4j
public class RequestExcelArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestExcel.class);
	}

	@Override
	@SneakyThrows
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer,
			NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) {
		if (!parameter.getParameterType().isAssignableFrom(List.class)) {
			log.warn("request excel fail resolver error , param not List {}", parameter.getParameterType());
			return null;
		}

		// 获取请求文件流
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		ServletInputStream inputStream = request.getInputStream();
		SheetAnalysisEventListener sheetAnalysisEventListener = new SheetAnalysisEventListener();

		// 获取目标类型
		ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameter().getParameterizedType();
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

		// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
		EasyExcel.read(inputStream, (Class) actualTypeArguments[0], sheetAnalysisEventListener).sheet().doRead();
		return sheetAnalysisEventListener.getList();
	}

}
