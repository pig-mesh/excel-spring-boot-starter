package com.pig4cloud.plugin.excel.aop;

import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.processor.NameProcessor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lengleng
 * @date 2020/3/29
 */
@Aspect
@RequiredArgsConstructor
public class DynamicNameAspect {

	public static final String EXCEL_NAME_KEY = "__EXCEL_NAME_KEY__";

	public static final String EXCEL_INCLUDES_KEY = "__EXCEL_INCLUDES_KEY__";

	private final NameProcessor processor;

	@Before("@annotation(excel)")
	public void around(JoinPoint point, ResponseExcel excel) {
		MethodSignature ms = (MethodSignature) point.getSignature();

		String name = excel.name();
		// 当配置的 excel 名称为空时，取当前时间
		if (!StringUtils.hasText(name)) {
			name = LocalDateTime.now().toString();
		}
		else {
			name = processor.doDetermineName(point.getArgs(), ms.getMethod(), excel.name());
		}

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Objects.requireNonNull(requestAttributes).setAttribute(EXCEL_NAME_KEY, name, RequestAttributes.SCOPE_REQUEST);

		// 针对include 字段的表达式处理
		if (excel.include().length == 1) {
			String includeFields = processor.doDetermineName(point.getArgs(), ms.getMethod(), excel.include()[0]);

			if (!StringUtils.hasText(includeFields)) {
				return;
			}

			List<String> includes = Arrays.stream(includeFields.split(","))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toList());
			Objects.requireNonNull(requestAttributes)
				.setAttribute(EXCEL_INCLUDES_KEY, includes, RequestAttributes.SCOPE_REQUEST);
		}

	}

}
