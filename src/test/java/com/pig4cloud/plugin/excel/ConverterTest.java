package com.pig4cloud.plugin.excel;

import com.alibaba.excel.converters.Converter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试 Converter
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Void.class)
@Import(value = {
	ListConverter.class,
	SetConverter.class
})
public class ConverterTest {

	@Autowired
	ObjectProvider<List<Converter<?>>> converterProvider;

	@Test
	public void test() {
		List<Converter<?>> ifAvailable = converterProvider.getIfAvailable();
		Assert.assertFalse(ifAvailable.isEmpty());
	}

}
