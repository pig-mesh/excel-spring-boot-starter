package com.pig4cloud.plugin.excel.handler;

import cn.idev.excel.context.AnalysisContext;
import com.pig4cloud.plugin.excel.annotation.ExcelLine;
import com.pig4cloud.plugin.excel.kit.Validators;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

	private final List<ErrorMessage> errorMessageList = new ArrayList<>();

	private Long lineNum = 1L;

	@Override
	public void invoke(Object o, AnalysisContext analysisContext) {
		lineNum++;

		Set<ConstraintViolation<Object>> violations = Validators.validate(o);
		if (!violations.isEmpty()) {
			Set<String> messageSet = violations.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.toSet());
			errorMessageList.add(new ErrorMessage(lineNum, messageSet));
		}
		else {
			// 获取所有的字段
			ReflectionUtils.doWithFields(o.getClass(), field -> {
				// 如果字段上有 @ExcelLine 注解，并且类型为 Long，则设置行号
				if (field.isAnnotationPresent(ExcelLine.class) && field.getType() == Long.class) {
					// 设置字段可访问
					ReflectionUtils.makeAccessible(field);
					// 设置字段值
					field.set(o, lineNum);
				}
			});
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
	public List<ErrorMessage> getErrors() {
		return errorMessageList;
	}

}
