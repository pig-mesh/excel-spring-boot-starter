package com.pig4cloud.plugin.excel.enums;

import com.pig4cloud.plugin.excel.handler.DictDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootApplication(scanBasePackages = "com.pig4cloud.plugin.excel")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * 单元测试，写出excel 字段固定枚举 enum
	 * @throws Exception
	 */
	@Test
	void testFileDownloadAndSave() throws Exception {
		MvcResult result = mockMvc.perform(get("/demo/test")).andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = result.getResponse();
		byte[] fileContent = response.getContentAsByteArray();

		// 验证响应不为空
		assertThat(fileContent).isNotEmpty();

		// 保存文件到 resources 目录
		saveFileToResources("downloaded-example.xlsx", fileContent);
	}

	@Autowired
	private DictDataProvider dictDataProvider;

	/**
	 * 单元测试，写出excel 字段指定枚举 type
	 * @throws Exception
	 */
	@Test
	void test2FileDownloadAndSave() throws Exception {
		dictDataProvider.addDict("sex_type", "0", "男");
		dictDataProvider.addDict("sex_type", "1", "女");

		MvcResult result = mockMvc.perform(get("/demo/test2")).andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = result.getResponse();
		byte[] fileContent = response.getContentAsByteArray();

		// 验证响应不为空
		assertThat(fileContent).isNotEmpty();

		// 保存文件到 resources 目录
		saveFileToResources("downloaded-example.xlsx", fileContent);
	}

	private void saveFileToResources(String filename, byte[] content) throws IOException {
		// 获取 resources 目录路径
		File resourceDirectory = new File("src/test/resources");
		if (!resourceDirectory.exists()) {
			resourceDirectory.mkdirs();
		}

		// 创建目标文件
		File outputFile = new File(resourceDirectory, filename);
		try (FileOutputStream fos = new FileOutputStream(outputFile)) {
			fos.write(content);
		}

		// 验证文件是否成功写入
		assertThat(outputFile.exists()).isTrue();
		assertThat(outputFile.length()).isEqualTo(content.length);
	}

	@Test
	void testFileUpload() throws Exception {

		ClassPathResource classPathResource = new ClassPathResource("tmp/enums.xlsx");

		// 创建一个模拟的 Excel 文件
		MockMultipartFile mockFile = new MockMultipartFile("file", // 参数名称，应该与 Controller
				// 中接收的文件参数名称一致
				"enums.xlsx", // 上传的文件名
				MediaType.MULTIPART_FORM_DATA_VALUE, // 文件类型
				classPathResource.getContentAsByteArray() // 文件内容，可以根据需要替换为实际的Excel内容
		);

		// 模拟文件上传请求
		MvcResult result = mockMvc.perform(multipart("/demo/upload") // 上传文件的URL
			.file(mockFile) // 添加文件
			.contentType(MediaType.MULTIPART_FORM_DATA) // 设置内容类型为 multipart/form-data
		)
			.andExpect(status().isOk()) // 验证返回的 HTTP 状态码是 200 OK
			.andReturn();

		// 这里可以进一步验证上传处理后的结果，例如返回的消息、数据库中的数据等
	}

}
