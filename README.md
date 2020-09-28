## EasyExcel

EasyExcel是一个基于Java的简单、省内存的读写Excel的开源项目。在尽可能节约内存的情况下支持读写百M的Excel。
64M内存1分钟内读取75M(46W行25列)的Excel,当然还有急速模式能更快，但是内存占用会在100M多一点

![](http://pigx.vip/20200331165749_w0DXBK_Screenshot.jpeg)  

## spring boot stater依赖

- 方便在 web 环境下使用 `easyexcel` ，已上传至 maven 仓库
```xml
<dependency>
    <groupId>com.pig4cloud.excel</groupId>
    <artifactId>excel-spring-boot-starter</artifactId>
    <version>0.0.7</version>
</dependency>
```

## 使用方法

只需要在 `Controller` 层返回 List 并增加 `@ResponseExcel`注解即可

```java
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseExcel {
	String name() default "";
	ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;
	String password() default "";
	String[] sheet() default {};
	boolean inMemory() default false;
	String template() default "";
	String[] include() default {};
	String[] exclude() default {};
	Class<? extends WriteHandler>[] writeHandler() default {};
	Class<? extends Converter>[] converter() default {};
}
```

## 基础用法

- 返回单 `sheet`, 全部字段导出
```java
@ResponseExcel(name = "lengleng", sheet = "demoList")
@GetMapping("/e1")
public List<DemoData> e1() {
    List<DemoData> dataList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
        DemoData data = new DemoData();
        data.setUsername("tr1" + i);
        data.setPassword("tr2" + i);
        dataList.add(data);
    }
    return dataList;
}
// 实体对象

@Data
public class DemoData {
	private String username;
	private String password;
}

```

![](http://pigx.vip/20200331162637_DVMcXW_Screenshot.jpeg)

- 自定义字段属性

```java
@Data
public class DemoData {
    @ColumnWidth(50)  // 定义宽度
	@ExcelProperty("用户名") // 定义列名称
    @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 40)
	private String username;
	@ExcelProperty("密码")
	private String password;
}
```

![](http://pigx.vip/20200331163948_E91zjM_Screenshot.jpeg)

-  忽略部分字段

```java
@Data
public class DemoData {
    @ColumnWidth(50)  // 定义宽度
	@ExcelProperty("用户名") // 定义列名称
    @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 40)
	private String username;
	@ExcelProperty("密码")
	private String password;
}
```
![](http://pigx.vip/20200331164144_l2gwfD_Screenshot.jpeg)

## 导出多sheet

```java
@ResponseExcel(name = "lengleng", sheet = {"第一个sheet","第二个sheet"})
@GetMapping("/e1")
public List<List<DemoData>> e1() {
    List<List<DemoData>> lists = new ArrayList<>();
    lists.add(list());
    lists.add(list());
    return lists;
}
```
![](http://pigx.vip/20200331164527_sbYDsC_Screenshot.jpeg)

## 设置导出加密码
```java
	@ResponseExcel(name = "lengleng", sheet = "sheetName",password = "lengleng")
	@GetMapping("/e1")
	public List<List<DemoData>> e1() {
		List<List<DemoData>> lists = new ArrayList<>();
		lists.add(list());
		lists.add(list());
		return lists;
	}

```
![](http://pigx.vip/20200331164945_6fsEsG_Screenshot.jpeg)

## 高级用法模板导出

```java
@ResponseExcel(name = "模板测试excel", sheet = "sheetName",template = "example.xlsx")
@GetMapping("/e1")
public List<DemoData> e1() {
    return list();
}
```

## 其他用法

- 理论上支持 [alibaba/easyexcel](https://github.com/alibaba/easyexcel) v2.2.6 版本的大部分配置
- 支持 [alibaba/easyexcel](https://www.yuque.com/easyexcel/doc/write) 原生的配置注解
- [github stater 地址，可fork 魔改](https://github.com/pigxcloud/excel-spring-boot-starter)
