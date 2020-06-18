package tc.platform;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;

/**
 * 解编码类组件
 * 
 * @date 2015-07-03 17:33:6
 */
@ComponentGroup(level = "平台", groupName = "解编码类组件")
public class P_CodecTools {

	private static final String CHARSET = "UTF-8";

	/**
	 * @param sourceString
	 *            入参|编码前字符串|{@link java.lang.String}
	 * @param outStr
	 *            出参|编码后字符串|{@link java.lang.String}
	 * @return 0 编码失败<br/>
	 *         1 编码成功<br/>
	 */
	@InParams(param = { @Param(name = "sourceString", comment = "编码前字符串", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "outStr", comment = "编码后字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "编码失败"),
			@Return(id = "1", desp = "编码成功") })
	@Component(label = "bas64编码", style = "判断型", type = "同步组件", date = "Fri Jul 03 17:35:39 CST 2015")
	public static TCResult b64Encode(String sourceString) {
		if (sourceString == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参编码前字符串参数不能为空");
		}
		BASE64Encoder b64Encoder = new BASE64Encoder();
		String encodeCode;
		try {
			encodeCode = b64Encoder.encode(sourceString.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(encodeCode);
	}

	/**
	 * @param sourceString
	 *            入参|解码前字符串|{@link java.lang.String}
	 * @param outStr
	 *            出参|解码后字符串|{@link java.lang.String}
	 * @return 0 编码失败<br/>
	 *         1 编码成功<br/>
	 */
	@InParams(param = { @Param(name = "sourceString", comment = "解码前字符串", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "outStr", comment = "解码后字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "编码失败"),
			@Return(id = "1", desp = "编码成功") })
	@Component(label = "base64解码", style = "判断型", type = "同步组件", date = "Mon Jul 06 09:37:33 CST 2015")
	public static TCResult b64Decode(String sourceString) {
		try {
			if (sourceString == null) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参解码前字符串参数不能为空");
			}
			BASE64Decoder b64Decoder = new BASE64Decoder();
			byte[] decodeCode = b64Decoder.decodeBuffer(sourceString);
			return TCResult.newSuccessResult(new String(decodeCode, CHARSET));
		} catch (IOException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
	}

	/**
	 * @param inStr
	 *            入参|被加密的字符串|{@link java.lang.String}
	 * @param charsetName
	 *            入参|加密使用的字符集,默认为GBK|{@link java.lang.String}
	 * @param outStr
	 *            出参|加密后的字符串|{@link java.lang.String}
	 * @return 0 加密失败<br/>
	 *         1 加密成功<br/>
	 */
	@InParams(param = {
			@Param(name = "inStr", comment = "被加密的字符串", type = java.lang.String.class),
			@Param(name = "charsetName", comment = "加密使用的字符集,默认为GBK", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "outStr", comment = "加密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "加密失败"),
			@Return(id = "1", desp = "加密成功") })
	@Component(label = "字符串加密", style = "判断型", type = "同步组件", comment = "将输入的字符串按照MD5加密算法进行加密,加密后的结果在返回的list[3]中", date = "Mon Jul 06 09:43:24 CST 2015")
	public static TCResult md5Encrypt(String inStr, String charsetName) {
		try {
			if (inStr == null) {
				return TCResult
						.newFailureResult(ErrorCode.AGR, "被加密的字符串参数不能为空");
			}
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
					'9', 'a', 'b', 'c', 'd', 'e', 'f' };
			// 获取MD5摘要算法的MessageDigest对象
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			if (charsetName == null || charsetName.equals("")) {
				charsetName = "GBK";
			}
			// 使用给定的字节更新
			try {
				md5.update(inStr.getBytes(charsetName));
			} catch (UnsupportedEncodingException e) {
				return TCResult.newFailureResult(ErrorCode.HANDLING, e);
			}
			// 获取密文
			byte[] encodeCode = md5.digest();
			// 把密文转换成十六进制的字符串形式
			int len = encodeCode.length;
			char[] str = new char[len * 2];
			int k = 0;
			for (int i = 0; i < len; i++) {
				byte byte0 = encodeCode[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return TCResult.newSuccessResult(new String(str));
		} catch (NoSuchAlgorithmException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
	}

}