package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.jcomponent.NatpClient;
import cn.com.agree.afa.jcomponent.NatpCodec;
import cn.com.agree.afa.jcomponent.TcpClient;
import cn.com.agree.afa.jcomponent.TradeResponseSender;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * 渠道通讯
 * 
 * @date 2015-07-07 10:18:49
 */
@ComponentGroup(level = "平台", groupName = "渠道通讯类组件")
public class P_Communition {

	static final byte VERSION_2 = 0x10;
	static final byte VERSION_3 = 0x30;
	
	static final String AFA_HOST_NAME = "AFA4J-HOST";

	/**
	 * @param __REQ__
	 *            入参|NATP同步通讯客户端请求字典|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param mc
	 *            入参|模板代码|{@link java.lang.String}
	 * @param tc
	 *            入参|交易代码|{@link java.lang.String}
	 * @param rc
	 *            入参|备用代码|{@link java.lang.String}
	 * @param host
	 *            入参|请求主机|{@link java.lang.String}
	 * @param port
	 *            入参|请求端口|int
	 * @param encoding
	 *            入参|字符编码类型|string
	 * @param timeout
	 *            入参|超时时间|int
	 * @param __RSP__
	 *            出参|dict|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 超时异常<br/>
	 */
	@InParams(param = {
			@Param(name = "__REQ__", comment = "NATP同步通讯客户端请求字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "mc", comment = "模板代码", type = java.lang.String.class),
			@Param(name = "tc", comment = "交易代码", type = java.lang.String.class),
			@Param(name = "rc", comment = "备用代码", type = java.lang.String.class),
			@Param(name = "host", comment = "请求主机", type = java.lang.String.class),
			@Param(name = "port", comment = "请求端口", type = int.class),
			@Param(name = "encoding", comment = "字符编码", type = java.lang.String.class),
			@Param(name = "timeout", comment = "超时时间", type = int.class) })
	@OutParams(param = { @Param(name = "__RSP__", comment = "dict", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "超时异常") })
	@Component(label = "NATP客户端3.0", style = "判断型", type = "同步组件", comment = "NATP同步通讯客户端3.0", date = "Tue Jul 07 10:32:27 CST 2015")
	public static TCResult natpClient_3(JavaDict __REQ__, String mc, String tc,
			String rc, String host, int port, String encoding, int timeout) {
		return natpClient(__REQ__, mc, tc, rc, host, port, encoding, timeout,
				VERSION_3);
	}

	/**
	 * @param __REQ__
	 *            入参|NATP同步通讯客户端请求字典|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param mc
	 *            入参|模板代码|{@link java.lang.String}
	 * @param tc
	 *            入参|交易代码|{@link java.lang.String}
	 * @param rc
	 *            入参|备用代码|{@link java.lang.String}
	 * @param host
	 *            入参|请求主机|{@link java.lang.String}
	 * @param port
	 *            入参|请求端口|int
	 * @param encoding
	 *            入参|字符编码类型|string
	 * @param timeout
	 *            入参|超时时间|int
	 * @param __RSP__
	 *            出参|dict|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 超时异常<br/>
	 */
	@InParams(param = {
			@Param(name = "__REQ__", comment = "NATP同步通讯客户端请求字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "mc", comment = "模板代码", type = java.lang.String.class),
			@Param(name = "tc", comment = "交易代码", type = java.lang.String.class),
			@Param(name = "rc", comment = "备用代码", type = java.lang.String.class),
			@Param(name = "host", comment = "请求主机", type = java.lang.String.class),
			@Param(name = "port", comment = "请求端口", type = int.class),
			@Param(name = "encoding", comment = "字符编码", type = java.lang.String.class),
			@Param(name = "timeout", comment = "超时时间", type = int.class) })
	@OutParams(param = { @Param(name = "__RSP__", comment = "dict", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "超时异常") })
	@Component(label = "NATP客户端2.0", style = "判断型", type = "同步组件", comment = "NATP同步通讯客户端2.0", date = "Tue Jul 07 10:32:27 CST 2015")
	public static TCResult natpClient_2(JavaDict __REQ__, String mc, String tc,
			String rc, String host, int port, String encoding, int timeout) {
		return natpClient(__REQ__, mc, tc, rc, host, port, encoding, timeout,
				VERSION_2);
	}

	private static TCResult natpClient(JavaDict __REQ__, String mc, String tc,
			String rc, String host, int port, String encoding, int timeout,
			byte version) {
		__REQ__.setItem("__MC__", mc);
		__REQ__.setItem("__TC__", tc);
		__REQ__.setItem("__NC__", rc);
		JavaDict __RSP__ = new JavaDict();
		TCResult result = NatpClient.exchange(__REQ__, __RSP__, host, port,
				timeout, encoding, version);
		return new TCResult(result.getStatus(), result.getErrorCode(),
				result.getErrorMsg(), new JavaList(__RSP__));
	}

	/**
	 * @param ip
	 *            入参|IP|{@link java.lang.String}
	 * @param port
	 *            入参|端口|int
	 * @param timeOut
	 *            入参|通信超时|int
	 * @param headLen
	 *            入参|头长度大小|int
	 * @param isContain
	 *            入参|头部信息是否包含头部长度数值，1：包含，0 不包含|int
	 * @param reqData
	 *            入参|请求报文|byte
	 * @param rspPacket
	 *            出参|接收到的响应包|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 超时异常<br/>
	 */
	@InParams(param = {
			@Param(name = "ip", comment = "ip地址", type = java.lang.String.class),
			@Param(name = "port", comment = "端口", type = int.class),
			@Param(name = "timeOut", comment = "通信超时", type = int.class),
			@Param(name = "headLen", comment = "头长度大小", type = int.class),
			@Param(name = "isContain", comment = "头部信息是否包含头部长度数值，1：包含，0 不包含", type = int.class),
			@Param(name = "reqData", comment = "请求报文", type = byte[].class) })
	@OutParams(param = { @Param(name = "rspPacket", comment = "接收到的响应包", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "超时异常") })
	@Component(label = "TCP客户端", style = "选择型", type = "同步组件", comment = "基础通讯客户端", date = "Thu Jul 09 16:21:17 CST 2015")
	public static TCResult simpleClient(String ip, int port, int timeOut,
			int headLen, int isContain, byte[] reqData) {
		JavaList options = new JavaList(0, headLen, isContain, 1);
		return TcpClient.exchange(ip, port, timeOut, reqData, options);
	}

	/**
     * @param ip
     *            入参|IP|{@link java.lang.String}
     * @param port
     *            入参|端口|int
     * @param timeOut
     *            入参|通信超时|int
     * @param headLen
     *            入参|头长度大小|int
     * @param isContain
     *            入参|头部信息是否包含头部长度数值，1：包含，0 不包含|int
     * @param reqData
     *            入参|请求报文|byte
     * @return 0 失败<br/>
     *         1 成功<br/>
     *         2 超时异常<br/>
     */
    @InParams(param = {
            @Param(name = "ip", comment = "ip地址", type = java.lang.String.class),
            @Param(name = "port", comment = "端口", type = int.class),
            @Param(name = "timeOut", comment = "通信超时", type = int.class),
            @Param(name = "headLen", comment = "头长度大小", type = int.class),
            @Param(name = "isContain", comment = "头部信息是否包含头部长度数值，1：包含，0 不包含", type = int.class),
            @Param(name = "reqData", comment = "请求报文", type = byte[].class) })
    @Returns(returns = { @Return(id = "0", desp = "失败"),
            @Return(id = "1", desp = "成功"), @Return(id = "2", desp = "超时异常") })
    @Component(label = "TCP客户端，不接收响应", style = "选择型", type = "同步组件", comment = "基础通讯客户端，只发送请求，不接收响应", date = "Thu Jul 09 16:21:17 CST 2015")
    public static TCResult simpleClientWithoutRsp(String ip, int port, int timeOut,
            int headLen, int isContain, byte[] reqData) {
        JavaList options = new JavaList(0, headLen, isContain, 0);
        return TcpClient.exchange(ip, port, timeOut, reqData, options);
    }

	/**
	 * @category TCP客户端,头长度用4字节大端二进制表示
	 * @param ip
	 *            入参|ip地址|{@link java.lang.String}
	 * @param port
	 *            入参|端口|{@link int}
	 * @param timeOut
	 *            入参|通信超时|{@link int}
	 * @param headLen
	 *            入参|头长度大小|{@link int}
	 * @param isContain
	 *            入参|头部信息是否包含头部长度数值，1：包含，0 不包含|{@link int}
	 * @param reqData
	 *            入参|请求报文|{@link byte}
	 * @param isReturn
	 *            入参|1：表示有返回值，0：表示没有返回值|int
	 * @param rspPacket
	 *            出参|接收到的响应包|{@link byte}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 超时异常<br/>
	 */
	@InParams(param = {
			@Param(name = "ip", comment = "ip地址", type = java.lang.String.class),
			@Param(name = "port", comment = "端口", type = int.class),
			@Param(name = "timeOut", comment = "通信超时", type = int.class),
			@Param(name = "headLen", comment = "头长度大小", type = int.class),
			@Param(name = "isContain", comment = "头部信息是否包含头部长度数值，1：包含，0 不包含", type = int.class),
			@Param(name = "reqData", comment = "请求报文", type = byte[].class),
			@Param(name = "isReturn", comment = "1：表示有返回值，0：表示没有返回值", type = int.class) })
	@OutParams(param = { @Param(name = "rspPacket", comment = "接收到的响应包", type = byte.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "超时异常") })
	@Component(label = "TCP客户端,头长度用4字节大端二进制表示", style = "选择型", type = "同步组件", comment = "基础通讯客户端", date = "2016-10-25 10:53:36")
	public static TCResult P_simpleClientByBinaryPacketHead(String ip,
			int port, int timeOut, int headLen, int isContain, byte[] reqData,
			int isReturn) {
		JavaList options = new JavaList(1, headLen, isContain, isReturn);
		return TcpClient.exchange(ip, port, timeOut, reqData, options);
	}
	
	

	/**
	 * @param ip
	 *            入参|ip地址或域名|{@link java.lang.String}
	 * @param port
	 *            入参|端口|int
	 * @param realIP
	 *            出参|ip地址|{@link java.lang.String}
	 * @param realPort
	 *            出参|int|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "ip", comment = "ip地址或域名", type = java.lang.String.class),
			@Param(name = "port", comment = "端口", type = int.class) })
	@OutParams(param = {
			@Param(name = "realIP", comment = "ip地址", type = java.lang.String.class),
			@Param(name = "realPort", comment = "端口", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "域名解析", style = "判断型", type = "同步组件", comment = "域名解析，将域名转换为IP，TCP/IP协议", date = "Tue Jul 07 14:58:44 CST 2015")
	public static TCResult getTranslateAddr(String ip, int port) {
		String realIp = null;
		try {
			realIp = InetAddress.getByName(ip).toString();
		} catch (UnknownHostException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(realIp, port);
	}

	/**
	 * @param ifname
	 *            入参|网络接口|{@link java.lang.String}
	 * @param type
	 *            入参|返回的ip格式类型|{@link java.lang.String}
	 * @param hostIP
	 *            出参|返回主机IP地址|{@link java.lang.String}
	 * @return 0 异常<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "ifname", comment = "网络接口", type = java.lang.String.class),
			@Param(name = "type", comment = "返回的ip格式类型,例如ipv4 or ipv6", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "hostIP", comment = "返回主机IP地址", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "异常"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "获取主机IP地址", style = "判断型", type = "同步组件", comment = "获取主机IP地址", date = "Tue Jul 07 15:07:26 CST 2015")
	public static TCResult getHostIP(String ifname, String type) {
		if (ifname == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参ifname不能为空！");
		}
		if (type == null || type.length() == 0) {
			type = "ipv4";
		}
		String result = "";
		Enumeration<?> interfaces;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		while (interfaces.hasMoreElements()) {
			NetworkInterface intf = (NetworkInterface) interfaces.nextElement();
			if (ifname.equals(intf.getName())) {
				Enumeration<?> addresses = intf.getInetAddresses();
				while (addresses.hasMoreElements()) {
					Object object = addresses.nextElement();
					if (object instanceof Inet4Address && type.equals("ipv4")) {
						result = ((Inet4Address) object).getHostAddress()
								.toString();
						break;
					} else if (object instanceof Inet6Address
							&& type.equals("ipv6")) {
						result = ((Inet6Address) object).getHostAddress()
								.toString();
						break;
					} else {

					}
				}
				break;
			}
		}
		return TCResult.newSuccessResult(result);
	}

	/**
	 * @param __REQ__
	 *            入参|请求容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param hostList
	 *            入参|主机地址列表，格式“ip：port”|
	 *            {@cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param mc
	 *            入参|应用代码|{@link java.lang.String}
	 * @param tc
	 *            入参|交易代码|{@link java.lang.String}
	 * @param rc
	 *            入参|保留域代码|{@link java.lang.String}
	 * @param timeOut
	 *            入参|超时时间|int
	 * @param encoding
	 *            入参|字符编码类型|string
	 * @param _RSP_
	 *            出参|响应容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 超时异常<br/>
	 */
	@InParams(param = {
			@Param(name = "__REQ__", comment = "请求容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "hostList", comment = "主机地址列表，格式“ip：port”", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "mc", comment = "应用代码", type = java.lang.String.class),
			@Param(name = "tc", comment = "交易代码", type = java.lang.String.class),
			@Param(name = "rc", comment = "保留域代码", type = java.lang.String.class),
			@Param(name = "timeOut", comment = "超时时间", type = int.class),
			@Param(name = "encoding", comment = "字符编码", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "_RSP_", comment = "响应容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "超时异常") })
	@Component(label = "NATP客户端3.0(List)", style = "选择型", type = "同步组件", comment = "NATP客户端3.0(List)", date = "Tue Jul 07 15:34:17 CST 2015")
	public static TCResult natpExchangeByList_3(JavaDict __REQ__,
			JavaList hostList, String mc, String tc, String rc, int timeOut,
			String encoding) {
		return natpExchangeByList(__REQ__, hostList, mc, tc, rc, timeOut,
				encoding, VERSION_3);
	}

	/**
	 * @param __REQ__
	 *            入参|请求容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param hostList
	 *            入参|主机地址列表，格式“ip：port”|
	 *            {@cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param mc
	 *            入参|应用代码|{@link java.lang.String}
	 * @param tc
	 *            入参|交易代码|{@link java.lang.String}
	 * @param rc
	 *            入参|保留域代码|{@link java.lang.String}
	 * @param timeOut
	 *            入参|超时时间|int
	 * @param encoding
	 *            入参|字符编码类型|string
	 * @param _RSP_
	 *            出参|响应容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 超时异常<br/>
	 */
	@InParams(param = {
			@Param(name = "__REQ__", comment = "请求容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "hostList", comment = "主机地址列表，格式“ip：port”", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "mc", comment = "应用代码", type = java.lang.String.class),
			@Param(name = "tc", comment = "交易代码", type = java.lang.String.class),
			@Param(name = "rc", comment = "保留域代码", type = java.lang.String.class),
			@Param(name = "timeOut", comment = "超时时间", type = int.class),
			@Param(name = "encoding", comment = "字符编码", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "_RSP_", comment = "响应容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "超时异常") })
	@Component(label = "NATP客户端2.0(List)", style = "选择型", type = "同步组件", comment = "NATP客户端2.0(List)", date = "Tue Jul 07 15:34:17 CST 2015")
	public static TCResult natpExchangeByList_2(JavaDict __REQ__,
			JavaList hostList, String mc, String tc, String rc, int timeOut,
			String encoding) {
		return natpExchangeByList(__REQ__, hostList, mc, tc, rc, timeOut,
				encoding, VERSION_2);
	}

	private static TCResult natpExchangeByList(JavaDict __REQ__,
			JavaList hostList, String mc, String tc, String rc, int timeOut,
			String encoding, byte version) {
		if (hostList == null || hostList.size() <= 0) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数hostList 为空值或者它的大小为零!");
		}
		__REQ__.setItem("__MC__", mc);
		__REQ__.setItem("__TC__", tc);
		__REQ__.setItem("__NC__", rc);
		TCResult tcResult = null;
		JavaDict __RSP__ = new JavaDict();
		JavaList list = new JavaList();
		list.add(__RSP__);
		for (int i = 0; i < hostList.size(); i++) {
			String address = (String) hostList.get(i);
			String[] info = address.split(":");
			String ip = info[0];
			String port = info[1];
			tcResult = NatpClient.exchange(__REQ__, __RSP__, ip,
					Integer.valueOf(port), timeOut, encoding, version);
			if (tcResult.getStatus() == 1) {
				return TCResult.newSuccessResult(__RSP__);
			} else {
				continue;
			}
		}
		return tcResult;
	}

	/**
	 * @param address
	 *            入参|IP地址或Unix域套接字|{@link java.lang.String}
	 * @param port
	 *            入参|端口|int
	 * @param timeOut
	 *            入参|通信超时,单位：秒|int
	 * @param charset
	 *            入参|报文编码|{@link java.lang.String}
	 * @param pckStr
	 *            入参|报文字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2超时异常<br/>
	 */
	@InParams(param = {
			@Param(name = "address", comment = "IP地址或Unix域套接字", type = java.lang.String.class),
			@Param(name = "port", comment = "端口", type = int.class),
			@Param(name = "tmo", comment = "通信超时,单位:秒", type = int.class),
			@Param(name = "charset", comment = "报文编码", type = java.lang.String.class),
			@Param(name = "pckStr", comment = "报文字符串", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "超时异常") })
	@Component(label = "简单TCPClient和CAMA通信", style = "判断型", type = "同步组件", comment = "简单TCPClient和CAMA通信", date = "Tue Jul 07 15:51:32 CST 2015")
	public static TCResult simpleCliSentToCama(String address, int port,
			int timeOut, String charset, String pckStr) {
		int length = pckStr.length();
		char c0 = (char) (length / 256 / 256 / 256);
		char c1 = (char) (length / 256 / 256);
		char c2 = (char) (length / 256);
		char c3 = (char) (97);
		String pckLenString = String.valueOf(c0) + String.valueOf(c1)
				+ String.valueOf(c2) + String.valueOf(c3);
		String sendString = pckLenString + pckStr;
		return TcpClient
				.commToCama(address, port, timeOut, charset, sendString);
	}

	/**
	 * @param address
	 *            入参|IP地址或Unix域套接字|{@link java.lang.String}
	 * @param port
	 *            入参|端口|int
	 * @param timeOut
	 *            入参|通信超时，单位：秒|int
	 * @param seqno
	 *            入参|异步转同步流水号|byte
	 * @param pckStr
	 *            入参|答应报文|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 超时<br/>
	 */
	@InParams(param = {
			@Param(name = "address", comment = "IP地址或Unix域套接字", type = java.lang.String.class),
			@Param(name = "port", comment = "端口", type = int.class),
			@Param(name = "timeOut", comment = "通信超时，单位：秒", type = int.class),
			@Param(name = "seqno", comment = "异步转同步流水号", type = java.lang.String.class),
			@Param(name = "pckStr", comment = "答应报文", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "超时异常") })
	@Component(label = "异步转同步应答组件", style = "选择型", type = "同步组件", comment = "Example:P_SendReplyMsg(IP,Port ,tmo ,seqno,pckstr) 注意 如果Port =0 则认为走的是unix 域套接字", date = "Thu Jul 09 16:42:05 CST 2015")
	public static TCResult sendReplyMsg(String address, int port, int timeOut,
			String seqno, byte[] pckStr) {
		return TradeResponseSender.sendResponseBySocket(address, port, timeOut,
				seqno, pckStr);
	}

	/**
	 * @param __REQ__
	 *            入参|请求容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param encoding
	 *            入参|字符编码|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "__REQ__", comment = "请求容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "encoding", comment = "字符编码", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "NATP拆包", style = "判断型", type = "同步组件", comment = "NATP拆包", date = "Fri Jul 17 11:04:21 CST 2015")
	public static TCResult natpUnPack(JavaDict __REQ__, String encoding) {
		AppLogger.trace("====== 开始解NATP包 ======");
		AppLogger.dump(__REQ__.getItem("__RCVPCK__"));

		TCResult result = NatpCodec.decode(__REQ__, encoding);
		if (result.getStatus() != 1) {
			return result;
		}
		__REQ__.removeItem("__RCVPCK__");

		Iterator<Entry<Object, Object>> reqSetIt = __REQ__.entrySet()
				.iterator();
		while (reqSetIt.hasNext()) {
			Entry<Object, Object> entry = reqSetIt.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (key instanceof String) {
				if (!((String) key).equals("__RCVPCK__")
						&& !((String) key).equals("__SNDPCK__")) {
					AppLogger.trace(key + "=" + value);
				}
			}
		}
		AppLogger.trace("====== 结束解NATP包 ======!");
		return result;

	}

	/**
	 * @param __RSP__
	 *            入参|natp拼包容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param mc
	 *            入参|应用代码|{@link java.lang.String}
	 * @param tc
	 *            入参|交易代码|{@link java.lang.String}
	 * @param encoding
	 *            入参|字符编码|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "__RSP__", comment = "natp拼包容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "mc", comment = "应用代码", type = java.lang.String.class),
			@Param(name = "tc", comment = "交易代码", type = java.lang.String.class),
			@Param(name = "encoding", comment = "字符编码", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "NATP拼包", style = "判断型", type = "同步组件", comment = "NATP拼包", date = "Fri Jul 17 11:18:16 CST 2015")
	public static TCResult natpPack(JavaDict __RSP__, String mc, String tc,
			String encoding) {
		AppLogger.trace("====== 开始打NATP包 ======!");
		if (mc != null) {
			__RSP__.setItem("__MC__", mc);
		} else {
			if (!__RSP__.hasKey("__MC__")) {
				__RSP__.setItem("__MC__", "");
			}
		}
		if (tc != null) {
			__RSP__.setItem("__TC__", tc);
		} else {
			if (!__RSP__.hasKey("__TC__")) {
				__RSP__.setItem("__TC__", "");
			}
		}
		if (!__RSP__.hasKey("__NC__")) {
			__RSP__.setItem("__NC__", "");
		}
		Iterator<Entry<Object, Object>> reqSetIt = __RSP__.entrySet()
				.iterator();
		while (reqSetIt.hasNext()) {
			Entry<Object, Object> entry = reqSetIt.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (key instanceof String) {
				if (!((String) key).equals("__RCVPCK__")
						&& !((String) key).equals("__SNDPCK__")) {
					AppLogger.trace(key + "=" + value);
				}
			}
		}
		TCResult result = NatpCodec.encode(__RSP__, encoding);
		if (result.getStatus() != 1) {
			return result;
		}

		AppLogger.dump(__RSP__.getItem("__SNDPCK__"));
		AppLogger.trace("====== 结束打NATP包 ======");
		return result;
	}
	
	/**
	 * @category 通过AFA4J-HOST获取绑定IP地址
	 * @param hostIP
	 *            出参|AFA4J-HOST绑定的IP地址|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@OutParams(param = { @Param(name = "hostIP", comment = "AFA4J-HOST绑定的IP地址", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "通过AFA4J-HOST获取绑定IP地址", style = "判断型", type = "同步组件", comment = "多网卡时，支持通过AFA4J-HOST获取绑定IP地址", author = "Anonymous", date = "2016-12-23 03:35:45")
	public static TCResult getAFAIpByHost() {
		String result = "";
		try {
			result = InetAddress.getByName(AFA_HOST_NAME).getHostAddress();
		} catch (UnknownHostException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(result);
	}

}
