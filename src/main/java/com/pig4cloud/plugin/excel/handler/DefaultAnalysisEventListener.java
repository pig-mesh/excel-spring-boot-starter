package com.pig4cloud.plugin.excel.handler;

import com.alibaba.excel.context.AnalysisContext;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 默认的 AnalysisEventListener
 *
 * @author lengleng
 * @author L.cm
 * @date 2021/4/16
 */
@Slf4j
public class DefaultAnalysisEventListener extends ListAnalysisEventListener<Object> {

	private final List<Object> list = new ArrayList<>();

	private final Map<Long, Set<ConstraintViolation<Object>>> errors = new ConcurrentHashMap<>();

	private Long lineNum = 1L;

	@Override
	public void invoke(Object o, AnalysisContext analysisContext) {
		lineNum++;

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(o);
		if (!violations.isEmpty()) {
			errors.put(lineNum, violations);
		}
		else {
			list.add(o);
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		log.debug("Excel read analysed");
	}

	@Override
	public List<Object> getList() {
		return list;
	}

	@Override
	public Map<Long, Set<ConstraintViolation<Object>>> getErrors() {
		return errors;
	}

	/**
	 * 格式化 bean 校验错误信息
	 * @param constraintViolations 错误
	 * @return string
	 */
	private String toString(Set<? extends ConstraintViolation<?>> constraintViolations) {
		return constraintViolations.stream()
				.map(cv -> cv == null ? "null"
						: String.format("行号【%s】,字段【%s】校验失败,错误提示【%s】", lineNum, cv.getPropertyPath(), cv.getMessage()))
				.collect(Collectors.joining(", "));
	}

}
