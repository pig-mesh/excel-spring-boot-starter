package com.pig4cloud.plugin.excel.handler;

import com.pig4cloud.plugin.excel.vo.DictEnum;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * dict 数据提供程序
 *
 * @author lengleng
 * @date 2024/08/30
 */
public interface DictDataProvider {

	static Map<String, DictEnum[]> cache = new HashMap<>();

	/**
	 * 获取缓存
	 * @return {@link Map }<{@link String }, {@link DictEnum[] }>
	 */
	default Map<String, DictEnum[]> getCache() {
		return cache;
	}

	/**
	 * 获取 dict
	 * @param type 类型
	 * @return {@link DictEnum[] }
	 */
	default DictEnum[] getDict(String type) {
		return cache.get(type);
	}

	/**
	 * 添加 dict
	 * @param type 类型
	 * @param key key
	 * @param value value
	 */
	default void addDict(String type, String key, String value) {
		// 1. 获取当前已有的 DictEnum 数组
		DictEnum[] existingEnums = cache.get(type);

		// 2. 创建新的 DictEnum 对象
		DictEnum newEnum = new DictEnum() {
			@Override
			public String getValue() {
				return key;
			}

			@Override
			public String getLabel() {
				return value;
			}
		};

		// 3. 使用 ArrayUtils.add 将新元素添加到数组中
		DictEnum[] newEnums = ArrayUtils.add(existingEnums, newEnum);

		// 4. 将新数组放入 cache 中
		cache.put(type, newEnums);
	}

	/**
	 * 删除 dict
	 * @param type 类型
	 */
	default void delDict(String type) {
		cache.remove(type);
	}

	/**
	 * 重新加载 dict
	 */
	default void clear() {
		cache.clear();
	}

}
