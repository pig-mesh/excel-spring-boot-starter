package com.pig4cloud.plugin.excel.aop;

import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.kit.ExcelNameContextHolder;
import com.pig4cloud.plugin.excel.processor.NameProcessor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

/**
 * @author lengleng
 * @date 2020/3/29
 */
@Aspect
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class DynamicNameAspect {
	private final NameProcessor processor;

	@Before("@annotation(excel)")
	public void around(JoinPoint point, ResponseExcel excel) {
		MethodSignature ms = (MethodSignature) point.getSignature();
		String name = processor.doDetermineName(point.getArgs(), ms.getMethod(), excel.name());
		ExcelNameContextHolder.set(name);
	}
}
