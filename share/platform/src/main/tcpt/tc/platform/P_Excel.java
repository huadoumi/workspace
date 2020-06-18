package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.io.File;
import java.io.IOException;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaList;
import cn.com.agree.jxl.Cell;
import cn.com.agree.jxl.Sheet;
import cn.com.agree.jxl.Workbook;
import cn.com.agree.jxl.read.biff.BiffException;
import cn.com.agree.jxl.write.Label;
import cn.com.agree.jxl.write.WritableSheet;
import cn.com.agree.jxl.write.WritableWorkbook;

/**
 * Excel基本操作类组件
 * 
 * @date 2016-07-27 14:20:52
 */
@ComponentGroup(level = "平台", groupName = "Excel操作类组件")
public class P_Excel {

	/**
	 * @param fileName
	 *            入参|Excel文件名|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "fileName", comment = "Excel文件名", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "创建Excel文件", style = "判断型", type = "同步组件", comment = "创建Excel文件", date = "2016-07-27 03:27:10")
	public static TCResult createExcel(String fileName) {
		if (fileName == null || fileName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		File excelFile = new File(fileName);
		if (excelFile.exists()) {
			return TCResult.newSuccessResult();
		}
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(excelFile);
			book.createSheet("Sheet1", 0);
			book.write();
		} catch (IOException e) {
			return TCResult.newFailureResult(ErrorCode.IOCTL,
					"工作表创建失败或写入内容时候异常");
		} finally {
			try {
				if (book != null) {
					book.close();
				}
			} catch (Exception e) {
				AppLogger.error(e);
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param fileName
	 *            入参|Excel文件名|{@link java.lang.String}
	 * @param sheetName
	 *            入参|工作表名|{@link java.lang.String}
	 * @param index
	 *            入参|工作表索引|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "Excel文件名", type = java.lang.String.class),
			@Param(name = "sheetName", comment = "工作表名", type = java.lang.String.class),
			@Param(name = "index", comment = "工作表索引", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "创建工作表", style = "判断型", type = "同步组件", comment = "创建工作表", date = "2016-07-27 03:41:20")
	public static TCResult createSheet(String fileName, String sheetName,
			int index) {
		if (fileName == null || fileName.equals("") || sheetName == null
				|| sheetName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		File excelFile = new File(fileName);
		if (!excelFile.exists()) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, "Excel文件不存在");
		}
		WritableWorkbook workbook = null;
		try {
			workbook = safelyGetWorkBook(excelFile);
			workbook.createSheet(sheetName, index);
			return TCResult.newSuccessResult();
		} catch (WorkbookException e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (workbook != null) {
				try {
					workbook.write();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				try {
					workbook.close();
				} catch (Exception e) {
					AppLogger.error(e);
				}
			}
		}

	}

	/**
	 * @category 批量写入文本数据
	 * @param path
	 *            入参|路径|{@link java.lang.String}
	 * @param sheetName
	 *            入参|工作簿名称|{@link java.lang.String}
	 * @param cells
	 *            入参|数据集合|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "Excel文件名", type = java.lang.String.class),
			@Param(name = "sheetName", comment = "工作表名称", type = java.lang.String.class),
			@Param(name = "cells", comment = "数据集合,每一项都是一个JavaList，内容顺序为（列标,行标,单元格内容）", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "向指定工作表批量写入文本数据", style = "判断型", type = "同步组件", comment = "向指定工作表批量写入文本数据", date = "2016-07-26 10:03:38")
	public static TCResult P_writeTextByPatch(String fileName,
			String sheetName, JavaList cells) {
		if (fileName == null || fileName.equals("") || sheetName == null
				|| sheetName.equals("") || cells == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		File excelFile = new File(fileName);
		if (!excelFile.exists()) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, "Excel文件不存在");
		}
		WritableWorkbook workbook = null;
		try {
			workbook = safelyGetWorkBook(excelFile);
			WritableSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				return TCResult.newFailureResult(ErrorCode.FILECTL,
						"要修改的工作表不存在");
			}
			for (Object ce : cells) {
				if (ce instanceof JavaList) {
					JavaList clist = (JavaList) ce;
					int col = clist.getIntItem(0);
					int row = clist.getIntItem(1);
					String content = clist.getStringItem(2);
					sheet.addCell(new Label(col, row, content));
				}
			}
			return TCResult.newSuccessResult();
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (workbook != null) {
				try {
					workbook.write();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				try {
					workbook.close();
				} catch (Exception e) {
					AppLogger.error(e);
				}
			}
		}

	}

	/**
	 * @category 读入工作簿内容
	 * @param path
	 *            入参|路径|{@link java.lang.String}
	 * @param sheetName
	 *            入参|工作簿名称|{@link java.lang.String}
	 * @param cells
	 *            出参|数据集合,每一项都是一个JavaList，内容顺序为（列标,行标,单元格内容）|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "Excel文件名", type = java.lang.String.class),
			@Param(name = "sheetName", comment = "工作表名称", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "cells", comment = "数据集合,每一项都是一个JavaList，内容顺序为（列标,行标,单元格内容）", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "读入指定工作表内容", style = "判断型", type = "同步组件", comment = "读入指定工作表内容", date = "2016-07-26 10:04:19")
	public static TCResult P_readTextBySheet(String fileName, String sheetName) {
		if (fileName == null || fileName.equals("") || sheetName == null
				|| sheetName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		File excelFile = new File(fileName);
		if (!excelFile.exists()) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, "Excel文件不存在");
		}
		WritableWorkbook workbook = null;
		try {
			workbook = safelyGetWorkBook(excelFile);
			WritableSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				return TCResult.newFailureResult(ErrorCode.FILECTL,
						"要修改的工作表不存在");
			}
			JavaList rowList = new JavaList();
			for (int i = 0; i < sheet.getRows(); i++) {
				JavaList colList = new JavaList();
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					if (cell instanceof Label) {
						Label label = (Label) cell;
						int col = label.getColumn();
						int row = label.getRow();
						String content = label.getContents();
						colList.add(new JavaList(col, row, content));
					}
				}
				rowList.add(colList);
			}
			return TCResult.newSuccessResult(rowList);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (workbook != null) {
				try {
					workbook.write();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				try {
					workbook.close();
				} catch (Exception e) {
					AppLogger.error(e);
				}
			}
		}

	}

	@SuppressWarnings("unused")
	private static WritableWorkbook safelyGetWorkBook(File excelFile)
			throws WorkbookException {
		WritableWorkbook book = null;
		Workbook wb = null;

		try {
			wb = Workbook.getWorkbook(excelFile);
		} catch (BiffException e) {
			if (wb != null) {
				wb.close();
			}
			throw new WorkbookException(e);
		} catch (IOException e) {
			if (wb != null) {
				wb.close();
			}
			throw new WorkbookException(e);
		}
		try {
			book = Workbook.createWorkbook(excelFile, wb);
		} catch (Exception e) {
			if (wb != null) {
				wb.close();
			}
			if (book != null) {
				try {
					book.write();
				} catch (IOException e1) {
					e = e1;
				}
				try {
					book.close();
				} catch (Exception e1) {
					e = e1;
				}
			}
			throw new WorkbookException(e);
		}

		return book;
	}

	private static class WorkbookException extends Exception {
		private static final long serialVersionUID = -6328385878574643954L;

		public WorkbookException(Throwable cause) {
			super(cause);
		}
	}

	/**
	 * @param fileName
	 *            入参|Excel文件名|{@link java.lang.String}
	 * @param sheetName
	 *            入参|工作表名|{@link java.lang.String}
	 * @param rowNum
	 *            入参|单元格行号|int
	 * @param columnNum
	 *            入参|单元格列号|int
	 * @param contents
	 *            出参|单元格内容|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "Excel文件名", type = java.lang.String.class),
			@Param(name = "sheetName", comment = "工作表名", type = java.lang.String.class),
			@Param(name = "rowNum", comment = "单元格行号", type = int.class),
			@Param(name = "columnNum", comment = "单元格列号", type = int.class) })
	@OutParams(param = { @Param(name = "contents", comment = "单元格内容", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "读取单元格内容", style = "判断型", type = "同步组件", comment = "读取Excel单元格里面的内容", date = "2016-07-27 05:59:57")
	public static TCResult readCellData(String fileName, String sheetName,
			int rowNum, int columnNum) {
		if (fileName == null || fileName.equals("") || sheetName == null
				|| sheetName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}

		Workbook readBook = null;
		try {
			readBook = Workbook.getWorkbook(new File(fileName));
		} catch (BiffException e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} catch (IOException e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		}

		Sheet readSheet = readBook.getSheet(sheetName);
		if (readSheet == null) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, "相应的工作表不存在");
		}
		Cell cell = readSheet.getCell(columnNum, rowNum);
		String contents = cell.getContents();
		if (readBook != null) {
			readBook.close();
		}
		return TCResult.newSuccessResult(contents);
	}

	/**
	 * @param fileName
	 *            入参|Excel文件名|{@link java.lang.String}
	 * @param sheetName
	 *            入参|工作表名|{@link java.lang.String}
	 * @param rowNum
	 *            入参|单元格行号|int
	 * @param columnNum
	 *            入参|单元格列号|int
	 * @param contents
	 *            入参|待写入的内容|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "Excel文件名", type = java.lang.String.class),
			@Param(name = "sheetName", comment = "工作表名", type = java.lang.String.class),
			@Param(name = "rowNum", comment = "单元格行号", type = int.class),
			@Param(name = "columnNum", comment = "单元格列号", type = int.class),
			@Param(name = "contents", comment = "待写入的内容", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "写入单元格内容", style = "判断型", type = "同步组件", comment = "写入Excel单元格里面的内容", date = "2016-07-27 06:18:21")
	public static TCResult writeCellData(String fileName, String sheetName,
			int rowNum, int columnNum, String contents) {
		if (fileName == null || fileName.equals("") || sheetName == null
				|| sheetName.equals("") || contents == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		File excelFile = new File(fileName);
		if (!excelFile.exists()) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, "Excel文件不存在");
		}
		WritableWorkbook workbook = null;
		try {
			workbook = safelyGetWorkBook(excelFile);
			WritableSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				return TCResult.newFailureResult(ErrorCode.FILECTL,
						"要修改的工作表不存在");
			}
			sheet.addCell(new Label(columnNum, rowNum, contents));
			return TCResult.newSuccessResult();
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (workbook != null) {
				try {
					workbook.write();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				try {
					workbook.close();
				} catch (Exception e) {
					AppLogger.error(e);
				}
			}
		}
	}

	/**
	 * @param fileName
	 *            入参|Excel文件名|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "fileName", comment = "Excel文件名", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "删除Excel文件", style = "判断型", type = "同步组件", comment = "删除Excel文件", date = "2016-07-27 06:28:49")
	public static TCResult delExcel(String fileName) {
		File file = new File(fileName);
		if (file.delete()) {
			return TCResult.newSuccessResult();
		} else {
			return TCResult.newFailureResult(ErrorCode.FILECTL, "文件删除失败");
		}
	}

}
