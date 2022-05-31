package com.pig4cloud.plugin.excel;

import com.alibaba.excel.converters.Converter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Configuration
@Import(value = { ListConverter.class, SetConverter.class })
class Config {

}

/**
 * 测试 Converter
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Config.class)
class ConverterTest {

	@Autowired
	ObjectProvider<List<Converter<?>>> converterProvider;

	@Test
	void test() {
		List<Converter<?>> ifAvailable = converterProvider.getIfAvailable();
		Assertions.assertFalse(ifAvailable == null || ifAvailable.isEmpty());
	}

}
