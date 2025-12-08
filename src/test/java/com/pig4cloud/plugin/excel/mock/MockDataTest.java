package com.pig4cloud.plugin.excel.mock;

import cn.idev.excel.EasyExcel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Mock数据生成测试类 - 生成100万条数据
 *
 * @author lengleng
 * @date 2025/12/08
 */
class MockDataTest {

	private static final int TOTAL_COUNT = 1000000;

	private static final String[] FIRST_NAMES = { "张", "李", "王", "刘", "陈", "杨", "赵", "黄", "周", "吴", "徐", "孙", "胡", "朱",
			"高", "林", "何", "郭", "马", "罗" };

	private static final String[] LAST_NAMES = { "伟", "芳", "娜", "秀英", "敏", "静", "丽", "强", "磊", "军", "洋", "勇", "艳", "杰",
			"涛", "明", "超", "秀兰", "霞", "平", "刚", "桂英" };

	private static final String[] DEPARTMENTS = { "技术部", "产品部", "运营部", "市场部", "人力资源部", "财务部", "销售部", "客服部", "设计部",
			"行政部" };

	private static final String[] CITIES = { "北京", "上海", "广州", "深圳", "杭州", "南京", "成都", "武汉", "西安", "重庆" };

	private static final String[] DISTRICTS = { "朝阳区", "海淀区", "浦东新区", "天河区", "南山区", "西湖区", "江宁区", "武侯区", "雁塔区", "渝北区" };

	private final Random random = new Random();

	// @Test
	void generateMockData() {
		String fileName = System.getProperty("user.home") + File.separator + "mock_data_1000000.xlsx";

		long startTime = System.currentTimeMillis();
		System.out.println("开始生成 " + TOTAL_COUNT + " 条Mock数据...");

		// 生成数据
		List<MockData> dataList = new ArrayList<>(TOTAL_COUNT);
		for (int i = 1; i <= TOTAL_COUNT; i++) {
			MockData data = new MockData();
			data.setUserId((long) i);
			data.setUsername(generateRandomName());
			data.setEmail(generateRandomEmail(i));
			data.setAge(generateRandomAge());
			data.setPhone(generateRandomPhone());
			data.setAddress(generateRandomAddress());
			data.setDepartment(generateRandomDepartment());
			data.setSalary(generateRandomSalary());
			dataList.add(data);

			// 每1万条打印一次进度
			if (i % 10000 == 0) {
				System.out.println("已生成 " + i + " 条数据...");
			}
		}

		long generateTime = System.currentTimeMillis();
		System.out.println("数据生成完成,耗时: " + (generateTime - startTime) + "ms");

		// 写入Excel
		System.out.println("开始写入Excel文件: " + fileName);
		EasyExcel.write(fileName, MockData.class).sheet("用户数据").doWrite(dataList);

		long writeTime = System.currentTimeMillis();
		System.out.println("Excel写入完成,耗时: " + (writeTime - generateTime) + "ms");
		System.out.println("总耗时: " + (writeTime - startTime) + "ms");
		System.out.println("文件保存路径: " + fileName);
	}

	/**
	 * 生成随机姓名
	 */
	private String generateRandomName() {
		String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		// 有30%的概率生成三个字的名字
		if (random.nextInt(100) < 30) {
			String lastName2 = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
			return firstName + lastName + lastName2;
		}
		return firstName + lastName;
	}

	/**
	 * 生成随机邮箱
	 */
	private String generateRandomEmail(int id) {
		String[] domains = { "gmail.com", "qq.com", "163.com", "126.com", "sina.com", "outlook.com" };
		return "user" + id + "@" + domains[random.nextInt(domains.length)];
	}

	/**
	 * 生成随机年龄 (22-60岁)
	 */
	private Integer generateRandomAge() {
		return 22 + random.nextInt(39);
	}

	/**
	 * 生成随机手机号
	 */
	private String generateRandomPhone() {
		String[] prefixes = { "130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "150", "151", "152",
				"153", "155", "156", "157", "158", "159", "180", "181", "182", "183", "185", "186", "187", "188",
				"189" };
		String prefix = prefixes[random.nextInt(prefixes.length)];
		StringBuilder phone = new StringBuilder(prefix);
		for (int i = 0; i < 8; i++) {
			phone.append(random.nextInt(10));
		}
		return phone.toString();
	}

	/**
	 * 生成随机地址
	 */
	private String generateRandomAddress() {
		String city = CITIES[random.nextInt(CITIES.length)];
		String district = DISTRICTS[random.nextInt(DISTRICTS.length)];
		int streetNum = 1 + random.nextInt(999);
		int buildingNum = 1 + random.nextInt(50);
		int roomNum = 100 + random.nextInt(900);
		return city + district + "街道" + streetNum + "号" + buildingNum + "栋" + roomNum + "室";
	}

	/**
	 * 生成随机部门
	 */
	private String generateRandomDepartment() {
		return DEPARTMENTS[random.nextInt(DEPARTMENTS.length)];
	}

	/**
	 * 生成随机薪资 (5000-50000)
	 */
	private Double generateRandomSalary() {
		return 5000.0 + random.nextInt(45000);
	}

}
