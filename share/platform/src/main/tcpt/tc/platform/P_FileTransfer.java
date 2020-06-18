package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;
import cn.com.agree.afa.jcomponent.AFTClientDelegator;
import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;
import static tc.platform.P_String.isEmpty;

/**
 * 文件上传下载
 * 
 * @date 2015-07-11 1:10:48
 */
@ComponentGroup(level = "平台", groupName = "文件传输类组件")
public class P_FileTransfer {

	/**
	 * @param remoteHost
	 *            入参|远程主机ip地址|{@link java.lang.String}
	 * @param remotePort
	 *            入参|远程主机上传端口|int
	 * @param localFilePath
	 *            入参|本地上传文件路径|{@link java.lang.String}
	 * @param remoteDir
	 *            入参|远程主机上传目录|{@link java.lang.String}
	 * @param remoteFileName
	 *            入参|文件在远程主机上的命名|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "remoteHost", comment = "远程主机ip地址", type = java.lang.String.class),
			@Param(name = "remotePort", comment = "远程主机上传端口", type = int.class),
			@Param(name = "localFilePath", comment = "本地上传文件路径", type = java.lang.String.class),
			@Param(name = "remoteDir", comment = "远程主机上传目录", type = java.lang.String.class),
			@Param(name = "remoteFileName", comment = "文件在远程主机上的命名", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "上传文件", style = "判断型", type = "同步组件", comment = "根据AFP协议上传文件", date = "Mon Jul 13 09:53:58 CST 2015")
	public static TCResult upload(String remoteHost, int remotePort,
			String localFilePath, String remoteDir, String remoteFileName) {
		if (isEmpty(remoteHost)) {
			return TCResult.newFailureResult(ErrorCode.AGR, "远程主机ip地址不能为空");
		}

		if (remotePort < 0 || remotePort > 65535) {
			return TCResult.newFailureResult(ErrorCode.AGR, "远程端口非法:"
					+ remotePort);
		}

		if (isEmpty(localFilePath)) {
			return TCResult.newFailureResult(ErrorCode.AGR, "本地上传文件路径不能为空");
		}

		if (isEmpty(remoteDir)) {
			return TCResult.newFailureResult(ErrorCode.AGR, "远程主机上传目录不能为空");
		}

		if (isEmpty(remoteDir)) {
			return TCResult.newFailureResult(ErrorCode.AGR, "文件在远程主机上的命名不能为空");
		}
		return AFTClientDelegator.put(remoteHost, remotePort, localFilePath,
				remoteDir, remoteFileName);
	}

	/**
	 * @param remoteHost
	 *            入参|远程主机ip地址|{@link java.lang.String}
	 * @param remotePort
	 *            入参|远程主机端口|{@link int}
	 * @param localSaveFile
	 *            入参|下载文件本地保存路径|{@link java.lang.String}
	 * @param remoteDir
	 *            入参|远程文件所在目录|{@link java.lang.String}
	 * @param remoteFileName
	 *            入参|远程文件名字|{@link java.lang.String}
	 * @param downloadTimeoutMillis
	 *            入参|下载超时时间(毫秒)|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "remoteHost", comment = "远程主机ip地址", type = java.lang.String.class),
			@Param(name = "remotePort", comment = "远程主机端口", type = int.class),
			@Param(name = "localSaveFile", comment = "下载文件本地保存路径", type = java.lang.String.class),
			@Param(name = "remoteDir", comment = "远程文件所在目录", type = java.lang.String.class),
			@Param(name = "remoteFileName", comment = "远程文件名字", type = java.lang.String.class),
			@Param(name = "downloadTimeoutMillis", comment = "下载超时时间(毫秒)", type = int.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "下载文件", style = "判断型", type = "同步组件", comment = "根据AFP协议下载文件", date = "Mon Jul 13 09:54:30 CST 2015")
	public static TCResult download(String remoteHost, int remotePort,
			String localSaveFile, String remoteDir, String remoteFileName,
			int downloadTimeoutMillis) {
		if (isEmpty(remoteHost)) {
			return TCResult.newFailureResult(ErrorCode.AGR, "远程主机ip地址不能为空");
		}

		if (remotePort < 0 || remotePort > 65535) {
			return TCResult.newFailureResult(ErrorCode.AGR, "远程端口非法:"
					+ remotePort);
		}

		if (isEmpty(localSaveFile)) {
			return TCResult.newFailureResult(ErrorCode.AGR, "下载文件本地保存路径不能为空");
		}

		if (isEmpty(remoteDir)) {
			return TCResult.newFailureResult(ErrorCode.AGR, "远程文件所在目录不能为空");
		}

		if (isEmpty(remoteDir)) {
			return TCResult.newFailureResult(ErrorCode.AGR, "远程文件名字不能为空");
		}

		if (downloadTimeoutMillis < 0) {
			return TCResult.newFailureResult(ErrorCode.AGR, "下载超时时间不能小于0");
		}

		return AFTClientDelegator.get(remoteHost, remotePort, localSaveFile,
				remoteDir, remoteFileName, downloadTimeoutMillis);
	}

}
