package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.util.ByteUtils;
import cn.com.agree.afa.util.DesUtils;

/**
 * Des加解密类组件
 * 
 * @date 2015-07-24 15:16:23
 */
@ComponentGroup(level = "平台", groupName = "Des加解密类组件")
public class P_Des {

	private static String charset = "UTF-8";
	private static final byte NULL_BYTE = 0x00;
	private static final String KFT_DES = "DES";
	private static final String KFT_DESEDE = "DESede";
	private static final String DES_CBC_NOPADDING_MODE = "DES/CBC/NoPadding";
	private static final String DES_CBC_PKCS5PADDING_MODE = "DES/CBC/PKCS5Padding";
	private static final String DES_ECB_PKCS5PADDING_MODE = "DES/ECB/PKCS5Padding";
	private static final String DESEDE_CBC_NOPADDING_MODE = "DESede/CBC/NoPadding";
	private static final String DESEDE_CBC_PKCS5PADDING_MODE = "DESede/CBC/PKCS5Padding";
	private static final String DESEDE_ECB_PKCS5PADDING_MODE = "DESede/ECB/PKCS5Padding";

	/**
	 * @param srcBytes
	 *            入参|需要合并的字节数组|byte
	 * @param result
	 *            出参|双字节合并后返回的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "srcBytes", comment = "需要合并的字节数组", type = byte[].class) })
	@OutParams(param = { @Param(name = "result", comment = "双字节合并后返回的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "合并双字节", style = "判断型", type = "同步组件", comment = "双字节合并为单字节", date = "Mon Jul 27 09:38:20 CST 2015")
	public static TCResult doubleByteToByte(byte[] srcBytes) {
		if (srcBytes == null || srcBytes.length == 0) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数不能为null，且数组元素不为0");
		}
		byte[] result = null;
		if (srcBytes.length % 2 != 0) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数数组长度不是偶数");
		}
		result = ByteUtils.toSBCS(srcBytes);
		return TCResult.newSuccessResult(result);
	}

	/**
	 * @param srcBytes
	 *            入参|需要拆分的字节数组|byte
	 * @param result
	 *            出参|拆分后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "srcBytes", comment = "需要拆分的字节数组", type = byte[].class) })
	@OutParams(param = { @Param(name = "result", comment = "拆分后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "拆分单字节", style = "判断型", type = "同步组件", comment = "单字节拆分为双字节", date = "Fri Jul 24 17:01:21 CST 2015")
	public static TCResult byteToDoubleByte(byte[] srcBytes) {
		if (srcBytes == null || srcBytes.length == 0) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数不能为null，且数组元素不为0");
		}
		byte[] result = null;
		result = ByteUtils.toDBCS(srcBytes);
		return TCResult.newSuccessResult(result);
	}

	/**
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param str
	 *            出参|加密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "str", comment = "加密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "Des3加密并拆分单字节", style = "判断型", type = "同步组件", comment = "Des3加密并拆分单字节，返回字符串不出现乱码", date = "Mon Jul 27 09:39:48 CST 2015")
	public static TCResult des3EncryptAndToDBCS(String src, String key) {
		byte[] dest = null;
		String str = null;
		try {
			dest = DesUtils.encrypt3(src.getBytes(charset),
					key.getBytes(charset));
			str = new String(ByteUtils.toDBCS(dest));
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @param enc
	 *            入参|加密后的字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param str
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "enc", comment = "加密后的字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "str", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "合并双字节后进行Des3解密", style = "判断型", type = "同步组件", comment = "合并双字节后进行Des3解密，对应des3EncryptAndToDBCS组件", date = "Mon Jul 27 09:41:59 CST 2015")
	public static TCResult toSBCSAndDes3Decrypt(String enc, String key) {
		byte[] dest = null;
		String str = null;
		try {
			dest = ByteUtils.toSBCS(enc.getBytes());
			dest = DesUtils.decrypt3(dest, key.getBytes(charset));
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "Des3加密并返回字节数组", style = "判断型", type = "同步组件", comment = "Des3加密并返回字节数组", date = "Mon Jul 27 09:42:28 CST 2015")
	public static TCResult des3Encrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = DesUtils.encrypt3(src.getBytes(charset),
					key.getBytes(charset));
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}

	/**
	 * @param dest
	 *            入参|Des3加密后的数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param str
	 *            出参|解密后得到的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "Des3加密后的数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "str", comment = "解密后得到的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "Des3解密字节数组", style = "判断型", type = "同步组件", comment = "Des3解密字节数组，对应des3Encrypt组件", date = "Mon Jul 27 09:43:26 CST 2015")
	public static TCResult des3Decrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = DesUtils.decrypt3(dest, key.getBytes(charset));
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param str
	 *            出参|加密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "str", comment = "加密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "Des加密并拆分单字节", style = "判断型", type = "同步组件", comment = "Des加密并拆分单字节，使得返回的字符串不出现乱码", date = "Mon Jul 27 09:44:52 CST 2015")
	public static TCResult desEncryptAndToDBCS(String src, String key) {
		byte[] dest = null;
		String str = null;
		try {
			dest = DesUtils.encrypt(src.getBytes(charset),
					key.getBytes(charset));
			str = new String(ByteUtils.toDBCS(dest));
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @param enc
	 *            入参|加密后的字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param str
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "enc", comment = "加密后的字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "str", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "合并双字节并进行Des解密", style = "判断型", type = "同步组件", comment = "合并双字节并进行Des解密，对应desEncryptAndToDBCS组件", date = "Mon Jul 27 09:45:50 CST 2015")
	public static TCResult toSBCSAndDesDecrypt(String enc, String key) {
		byte[] dest = null;
		String str = null;
		try {
			dest = ByteUtils.toSBCS(enc.getBytes());
			dest = DesUtils.decrypt(dest, key.getBytes(charset));
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @param src
	 *            入参|加密前的字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前的字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "Des加密并返回字节数组", style = "判断型", type = "同步组件", comment = "Des加密并返回字节数组", date = "Fri Jul 24 16:17:48 CST 2015")
	public static TCResult desEncrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = DesUtils.encrypt(src.getBytes(charset),
					key.getBytes(charset));
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}

	/**
	 * @param dest
	 *            入参|加密后的字节数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param str
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "加密后的字节数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "str", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "Des解密字节数组", style = "判断型", type = "同步组件", comment = "Des解密字节数组，对应组件desEncrypt", date = "Fri Jul 24 16:19:04 CST 2015")
	public static TCResult desDecrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = DesUtils.decrypt(dest, key.getBytes(charset));
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}
	
	/**
	 * @category DES模式加密并返回字节数组
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES各种模式通用加密并返回字节数组", style = "判断型", type = "同步组件", comment = "DES各种模式通用加密并返回字节数组", date = "2016-01-19 04:42:13")
	public static TCResult genericDesEncrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = desEncrypt(src.getBytes(charset), key, null, KFT_DES);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}
	
	/**
	 * @category DES各种模式通用解密字节数组
	 * @param dest
	 *            入参|加密后的字节数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param str
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "加密后的字节数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "str", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES各种模式通用解密字节数组", style = "判断型", type = "同步组件", comment = "DES各种模式通用解密字节数组", date = "2016-01-19 04:55:09")
	public static TCResult genericDesDecrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = desDecrypt(dest, key, null, KFT_DES);
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}
	
	/**
	 * @category DES3模式加密并返回字节数组
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES3各种模式通用加密并返回字节数组", style = "判断型", type = "同步组件", comment = "DES3各种模式通用加密并返回字节数组", date = "2016-01-19 04:42:13")
	public static TCResult genericDes3Encrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = desEncrypt(src.getBytes(charset), key, null, KFT_DESEDE);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}
	
	/**
	 * @category DES3各种模式通用解密字节数组
	 * @param dest
	 *            入参|加密后的字节数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param str
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "加密后的字节数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "str", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES3各种模式通用解密字节数组", style = "判断型", type = "同步组件", comment = "DES3各种模式通用解密字节数组", date = "2016-01-19 04:55:09")
	public static TCResult genericDes3Decrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = desDecrypt(dest, key, null, KFT_DESEDE);
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}
	
	/**
	 * @category DES/CBC/NoPadding模式加密并返回字节数组
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES_CBC_NoPadding模式加密并返回字节数组", style = "判断型", type = "同步组件", comment = "DES/CBC/NoPadding模式加密并返回字节数组", date = "2016-01-19 04:42:13")
	public static TCResult desCbcNoPaddingEncrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = desEncrypt(padNullBytes(src.getBytes(charset)), key, DES_CBC_NOPADDING_MODE, KFT_DES);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}

	/**
	 * @category DES/CBC/NoPadding模式解密字节数组
	 * @param dest
	 *            入参|加密后的字节数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param str
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "加密后的字节数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "str", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES_CBC_NoPadding模式解密字节数组", style = "判断型", type = "同步组件", comment = "DES/CBC/NoPadding模式解密字节数组", date = "2016-01-19 04:55:09")
	public static TCResult desCbcNoPaddingDecrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = desDecrypt(dest, key, DES_CBC_NOPADDING_MODE, KFT_DES);
			str = new String(removeNullBytes(dest), charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @category DES/CBC/PKCS5Padding模式加密并返回字节数组
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES_CBC_PKCS5Padding模式加密并返回字节数组", style = "判断型", type = "同步组件", comment = "DES/CBC/PKCS5Padding模式加密并返回字节数组", date = "2016-01-19 04:45:29")
	public static TCResult desCbcPKCS5PaddingEncrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = desEncrypt(src.getBytes(charset), key, DES_CBC_PKCS5PADDING_MODE, KFT_DES);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}

	/**
	 * @category DES/CBC/PKCS5Padding模式解密字节数组
	 * @param dest
	 *            入参|加密后的字节数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param src
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "加密后的字节数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "src", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES_CBC_PKCS5Padding模式解密字节数组", style = "判断型", type = "同步组件", comment = "DES/CBC/PKCS5Padding模式解密字节数组", date = "2016-01-19 04:58:01")
	public static TCResult desCbcPKCS5PaddingDecrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = desDecrypt(dest, key, DES_CBC_PKCS5PADDING_MODE, KFT_DES);
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @category DES/ECB/PKCS5Padding模式加密并返回字节数组
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES_ECB_PKCS5Padding模式加密并返回字节数组", style = "判断型", type = "同步组件", comment = "DES/ECB/PKCS5Padding模式加密并返回字节数组", date = "2016-01-19 04:49:23")
	public static TCResult desEcbPKCS5PaddingEncrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = desEncrypt(src.getBytes(charset), key, DES_ECB_PKCS5PADDING_MODE, KFT_DES);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}

	/**
	 * @category DES/ECB/PKCS5Padding模式解密字节数组
	 * @param dest
	 *            入参|加密后的字节数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param src
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "加密后的字节数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "src", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DES_ECB_PKCS5Padding模式解密字节数组", style = "判断型", type = "同步组件", comment = "DES/ECB/PKCS5Padding模式解密字节数组", date = "2016-01-19 04:59:43")
	public static TCResult desEcbPKCS5PaddingDecrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = desDecrypt(dest, key, DES_ECB_PKCS5PADDING_MODE, KFT_DES);
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @category DESede/CBC/NoPadding模式加密并返回字节数组
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DESede_CBC_NoPadding模式加密并返回字节数组", style = "判断型", type = "同步组件", comment = "DESede/CBC/NoPadding模式加密并返回字节数组", date = "2016-01-19 05:42:46")
	public static TCResult des3CbcNoPaddingEncrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = desEncrypt(padNullBytes(src.getBytes(charset)), key, DESEDE_CBC_NOPADDING_MODE, KFT_DESEDE);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}

	/**
	 * @category DESede/CBC/NoPadding模式解密字节数组
	 * @param dest
	 *            入参|加密后的字节数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param src
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "加密后的字节数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "src", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DESede_CBC_NoPadding模式解密字节数组", style = "判断型", type = "同步组件", comment = "DESede/CBC/NoPadding模式解密字节数组", date = "2016-01-19 05:44:33")
	public static TCResult des3CbcNoPaddingDecrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = desDecrypt(dest, key, DESEDE_CBC_NOPADDING_MODE, KFT_DESEDE);
			str = new String(removeNullBytes(dest), charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @category DESede/CBC/PKCS5Padding模式加密并返回字节数组
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DESede_CBC_PKCS5Padding模式加密并返回字节数组", style = "判断型", type = "同步组件", comment = "DESede/CBC/PKCS5Padding模式加密并返回字节数组", date = "2016-01-19 06:03:38")
	public static TCResult des3CbcPKCS5PaddingEncrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = desEncrypt(src.getBytes(charset), key, DESEDE_CBC_PKCS5PADDING_MODE, KFT_DESEDE);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}

	/**
	 * @category DESede/CBC/PKCS5Padding模式解密字节数组
	 * @param dest
	 *            入参|加密后的字节数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param src
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "加密后的字节数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "src", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DESede_CBC_PKCS5Padding模式解密字节数组", style = "判断型", type = "同步组件", comment = "DESede/CBC/PKCS5Padding模式解密字节数组", date = "2016-01-19 06:05:20")
	public static TCResult des3CbcPKCS5PaddingDecrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = desDecrypt(dest, key, DESEDE_CBC_PKCS5PADDING_MODE, KFT_DESEDE);
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}

	/**
	 * @category DESede/ECB/PKCS5Padding模式加密并返回字节数组
	 * @param src
	 *            入参|加密前字符串|{@link java.lang.String}
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param dest
	 *            出参|加密后的字节数组|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "加密前字符串", type = java.lang.String.class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dest", comment = "加密后的字节数组", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DESede_ECB_PKCS5Padding模式加密并返回字节数组", style = "判断型", type = "同步组件", comment = "DESede/ECB/PKCS5Padding模式加密并返回字节数组", date = "2016-01-19 06:07:01")
	public static TCResult des3EcbPKCS5PaddingEncrypt(String src, String key) {
		byte[] dest = null;
		try {
			dest = desEncrypt(src.getBytes(charset), key, DESEDE_ECB_PKCS5PADDING_MODE, KFT_DESEDE);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(dest);
	}

	/**
	 * @category DESede/ECB/PKCS5Padding模式解密字节数组
	 * @param dest
	 *            入参|加密后的字节数组|byte
	 * @param key
	 *            入参|密钥|{@link java.lang.String}
	 * @param src
	 *            出参|解密后的字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dest", comment = "加密后的字节数组", type = byte[].class),
			@Param(name = "key", comment = "密钥", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "src", comment = "解密后的字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "DESede_ECB_PKCS5Padding模式解密字节数组", style = "判断型", type = "同步组件", comment = "DESede/ECB/PKCS5Padding模式解密字节数组", date = "2016-01-19 06:08:17")
	public static TCResult des3EcbPKCS5PaddingDecrypt(byte[] dest, String key) {
		String str = null;
		try {
			dest = desDecrypt(dest, key, DESEDE_ECB_PKCS5PADDING_MODE, KFT_DESEDE);
			str = new String(dest, charset);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult(str);
	}


	/**
	 * DES/DES3加密
	 * 
	 * @param 明文数据
	 * @param 密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] desEncrypt(byte[] data, String key, String decMode,
			String keyFactoryType) throws Exception {
		return doDes(data, initKey(key,keyFactoryType), decMode, keyFactoryType, Cipher.ENCRYPT_MODE);
	}

	/**
	 * DES/DES3解密
	 * 
	 * @param 密文数据
	 * @param 密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] desDecrypt(byte[] data, String key, String decMode,
			String keyFactoryType) throws Exception {
		byte[] plaintext = doDes(data, initKey(key,keyFactoryType), decMode, keyFactoryType, Cipher.DECRYPT_MODE);
		return plaintext;
	}
	
	/**
	 * 生成合法密钥
	 * 
	 * @param 密钥
	 * @param 加密模式(DES或者DESede)
	 * @return
	 * @throws Exception
	 */
	private static byte[] initKey(String key, String keyFactoryType) throws Exception {
		SecureRandom secureRandom = null;    
        if (key != null) {
        	byte[] buf = key.getBytes(charset);
        	if(KFT_DES.equals(keyFactoryType) && buf.length>=8)
        		return buf;
        	if(KFT_DESEDE.equals(keyFactoryType) && buf.length>=24)
        		return buf;
            secureRandom = new SecureRandom(buf);  
        } else {  
            secureRandom = new SecureRandom(keyFactoryType.getBytes(charset));  
        }  
        KeyGenerator kg = KeyGenerator.getInstance(keyFactoryType);  
        kg.init(secureRandom);  
        SecretKey secretKey = kg.generateKey();  
		return secretKey.getEncoded();
	}

