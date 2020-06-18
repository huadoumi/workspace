package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.util.ebcd.AsciiAndBcd;
import cn.com.agree.afa.util.ebcd.AsciiAndEbcd;



/**
 * EBCD编码转换类组件
 * 
 * @date 2015-07-20 11:31:43
 */
@ComponentGroup(level = "平台", groupName = "EBCD编码转换类组件")
public class P_EBCD {

	/**
	 * @param srcStr
	 *            入参|源ASCII编码的数据|{@link java.lang.String}
	 * @param desBytes
	 *            出参|BCD编码的数据|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "srcStr", comment = "源ASCII编码的数据", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "desBytes", comment = "BCD编码的数据", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "ASCII编码转BCD编码", style = "判断型", type = "同步组件", comment = "ASCII编码转BCD编码", date = "Mon Jul 20 16:28:18 CST 2015")
	public static TCResult ASCII2BCD(String srcStr) {
		byte[] srcBytes = srcStr.getBytes();
		int srcLen = srcBytes.length;
		byte[] destBytes = AsciiAndBcd.ASCII_To_BCD(srcBytes, srcBytes.length);
		int iLen = 0;
		iLen = srcLen / 2 + srcLen % 2;
		return TCResult.newSuccessResult(destBytes, iLen);
	}

	/**
	 * @param srcStr
	 *            入参|源BCD编码|{@link java.lang.String}
	 * @param desBytes
	 *            出参|目标ASCII编码|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "srcStr", comment = "源BCD编码", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "desBytes", comment = "目标ASCII编码", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "BCD编码转ASCII编码", style = "判断型", type = "同步组件", comment = "BCD编码转ASCII编码", date = "Tue Jul 21 16:56:21 CST 2015")
	public static TCResult BCD2ASCII(String srcStr) {
		byte[] srcBytes = srcStr.getBytes();
		int srcLen = srcBytes.length;
		byte[] destBytes = AsciiAndBcd.BCD_To_ASCII(srcBytes, srcLen * 2);
		int iLen = 0;
		iLen = srcLen * 2;
		return TCResult.newSuccessResult(destBytes, iLen);
	}

	/**
	 * @param src
	 *            入参|源ASCII编码的数据|{@link java.lang.String}
	 * @param des
	 *            出参|BCD编码的数据|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "srcStr", comment = "源ASCII编码的数据", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "desBytes", comment = "EBCD编码的数据", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "ASCII编码转EBCD编码", style = "判断型", type = "同步组件", comment = "ASCII编码转EBCD编码", date = "Mon Jul 20 14:49:39 CST 2015")
	public static TCResult ASCII2EBCD(String srcStr) {
		byte[] srcBytes = srcStr.getBytes();
		byte[] destBytes = AsciiAndEbcd.ASCIIToEBCDIC(srcBytes);
		int iLen = 0;
		for (int i = 0; i < destBytes.length; i++) {
			if (destBytes[i] == 0x00) {
				iLen = i;
				break;
			}
		}
		return TCResult.newSuccessResult(destBytes, iLen);
	}

	/**
	 * @param src
	 *            入参|源EBCD编码|{@link java.lang.String}
	 * @param src
	 *            出参|目标ASCII编码|byte
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "srcStr", comment = "源EBCD编码", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "desBytes", comment = "目标ASCII编码", type = byte[].class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "EBCD编码转ASCII编码", style = "判断型", type = "同步组件", comment = "EBCD编码转ASCII编码", date = "Mon Jul 20 15:20:16 CST 2015")
	public static TCResult EBCD2ASCII(String srcStr) {
		byte[] srcBytes = srcStr.getBytes();
		byte[] destBytes = AsciiAndEbcd.EBCDICToASCII(srcBytes);
		int iLen = 0;
		for (int ii = 0; ii < destBytes.length; ii++) {
			if (destBytes[ii] == 0x00) {
				iLen = ii;
				break;
			}
		}
		return TCResult.newSuccessResult(destBytes, iLen);
	}

}
