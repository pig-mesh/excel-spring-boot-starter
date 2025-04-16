package com.pig4cloud.plugin.excel.enums;

import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lengleng
 * @date 2024/8/30
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

	@ResponseExcel(include = "#str")
	@RequestMapping("/test")
	public List<IndexOrNameData2> test(String str) {
		List<IndexOrNameData2> list = new ArrayList<>();
		IndexOrNameData2 indexOrNameData2 = new IndexOrNameData2();
		indexOrNameData2.setSex("0");

		IndexOrNameData2 indexOrNameData3 = new IndexOrNameData2();
		indexOrNameData3.setSex("1");
		list.add(indexOrNameData2);
		list.add(indexOrNameData3);
		return list;
	}

	@ResponseExcel
	@RequestMapping("/test2")
	public List<IndexOrNameData3> test2() {
		List<IndexOrNameData3> list2 = new ArrayList<>();
		IndexOrNameData3 indexOrNameData2 = new IndexOrNameData3();
		indexOrNameData2.setSex("0");

		IndexOrNameData3 indexOrNameData3 = new IndexOrNameData3();
		indexOrNameData3.setSex("1");
		list2.add(indexOrNameData2);
		list2.add(indexOrNameData3);
		return list2;
	}

	@PostMapping("/upload")
	public void upload(@RequestExcel List<IndexOrNameData3> dataList, BindingResult bindingResult) {
		// JSR 303 校验通用校验获取失败的数据
		List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
	}

}
