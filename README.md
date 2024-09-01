以下是基于你提供的内容生成的开源项目 `excel-spring-boot-starter` 的 README 示例：

---

# Excel Spring Boot Starter

`excel-spring-boot-starter` 是一个基于 `EasyExcel` 实现的 Spring Boot Starter，用于简化 Excel 的读写操作。`EasyExcel` 是一个 Java 开源项目，旨在以尽可能低的内存消耗实现对 Excel 文件的读写。通过 `EasyExcel`，你可以在仅使用 64M 内存的情况下，在 1 分钟内读取 75M（46 万行，25 列）的 Excel 文件。

- 更多详细的使用说明，请参考文档：[https://www.yuque.com/pig4cloud/ogf9nv](https://www.yuque.com/pig4cloud/ogf9nv)

## 功能概述

- 轻松集成到 Spring Boot 项目中，快速实现 Excel 文件的导入和导出。
- 通过注解配置导入和导出的 Excel 文件格式。
- 提供了简洁易用的 API，极大地减少了手动处理 Excel 文件的工作量。

## 依赖引用

项目已经上传至 Maven 中央仓库，只需引入以下依赖即可使用：

| 版本    | 支持版本            |
|-------|-------------------|
| 3.3.1 | 适配 Spring Boot 3.x |
| 1.2.7 | 适配 Spring Boot 2.x |

在 `pom.xml` 中添加以下依赖：

```xml
<dependency>
  <groupId>com.pig4cloud.excel</groupId>
  <artifactId>excel-spring-boot-starter</artifactId>
  <version>${lastVersion}</version>
</dependency>
```

## 导入 Excel

### 控制器示例

你可以通过在接口方法中使用 `@RequestExcel` 注解来接收上传的 Excel 文件并将其解析为 Java 对象列表：

```java
@PostMapping("/upload")
public void upload(@RequestExcel List<DemoData> dataList, BindingResult bindingResult) {
  // JSR 303 校验通用校验获取失败的数据
  List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();
}
```

### 实体类定义

需要先定义与 Excel 表格对应的实体类，并使用 `@ExcelProperty` 注解来标注 Excel 列的索引：

```java
@Data
public class Demo {
  @ExcelProperty(index = 0)
  private String username;

  @ExcelProperty(index = 1)
  private String password;
}
```

### 示例表格

下图展示了与上述实体类对应的 Excel 表格：

![Example Excel](https://minio.pigx.top/oss/1618560470.png)

## 导出 Excel

你只需在控制器方法中返回一个 `List`，并使用 `@ResponseExcel` 注解即可将数据导出为 Excel 文件：

```java
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseExcel {
  String name() default "";
  ExcelTypeEnum suffix() default ExcelTypeEnum.XLSX;
  String password() default "";
  Sheet[] sheets() default @Sheet(sheetName = "sheet1");
  boolean inMemory() default false;
  String template() default "";
  String[] include() default {};
  String[] exclude() default {};
  Class<? extends WriteHandler>[] writeHandler() default {};
  Class<? extends Converter>[] converter() default {};
  Class<? extends HeadGenerator> headGenerator() default HeadGenerator.class;
}
```

## 使用文档

更多详细的使用说明，请参考文档：[https://www.yuque.com/pig4cloud/ogf9nv](https://www.yuque.com/pig4cloud/ogf9nv)