	private static byte[] doDes(byte[] data, byte[] key, String transformation,
			String keyFactoryType, int opmode) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(keyFactoryType);
		KeySpec keySpec = null;
		
		//DES方式
		if (KFT_DES.equals(keyFactoryType)) {
			keySpec = new DESKeySpec(key);
		}
		//DES3方式
		if (KFT_DESEDE.equals(keyFactoryType)) {
			keySpec = new DESedeKeySpec(key);
		}
		SecretKey desKey = keyFactory.generateSecret(keySpec);
		Cipher c = null;
		if (transformation!=null) {
			c = Cipher.getInstance(transformation);
			if(transformation.contains("/CBC/")){
				IvParameterSpec param = new IvParameterSpec(new byte[8]);
				c.init(opmode, desKey, param);
			}else{
				c.init(opmode, desKey);
			}
		} else {
			c = Cipher.getInstance(keyFactoryType);
			c.init(opmode, desKey);
		}
		return c.doFinal(data);
	}

	/**
	 * 补充空字节，成8的倍数
	 * 
	 * @param data
	 * @return
	 */
	private static byte[] padNullBytes(byte[] data) {
		int mod = data.length % 8;
		if (mod == 0) {
			return data;
		}
		byte[] result = new byte[data.length + (8 - mod)];
		System.arraycopy(data, 0, result, 0, data.length);
		for (int i = data.length; i < result.length; i++) {
			result[i] = NULL_BYTE;
		}
		return result;
	}

	/**
	 * 去掉多余的空字节
	 * 
	 * @param data
	 * @return
	 */
	private static byte[] removeNullBytes(byte[] data) {
		int nbCount = 0;
		for (int i = data.length - 1; i >= 0; i--) {
			if (data[i] != NULL_BYTE) {
				break;
			}
			nbCount++;
		}
		if (nbCount > 0) {
			byte[] result = new byte[data.length - nbCount];
			System.arraycopy(data, 0, result, 0, result.length);
			return result;
		} else {
			return data;
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String src = new String("汉字有多少种格式，He is stupid.I don't know how to express that.");
		String key = null;

		 TCResult r1 = desCbcNoPaddingEncrypt(src, key);
		 byte[] encStr = (byte[]) (r1.getOutputParams().get(0));
		 System.out.println("编码后的字符串   " + encStr);
		 TCResult r2 = desCbcNoPaddingDecrypt(encStr, key);
		 String decStr = (String) (r2.getOutputParams().get(0));
		 System.out.println("解码后的字符串   " + decStr);
		 System.out.println("=====================================");
		 TCResult r3 = desCbcPKCS5PaddingEncrypt(src, key);
		 byte[] encStr1 = (byte[]) (r3.getOutputParams().get(0));
		 System.out.println("编码后的字符串   " + encStr1);
		 TCResult r4 = desCbcPKCS5PaddingDecrypt(encStr1, key);
		 String decStr1 = (String) (r4.getOutputParams().get(0));
		 System.out.println("解码后的字符串   " + decStr1);
		 System.out.println("=====================================");
		 TCResult r5 = desEcbPKCS5PaddingEncrypt(src, key);
		 byte[] encStr2 = (byte[]) (r5.getOutputParams().get(0));
		 System.out.println("编码后的字符串   " + encStr2);
		 TCResult r6 = desEcbPKCS5PaddingDecrypt(encStr2, key);
		 String decStr2 = (String) (r6.getOutputParams().get(0));
		 System.out.println("解码后的字符串   " + decStr2);

		System.out.println("**************************************");
		TCResult r7 = des3CbcNoPaddingEncrypt(src, key);
		byte[] encStr3 = (byte[]) (r7.getOutputParams().get(0));
		System.out.println("编码后的字符串   " + encStr3);
		TCResult r8 = des3CbcNoPaddingDecrypt(encStr3, key);
		String decStr3 = (String) (r8.getOutputParams().get(0));
		System.out.println("解码后的字符串   " + decStr3);
		System.out.println("=====================================");
		TCResult r9 = des3CbcPKCS5PaddingEncrypt(src, key);
		byte[] encStr4 = (byte[]) (r9.getOutputParams().get(0));
		System.out.println("编码后的字符串   " + encStr4);
		TCResult r10 = des3CbcPKCS5PaddingDecrypt(encStr4, key);
		String decStr4 = (String) (r10.getOutputParams().get(0));
		System.out.println("解码后的字符串   " + decStr4);
		
		System.out.println("=====================================");
		TCResult r11 = des3EcbPKCS5PaddingEncrypt(src, key);
		byte[] encStr5 = (byte[]) (r11.getOutputParams().get(0));
		System.out.println("编码后的字符串   " + encStr5);
		TCResult r12 = des3EcbPKCS5PaddingDecrypt(encStr5, key);
		String decStr5 = (String) (r12.getOutputParams().get(0));
		System.out.println("解码后的字符串   " + decStr5);
		System.out.println("++++++++++++++++++++++++++++++++++++++");
		TCResult r13 = genericDesEncrypt(src, key);
		byte[] encStr6 = (byte[]) (r13.getOutputParams().get(0));
		System.out.println("编码后的字符串   " + encStr6);
		TCResult r14 = genericDesDecrypt(encStr6, key);
		String decStr6 = (String) (r14.getOutputParams().get(0));
		System.out.println("解码后的字符串   " + decStr6);
		
		System.out.println("=====================================");
		TCResult r15 = genericDes3Encrypt(src, key);
		byte[] encStr7 = (byte[]) (r15.getOutputParams().get(0));
		System.out.println("编码后的字符串   " + encStr7);
		TCResult r16 = genericDes3Decrypt(encStr7, key);
		String decStr7 = (String) (r16.getOutputParams().get(0));
		System.out.println("解码后的字符串   " + decStr7);
		// TCResult r1 = desEncryptAndToDBCS(src, key);
		// String encStr = (String) (r1.getOutputParams().get(0));
		// System.out.println("编码后的字符串   " + encStr);
		// TCResult r2 = toSBCSAndDesDecrypt(encStr, key);
		// String decStr = (String) (r2.getOutputParams().get(0));
		// System.out.println("解码后的字符串   " + decStr);

		// TCResult r1 = des3Encrypt(src, key);
		// byte[] encBytes = (byte[]) (r1.getOutputParams().get(0));
		// TCResult r2 = des3Decrypt(encBytes, key);
		// String decStr = (String) (r2.getOutputParams().get(0));
		// System.out.println(decStr);

		// byte[] bs1 = src.getBytes("UTF-8");
		// TCResult r1 = byteToDoubleByte(bs1);
		// byte[] res1 = (byte[])(r1.getOutputParams().get(0));
		// TCResult r2 = doubleByteToByte(res1);
		// byte[] res2 = (byte[])(r2.getOutputParams().get(0));
		// System.out.println( new String(res2,"UTF-8"));
	}

}
