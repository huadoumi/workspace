package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.DecimalFormat;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * 文件处理类组件
 * 
 * @date 2015-07-09 9:17:36
 */
@ComponentGroup(level = "平台", groupName = "文件处理类组件")
public class P_File {

	/**
	 * @param fileList
	 *            入参|文件列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :judgeIfExists(["f:/b.txt"])
	 * @example :judgeIfExists(["D:/test.txt","D:/user.txt"])
	 * @example :judgeIfExists(["D:/test.txt","D:/user.txt","server.txt"])
	 */
	@InParams(param = { @Param(name = "fileList", comment = "文件列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件判断", style = "判断型", type = "同步组件", comment = "判断列表中fileList的文件是否存在", date = "Thu Jul 16 11:14:00 CST 2015")
	public static TCResult judgeIfExists(JavaList fileList) {

		if (fileList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null");
		}

		if (fileList.size() == 0) {
			return TCResult.newFailureResult(ErrorCode.PARAM, "文件列表元素个数不能为0");
		}
		int order = 0;
		for (Object item : fileList) {
			order++;
			File file = new File((String) item);
			if (!file.exists() || file.isDirectory()) {
				return TCResult.newFailureResult(ErrorCode.FILECTL, "第" + order
						+ "文件不存在");
			}
		}
		return TCResult.newSuccessResult();

	}

	/**
	 * @param fileName
	 *            入参|文件名称|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :newFile("D:/test.txt")
	 * @example :newFile("D:/user.txt")
	 * @example :newFile("C:/deploy/test.txt")
	 */
	@InParams(param = { @Param(name = "fileName", comment = "文件名称", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件创建", style = "判断型", type = "同步组件", comment = "根据文件名创建空文件", date = "Thu Jul 16 11:24:42 CST 2015")
	public static TCResult newFile(String fileName) {

		if (fileName == null || fileName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		File file = new File(fileName);

		try {
			boolean success;
			success = file.createNewFile();
			if (success) {
				return TCResult.newSuccessResult();
			} else {
				return TCResult.newFailureResult(ErrorCode.FILECTL, "文件已经存在");
			}

		} catch (IOException e) {
			return TCResult.newFailureResult(ErrorCode.IOCTL, "发生未知的IO错误");
		}

	}

	/**
	 * @param fileList
	 *            入参|删除文件列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 文件不存在<br/>
	 * @使用范例 :
	 * @example :delete(["D:/user.txt"])
	 * @example :delete(["D:/user.txt","D:/test.txt"])
	 * @example :delete(["D:/user.txt","D:/test.txt","D:/server.txt"])
	 */
	@InParams(param = { @Param(name = "fileList", comment = "文件列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "文件不存在") })
	@Component(label = "文件删除", style = "选择型", type = "同步组件", comment = "批量删除列表中的文件", date = "Thu Jul 16 11:38:33 CST 2015")
	public static TCResult delete(JavaList fileList) {
		if (fileList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null");
		}
		int order = 0;
		for (Object item : fileList) {
			order++;
			File file = new File((String) item);
			if (!file.exists()) {
				return new TCResult(2, ErrorCode.FILECTL, "第" + order
						+ "个文件不存在");
			}
		}
		for (Object item : fileList) {
			File file = new File((String) item);
			file.delete();
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param fileList
	 *            入参|文件名列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :join(["D:/test.txt","D:/user.txt"])
	 * @example :join(["D:/test.txt","D:/user.txt","D:/server.txt"])
	 * @example 
	 *          :join(["D:/test.txt","D:/user.txt","D:/server.txt","D:/temp.txt"]
	 *          )
	 */
	@InParams(param = { @Param(name = "fileList", comment = "文件名列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件合并", style = "判断型", type = "同步组件", comment = "fileList[0]表示目标文件,后续文件合并到目标文件中", date = "Thu Jul 16 11:44:33 CST 2015")
	public static TCResult join(JavaList fileList) {
		if (fileList == null || fileList.size() < 2) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数不能为null且文件个数不能小于2");
		}

		if (((String) fileList.get(0)) == null
				|| ((String) fileList.get(0)).equals("")) {
			return TCResult.newFailureResult(ErrorCode.PARAM,
					"列表第一个元素不能为null或者为空字符串");
		}

		File dstFile = null;
		String fileName = null;
		int len;
		FileInputStream in = null;
		FileOutputStream out = null;
		byte[] buf = new byte[512];

		try {
			dstFile = new File((String) fileList.get(0));
			fileList.remove(0);
			out = new FileOutputStream(dstFile, true);

			for (Object item : fileList) {
				fileName = (String) item;
				File srcFile = new File(fileName);
				in = new FileInputStream(srcFile);
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				out.flush();
				in.close();
			}

			return TCResult.newSuccessResult();
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				in = null;
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				out = null;
			}

		}
	}

	/**
	 * @param filePath
	 *            入参|文件夹路径|{@link java.lang.String}
	 * @param zipName
	 *            入参|压缩文件总路径|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :zip("D:/deploy","D:/zip.zip")
	 * @example :zip("D:/deploy/tt.txt","D:/zip.zip")
	 * @example :zip("D:/aa/deploy","D:/zip.zip")
	 */
	@InParams(param = {
			@Param(name = "filePath", comment = "文件夹路径", type = java.lang.String.class),
			@Param(name = "zipName", comment = "压缩文件总路径", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件压缩", style = "判断型", type = "同步组件", comment = "指定文件夹下所有内容压缩成一个文件", date = "Thu Jul 16 13:58:49 CST 2015")
	public static TCResult zip(String filePath, String zipName) {

		if (filePath == null || filePath.equals("") || zipName == null
				|| zipName.equals(""))
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		Project pj = new Project();
		Zip zip = new Zip();
		zip.setProject(pj);
		zip.setDestFile(new File(zipName));
		FileSet fset = new FileSet();
		fset.setProject(pj);
		File origin = new File(filePath);
		if (origin.isFile()) {
			fset.setFile(origin);
		} else {
			fset.setDir(origin);
		}
		zip.addFileset(fset);
		zip.execute();
		return TCResult.newSuccessResult();

	}

	/**
	 * @使用范例 :
	 * @param fileName
	 *            入参|源文件名|{@link java.lang.String}
	 * @param codeFile
	 *            入参|加密文件|{@link java.lang.String}
	 * @param charset
	 *            入参|文件编码字符集，若参数传入为null，使用系统默认编码方式|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :encrypt("D:/user.txt","D:/test.txt")
	 * @example :encrypt("D:/server.txt","D:/test.txt")
	 * @example :encrypt("D:/temp.txt","D:/test.txt")
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "源文件名", type = java.lang.String.class),
			@Param(name = "codeFile", comment = "加密文件", type = java.lang.String.class),
			@Param(name = "charset", comment = "文件编码字符集，若参数传入为null，使用系统默认编码方式", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件加密", style = "判断型", type = "同步组件", comment = "对文件内容加密（用MD5加密无法还原，源文件保留）", date = "Thu Jul 16 18:07:40 CST 2015")
	public static TCResult encrypt(String fileName, String codeFile,
			String charset) {
		if (fileName == null || fileName.equals("") || codeFile == null
				|| codeFile.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		if (fileName.equals(codeFile)) {
			return TCResult.newFailureResult(ErrorCode.PARAM,
					"源文件名和加密文件文件名不能相同");
		}
		BufferedReader bufin = null;
		BufferedWriter bufout = null;
		String lineInfo = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };

		if (charset == null) {
			charset = Charset.defaultCharset().toString();
		}
		try {
			bufin = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileName)), charset));
			bufout = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(codeFile)), charset));
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			while ((lineInfo = bufin.readLine()) != null) {
				byte[] btInput = lineInfo.getBytes(charset);
				mdInst.update(btInput);
				byte[] mdBytes = mdInst.digest();
				int len = mdBytes.length;
				char[] str = new char[len * 2];
				int k = 0;
				for (int i = 0; i < len; i++) {
					byte element = mdBytes[i];
					str[k++] = hexDigits[element >>> 4 & 0xf];
					str[k++] = hexDigits[element & 0xf];
				}
				bufout.write(new String(str));
				bufout.newLine();
			}
			bufout.flush();
			return TCResult.newSuccessResult();
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (bufin != null) {
				try {
					bufin.close();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				bufin = null;
			}
			if (bufout != null) {
				try {
					bufout.close();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				bufout = null;
			}
		}
	}

	/**
	 * @param srcFileNameList
	 *            入参|待复制文件名列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :copy(["D:/user.txt"])
	 * @example :copy(["D:/user.txt","D:/server.txt"])
	 * @example :copy(["D:/user.txt","D:/server.txt","D:/temp.txt"])
	 */
	@InParams(param = { @Param(name = "srcFileNameList", comment = "待复制文件名列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件复制", style = "判断型", type = "同步组件", comment = "将批量源文件复制到该文件同一路径下，并重命名为源文件名+复件", date = "Thu Jul 16 15:38:43 CST 2015")
	public static TCResult copy(JavaList srcFileNameList) {
		if (srcFileNameList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为空");
		}

		File[] srcFileList = new File[srcFileNameList.size()];
		FileInputStream in = null;
		FileOutputStream out = null;
		byte[] buf = new byte[512];
		int len;

		for (int i = 0; i < srcFileNameList.size(); i++) {
			srcFileList[i] = new File(((String) srcFileNameList.get(i)));
			if (!srcFileList[i].exists()) {
				return TCResult.newFailureResult(ErrorCode.FILECTL, "第" + ++i
						+ "个文件不存在");
			}
		}

		for (int i = 0; i < srcFileList.length; i++) {
			String fileName = srcFileList[i].getAbsolutePath();
			String[] temp = fileName.split("\\.");
			String dstFileName = temp[0] + "复件" + "." + temp[1];
			try {
				in = new FileInputStream(srcFileList[i]);
				out = new FileOutputStream(new File(dstFileName));
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				out.flush();
			} catch (Exception e) {
				return TCResult.newFailureResult(ErrorCode.FILECTL, "复制文件失败 "
						+ "第" + ++i + "文件");
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						AppLogger.error(e);
					}
					in = null;
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						AppLogger.error(e);
					}
					out = null;
				}
			}
		}

		return TCResult.newSuccessResult();
	}

	/**
	 * @param fileName
	 *            入参|文件名|{@link java.lang.String}
	 * @param newName
	 *            入参|新的名字|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :rename("D:/user.txt","D:/user111.txt")
	 * @example :rename("D:/user.txt","D:/temp.txt")
	 * @example :rename("D:/user.txt","D:/temp")
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "文件名", type = java.lang.String.class),
			@Param(name = "newName", comment = "新的名字", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件重命名", style = "判断型", type = "同步组件", comment = "文件重命名", date = "Fri Jul 17 10:01:34 CST 2015")
	public static TCResult rename(String fileName, String newName) {
		if (fileName == null || newName == null || fileName.equals("")
				|| newName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		File origin = new File(fileName);
		if (!origin.exists()) {
			return TCResult
					.newFailureResult(ErrorCode.FILECTL, "文件不存在,无法进行重命名");
		}
		if (fileName.equals(newName)) {
			return TCResult.newSuccessResult();
		}
		origin.renameTo(new File(newName));
		return TCResult.newSuccessResult();
	}

	/**
	 * @param fileName
	 *            入参|文件名|{@link java.lang.String}
	 * @param getLinesNo
	 *            入参|读取文件前几行数据|{@link int}
	 * @param charset
	 *            入参|字符集，传入null则用默认GBK编码|{@link java.lang.String}
	 * @param strFromFile
	 *            出参|读到的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 文件内容为空<br/>
	 * @使用范例 :
	 * @example :readContent("D:/user.txt",0,null)
	 * @example :readContent("D:/user.txt",3,"GBK")
	 * @example :readContent("D:/test.txt",3,"UTF-8")
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "文件名", type = java.lang.String.class),
			@Param(name = "getLinesNo", comment = "读取文件前几行数据", type = int.class),
			@Param(name = "charset", comment = "字符集，传入null则用默认编码", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "strFromFile", comment = "读到的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "文件内容为空") })
	@Component(label = "文件内容读取", style = "选择型", type = "同步组件", comment = "读取文件的前几行，默认行数为0:表示读整个文件", date = "Wed Jul 15 15:50:53 CST 2015")
	public static TCResult readContent(String fileName, int getLinesNo,
			String charset) {
		if (fileName == null || fileName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"fileName参数不能为null或者空字符串");
		}

		if (System.getProperty("os.name").toUpperCase()
				.contains("windows".toUpperCase())) {
			if (!(fileName.contains(":"))) {
				if (fileName.charAt(0) == '/') {
					fileName = System.getenv("HOME").replace('\\', '/')
							+ fileName;
				} else {
					fileName = System.getenv("HOME").replace('\\', '/') + "/"
							+ fileName;
				}
			}
		} else {
			if (System.getProperty("os.name").toUpperCase()
					.contains("Linux".toUpperCase())) {
				if (fileName.charAt(0) != '/') {
					fileName = System.getenv("HOME") + "/" + fileName;
				}
			}
		}

		BufferedReader bufin = null;
		String lineInfo = null;
		String strFromFile = "";
		File file = new File(fileName);

		if (charset == null) {
			charset = Charset.defaultCharset().toString();
		}
		if (!file.exists()) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, "文件不存在");
		}
		if (file.length() == 0) {
			return new TCResult(2, ErrorCode.FILECTL, "文件内容为空");
		}
		int count = 0;
		try {
			bufin = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), charset));
			while ((lineInfo = bufin.readLine()) != null) {
				count++;
				strFromFile = strFromFile + lineInfo
						+ System.getProperty("line.separator");
				if (count == getLinesNo && getLinesNo != 0) {
					break;
				}
			}
			return TCResult.newSuccessResult(strFromFile);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (bufin != null) {
				try {
					bufin.close();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				bufin = null;
			}
		}
	}

	/**
	 * @param fileName
	 *            入参|文件名称|{@link java.lang.String}
	 * @param charset
	 *            入参|字符集名称，如果输入null则使用默认GBK编码|{@link java.lang.String}
	 * @param lineInfo
	 *            出参|读到的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :readLine("D:/user.txt")
	 * @example :readLine("D:/server.txt")
	 * @example :readLine("D:/test.txt")
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "文件名称", type = java.lang.String.class),
			@Param(name = "charset", comment = "字符集名称，如果输入null则使用默认编码", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "lineInfo", comment = "读到的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件内容读取一行", style = "判断型", type = "同步组件", comment = "读取文本文件的一行", date = "Wed Jul 15 16:07:20 CST 2015")
	public static TCResult readLine(String fileName, String charset) {
		BufferedReader bufin = null;
		if (fileName == null || fileName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}

		if (charset == null) {
			charset = Charset.defaultCharset().toString();
		}
		try {
			bufin = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), charset));
			String lineInfo = bufin.readLine();
			if (lineInfo == null)
				lineInfo = "";
			return TCResult.newSuccessResult(lineInfo);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (bufin != null) {
				try {
					bufin.close();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				bufin = null;
			}
		}
	}

	/**
	 * @param fileName
	 *            入参|要写入的文件名|{@link java.lang.String}
	 * @param strToFile
	 *            入参|写入文件的字符串列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param fAutoSkipLine
	 *            入参|1:列表换行写入，0：列表不换行|{@link int}
	 * @param charset
	 *            入参|文件字符集编码名称，如果传入为null则使用默认编码|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :writeContent("D:/test.txt",["111"],1,null)
	 * @example :writeContent("D:/test.txt",["111"],0,null)
	 * @example :writeContent("D:/test.txt",["111","2333"],1,null)
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "要写入的文件名", type = java.lang.String.class),
			@Param(name = "strToFile", comment = "写入文件的字符串列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "fAutoSkipLine", comment = "1:列表换行写入，0：列表不换行", type = int.class),
			@Param(name = "charset", comment = "文件字符集编码名称，如果传入为null则使用默认编码", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件内容写入", style = "判断型", type = "同步组件", comment = "文件存在则追加写入，不存在则创建写入", date = "Thu Jul 16 16:15:30 CST 2015")
	public static TCResult writeContent(String fileName, JavaList strToFile,
			int fAutoSkipLine, String charset) {
		if (strToFile == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数strToFile不能为null");
		}

		if (fileName == null || fileName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数fileName不能为null或空字符串");
		}
		if (System.getProperty("os.name").toUpperCase()
				.contains("windows".toUpperCase())) {
			if (!(fileName.contains(":"))) {
				if (fileName.charAt(0) == '/') {
					fileName = System.getenv("HOME").replace('\\', '/')
							+ fileName;
				} else {
					fileName = System.getenv("HOME").replace('\\', '/') + "/"
							+ fileName;
				}
			}
		} else {
			if (System.getProperty("os.name").toUpperCase()
					.contains("Linux".toUpperCase())) {
				if (fileName.charAt(0) != '/') {
					fileName = System.getenv("HOME") + "/" + fileName;
				}
			}
		}
		File file = new File(fileName);
		BufferedWriter bufout = null;
		boolean append = false;
		if (charset == null) {
			charset = Charset.defaultCharset().toString();
		}
		if (file.exists()) {
			append = true;
		}
		try {
			bufout = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, append), charset));
			for (Object item : strToFile) {
				String info = (String) item;
				bufout.write(info);
				if (fAutoSkipLine == 1)
					bufout.newLine();
			}
			bufout.flush();
			return TCResult.newSuccessResult();
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (bufout != null) {
				try {
					bufout.close();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				bufout = null;
			}
		}
	}

	/**
	 * @param fileName
	 *            入参|文件名|{@link java.lang.String}
	 * @param charset
	 *            入参|文件编码字符集，参数传入null则为系统默认编码方式|{@link java.lang.String}
	 * @param md5str
	 *            出参|文件的md5值|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :md5Calculate("D:/test.txt",null)
	 * @example :md5Calculate("D:/user.txt","UTF-8")
	 * @example :md5Calculate("D:/server.txt",null)
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "文件名", type = java.lang.String.class),
			@Param(name = "charset", comment = "文件编码字符集，参数传入null则为系统默认编码方式", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "md5str", comment = "文件的md5值", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "计算文件的md5", style = "判断型", type = "同步组件", comment = "计算文件的md5,最后返回由文件内容转化成的md5字符串", date = "Thu Jul 16 18:11:43 CST 2015")
	public static TCResult md5Calculate(String fileName, String charset) {
		BufferedReader bufin = null;
		String lineInfo = null;
		String md5str = "";
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		if (fileName == null || fileName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		if (charset == null) {
			charset = Charset.defaultCharset().toString();
		}
		try {
			bufin = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileName)), charset));
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			while ((lineInfo = bufin.readLine()) != null) {
				byte[] btInput = lineInfo.getBytes(charset);
				mdInst.update(btInput);
				byte[] mdBytes = mdInst.digest();
				int len = mdBytes.length;
				char[] str = new char[len * 2];
				int k = 0;
				for (int i = 0; i < len; i++) {
					byte element = mdBytes[i];
					str[k++] = hexDigits[element >>> 4 & 0xf];
					str[k++] = hexDigits[element & 0xf];
				}
				md5str = md5str + new String(str)
						+ System.getProperty("line.separator");
			}
			return TCResult.newSuccessResult(md5str);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (bufin != null) {
				try {
					bufin.close();
				} catch (IOException e) {
					AppLogger.error(e);
				}
				bufin = null;
			}

		}
	}

	/**
	 * @param filePath
	 *            入参|文件夹字符串|{@link java.lang.String}
	 * @param flag
	 *            入参|路径标识，0-相对路径，1-绝对路径|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :mkdir("D:/aa/bb",1)
	 * @example :mkdir("D:/aa/bb/cc",1)
	 * @example :mkdir("aa/bb",0)
	 */
	@InParams(param = {
			@Param(name = "filePath", comment = "文件夹字符串", type = java.lang.String.class),
			@Param(name = "flag", comment = "路径标识，0-Linux系统相对路径，1-绝对路径", type = int.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "创建目录", style = "判断型", type = "同步组件", comment = "创建指定目录", date = "Wed Jul 15 17:11:52 CST 2015")
	public static TCResult mkdir(String filePath, int flag) {
		if (filePath == null || filePath.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}

		if (System.getProperty("os.name").toUpperCase()
				.contains("windows".toUpperCase())) {
			if (!(filePath.contains(":"))) {
				if (filePath.charAt(0) == '/') {
					filePath = System.getenv("HOME").replace('\\', '/')
							+ filePath;
				} else {
					filePath = System.getenv("HOME").replace('\\', '/') + "/"
							+ filePath;
				}
			}
		} else {
			if (System.getProperty("os.name").toUpperCase()
					.contains("Linux".toUpperCase())) {
				if (flag == 0) {
					String userhome = System.getenv("HOME");
					if (filePath.charAt(0) != '/') {
						filePath = userhome + "/" + filePath;
					} else {
						filePath = userhome + filePath;
					}
				}
			}
		}

		File path = new File(filePath);
		if (!path.exists())
			path.mkdirs();
		return TCResult.newSuccessResult();

	}

	/**
	 * @param filePath
	 *            入参|文件夹字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :createFolder("D:/aa")
	 * @example :createFolder("D:/aa/bb/cc")
	 * @example :createFolder("D:/bb/cc/dd")
	 */
	@InParams(param = { @Param(name = "filePath", comment = "文件夹字符串", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "创建文件夹", style = "判断型", type = "同步组件", comment = "创建文件夹", date = "Wed Jul 15 17:21:37 CST 2015")
	public static TCResult createFolder(String filePath) {
		if (filePath == null || filePath.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}

		if (System.getProperty("os.name").toUpperCase()
				.contains("windows".toUpperCase())) {
			if (!(filePath.contains(":"))) {
				if (filePath.charAt(0) == '/') {
					filePath = System.getenv("HOME").replace('\\', '/')
							+ filePath;
				} else {
					filePath = System.getenv("HOME").replace('\\', '/') + "/"
							+ filePath;
				}
			}
		} else {
			if (System.getProperty("os.name").toUpperCase()
					.contains("Linux".toUpperCase())) {
				if (filePath.charAt(0) != '/') {
					filePath = System.getenv("HOME") + "/" + filePath;
				}
			}
		}

		File path = new File(filePath);
		if (!path.exists()) {
			path.mkdirs();
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param dir
	 *            入参|文件目录|{@link java.lang.String}
	 * @param result
	 *            出参|所有文件文件名列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :list("D:/deploy")
	 * @example :list("D:/JDK-MyEclipse")
	 * @example :list("D:/aa")
	 */
	@InParams(param = { @Param(name = "dir", comment = "文件目录", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "result", comment = "所有文件文件名列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件列举", style = "判断型", type = "同步组件", comment = "根据文件目录搜索该目录下的所有文件", date = "Wed Jul 15 17:33:32 CST 2015")
	public static TCResult list(String dir) {
		if (dir == null || dir.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "输入参数不能为null或空字符串");
		}
		File file = new File(dir);
		if (!file.isDirectory()) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, "文件目录不存在");
		}
		File[] files = file.listFiles();
		JavaList result = new JavaList();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				continue;
			}
			result.add(files[i].getName());
		}
		return TCResult.newSuccessResult(result);
	}

	/**
	 * @param fileName
	 *            入参|文件名|{@link java.lang.String}
	 * @param lenFmt
	 *            入参|长度格式|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param data
	 *            入参|数据域|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param extCxt
	 *            入参|扩展字典|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param charset
	 *            入参|文件编码字符集，如果传入为null则使用默认GBK编码|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :
	 *          crtFixLenFile("D:/test.txt",[1,2,3,4],[[1,2,3,4]],{sumflag:true
	 *          })
	 * @example :
	 *          crtFixLenFile("D:/test.txt",[1,2,3,4],[[1,2,3,4],[2,2,3,4]],{sumflag
	 *          :true})
	 * @example :
	 *          crtFixLenFile("D:/test.txt",[2,2,3,4],[[1,2,3,4],[2,2,3,4],[1,2
	 *          ,3,4]],{sumflag:true})
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "文件名", type = java.lang.String.class),
			@Param(name = "lenFmt", comment = "长度格式", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "data", comment = "数据域", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "extCxt", comment = "扩展字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "charset", comment = "文件编码字符集，如果传入为null则使用默认编码", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "创建定长文件", style = "判断型", type = "同步组件", comment = "按照指定的格式和提供的数据域创建固定格式的文件(扩展字典当前只支持在文件头写总笔数{\\\"sumflag\\\" : True})", date = "Wed Jul 15 17:48:39 CST 2015")
	public static TCResult crtFixLenFile(String fileName, JavaList lenFmt,
			JavaList data, JavaDict extCxt, String charset) {
		if (fileName == null || fileName.equals("") || lenFmt == null
				|| data == null || extCxt == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数不能为null，文件名不能为空字符串");
		}
		String fname = null;
		if (System.getProperty("os.name").toUpperCase()
				.contains("linux".toUpperCase())) {
			fname = System.getenv("HOME") + "/workspace/fdir/" + fileName;
		}

		if (charset == null) {
			charset = Charset.defaultCharset().toString();
		}
		fname = fileName;
		File file = new File(fname);
		int sum = data.size();
		PrintWriter pwriter = null;
		try {
			pwriter = new PrintWriter(file, charset);
			if (extCxt != null && extCxt.hasKey("sumflag")
					&& extCxt.get("sumflag") != null) {
				pwriter.println(sum);
			}
			for (Object object : data) {
				JavaList line = (JavaList) object;
				if (line.size() != lenFmt.size()) {
					return TCResult.newFailureResult(ErrorCode.DATALEN,
							"文件数据长度跟长度格式参数要求不一致");
				}
				for (int i = 0; i < line.size(); i++) {
					Integer ing = (Integer) line.get(i);
					String item = ing.toString();
					int len = Math.max(item.length(),
							((Integer) lenFmt.get(i)).intValue());
					String format = "%-" + len + "s";
					pwriter.print(String.format(format, item));
				}
				pwriter.println("");
			}
			return TCResult.newSuccessResult();
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		} finally {
			if (pwriter != null) {
				pwriter.close();
			}
		}
	}

	/**
	 * @param fileName
	 *            入参|文件或目录名|{@link java.lang.String}
	 * @param unit
	 *            入参|单位(K/M/G)|{@link java.lang.String}
	 * @param sizeStr
	 *            出参|文件的大小|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @使用范例 :
	 * @example :getFileSize("D:/deploy","B")
	 * @example :getFileSize("D:/JDK-MyEclipse","G")
	 * @example :getFileSize("D:/JDK-MyEclipse","B")
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "文件或目录名", type = java.lang.String.class),
			@Param(name = "unit", comment = "单位(K/M/G)", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "sizeStr", comment = "文件的大小", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "获取文件目录大小", style = "判断型", type = "同步组件", comment = "获取指定的目录或者文件的大小", date = "Thu Jul 16 16:35:17 CST 2015")
	public static TCResult getFileSize(String fileName, String unit) {
		long result;
		try {
			result = getFileSize(fileName);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		}
		double size = 0;
		String upperUnit = unit.toUpperCase();
		if (upperUnit.equals("B")) {
			size = (double) result;
		} else if (upperUnit.equals("K")) {
			size = ((double) result) / 1024;
		} else if (upperUnit.equals("M")) {
			size = ((double) result) / 1048576;
		} else if (upperUnit.equals("G")) {
			size = ((double) result) / 1073741824;
		} else {
			return TCResult.newFailureResult(ErrorCode.AGR, "不支持的单位名称");
		}
		DecimalFormat format = new DecimalFormat("###0.00");
		String sizeStr = format.format(size);
		return TCResult.newSuccessResult(sizeStr);
	}

	private static long getFileSize(String filename) {
		// 如果在递归中进行单位转化，转化误差的积累，导致后面的结果相差很大
		long size = 0;

		if (System.getProperty("os.name").toUpperCase()
				.contains("windows".toUpperCase())) {
			if (!(filename.contains(":"))) {
				if (filename.charAt(0) == '/') {
					filename = System.getenv("HOME").replace('\\', '/')
							+ filename;
				} else {
					filename = System.getenv("HOME").replace('\\', '/') + "/"
							+ filename;
				}
			}
		} else {
			if (System.getProperty("os.name").toUpperCase()
					.contains("Linux".toUpperCase())) {

				if (filename.charAt(0) != '/') {
					filename = System.getenv("HOME") + "/" + filename;
				}
			}
		}

		File file = new File(filename);

		if (!file.exists()) {
			throw new IllegalArgumentException("文件或文件夹不存在");
		}

		if (file.isFile()) {
			size += file.length();
		} else {
			File[] flist = file.listFiles();
			for (int i = 0; i < flist.length; i++) {
				size += getFileSize(flist[i].getAbsolutePath());
			}
		}

		return size;

	}

	/**
	 * @category 文件打开
	 * @param fileName
	 *            入参|文件名|{@link java.lang.String}
	 * @param charset
	 *            入参|字符集名称，如果输入null则使用默认编码|{@link java.lang.String}
	 * @param reader
	 *            出参|返回的BufferedReader引用|{@link java.io.BufferedReader}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "fileName", comment = "文件名", type = java.lang.String.class),
			@Param(name = "charset", comment = "字符集名称，如果输入null则使用默认编码", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "reader", comment = "返回的BufferedReader引用", type = java.io.BufferedReader.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件打开", style = "判断型", type = "同步组件", comment = "根据参数打开文件，返回BufferedReader引用", date = "2017-02-20 01:46:16")
	public static TCResult P_openFile(String fileName, String charset) {
		BufferedReader bufin = null;
		if (fileName == null || fileName.equals("")) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数不能为null或空字符串");
		}
		if (charset == null) {
			charset = Charset.defaultCharset().toString();
		}
		try {
			bufin = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), charset));
			return TCResult.newSuccessResult(bufin);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		}
	}

	/**
	 * @category 文件关闭
	 * @param reader
	 *            入参|Reader引用|{@link java.io.Reader}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "reader", comment = "Reader引用", type = java.io.Reader.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "文件关闭", style = "判断型", type = "同步组件", comment = "关闭传入的Reader引用", date = "2017-02-20 02:02:31")
	public static TCResult P_closeFile(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				AppLogger.error(e);
			}
			reader = null;
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @category BufferedReader读取一行
	 * @param reader
	 *            入参|BufferedReader引用|{@link java.io.BufferedReader}
	 * @param lineStr
	 *            出参|读取的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "reader", comment = "BufferedReader引用", type = java.io.BufferedReader.class) })
	@OutParams(param = { @Param(name = "lineStr", comment = "读取的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "BufferedReader读取一行", style = "判断型", type = "同步组件", comment = "根据传入的BufferedReader引用，读取文件内容一行", date = "2017-02-20 02:03:48")
	public static TCResult P_readFileLine(BufferedReader reader) {
		try {
			String lineInfo = reader.readLine();
			return TCResult.newSuccessResult(lineInfo);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.FILECTL, e);
		}
	}

}
