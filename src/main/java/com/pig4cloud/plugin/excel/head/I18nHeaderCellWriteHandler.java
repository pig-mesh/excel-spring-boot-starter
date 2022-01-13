package com.pig4cloud.plugin.excel.head;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 对表头进行国际化处理
 *
 * @author hccake
 */
@RequiredArgsConstructor
public class I18nHeaderCellWriteHandler implements CellWriteHandler {

	/**
	 * 国际化消息源
	 */
	private final MessageSource messageSource;

	/**
	 * 国际化翻译
	 */
	private final PropertyPlaceholderHelper.PlaceholderResolver placeholderResolver;

	public I18nHeaderCellWriteHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
		this.placeholderResolver = placeholderName -> messageSource.getMessage(placeholderName, null,
				LocaleContextHolder.getLocale());
	}

	/**
	 * 占位符处理
	 */
	private final PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("{", "}");

	@Override
	public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
			Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
		if (isHead) {
			List<String> originHeadNameList = head.getHeadNameList();
			if (CollectionUtils.isNotEmpty(originHeadNameList)) {
				// 国际化处理
				List<String> i18nHeadNames = originHeadNameList.stream()
						.map(headName -> propertyPlaceholderHelper.replacePlaceholders(headName, placeholderResolver))
						.collect(Collectors.toList());
				head.setHeadNameList(i18nHeadNames);
			}
		}
	}

	@Override
	public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
			Head head, Integer relativeRowIndex, Boolean isHead) {

	}

	/**
	 * Called before create the cell
	 * @param context
	 */
	@Override
	public void beforeCellCreate(CellWriteHandlerContext context) {
		CellWriteHandler.super.beforeCellCreate(context);
	}

	/**
	 * Called after the cell is created
	 * @param context
	 */
	@Override
	public void afterCellCreate(CellWriteHandlerContext context) {
		CellWriteHandler.super.afterCellCreate(context);
	}

	/**
	 * Called after the cell data is converted
	 * @param context
	 */
	@Override
	public void afterCellDataConverted(CellWriteHandlerContext context) {
		CellWriteHandler.super.afterCellDataConverted(context);
	}

	/**
	 * Called after the cell data is converted
	 * @param writeSheetHolder
	 * @param writeTableHolder Nullable.It is null without using table writes.
	 * @param cellData Nullable.It is null in the case of add header.
	 * @param cell
	 * @param head Nullable.It is null in the case of fill data and without head.
	 * @param relativeRowIndex Nullable.It is null in the case of fill data.
	 * @param isHead It will always be false when fill data.
	 */
	@Override
	public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
			WriteCellData<?> cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
		CellWriteHandler.super.afterCellDataConverted(writeSheetHolder, writeTableHolder, cellData, cell, head,
				relativeRowIndex, isHead);
	}

	/**
	 * Called after all operations on the cell have been completed
	 * @param context
	 */
	@Override
	public void afterCellDispose(CellWriteHandlerContext context) {
		CellWriteHandler.super.afterCellDispose(context);
	}

	/**
	 * Called after all operations on the cell have been completed
	 * @param writeSheetHolder
	 * @param writeTableHolder Nullable.It is null without using table writes.
	 * @param cellDataList Nullable.It is null in the case of add header.There may be
	 * several when fill the data.
	 * @param cell
	 * @param head Nullable.It is null in the case of fill data and without head.
	 * @param relativeRowIndex Nullable.It is null in the case of fill data.
	 * @param isHead It will always be false when fill data.
	 */
	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
			List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
		CellWriteHandler.super.afterCellDispose(writeSheetHolder, writeTableHolder, cellDataList, cell, head,
				relativeRowIndex, isHead);
	}

}
