package com.pig4cloud.plugin.excel.enums;

import com.pig4cloud.plugin.excel.handler.DictDataProvider;
import com.pig4cloud.plugin.excel.vo.DictEnum;

/**
 * @author lengleng
 * @date 2024/8/31
 */
// @Service
public class DictDataProviderImpl implements DictDataProvider {

	/**
	 * 获取 dict
	 * @param type 类型
	 * @return {@link DictEnum[] }
	 */
	@Override
	public DictEnum[] getDict(String type) {
		// 查询数据库组装返回对应的字典数据
		return DictEnum.builder().add("1", "男").add("0", "女").build();
	}

}
