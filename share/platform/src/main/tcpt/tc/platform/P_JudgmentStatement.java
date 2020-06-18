package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;
import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.TCResult;

/**
 * 判断语句类组件
 * 
 * @date 2015-07-17 16:42:57
 */
@ComponentGroup(level = "平台", groupName = "判断语句类组件")
public class P_JudgmentStatement {

	/**
	 * @param bool
	 *            入参|需要判断的对象|{@link java.lang.Object}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "bool", comment = "需要判断的对象", type = java.lang.Object.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "布尔判断框架", style = "判断型", type = "同步组件", comment = "布尔判断框架,返回ture/false", date = "Wed Jul 22 14:33:24 CST 2015")
	public static TCResult boolFrame(Object bool) {
		AppLogger.trace("调用虚拟组件  'boolFrame'");
		if (bool == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "bool 对象不能为空");
		}
		if (bool instanceof Boolean) {
			if (((Boolean) bool).booleanValue()) {
				return TCResult.newSuccessResult();
			} else {
				return new TCResult(TCResult.STAT_FAILURE);
			}
		} else if (bool instanceof Integer) {
			if (((Integer) bool).intValue() >= 1) {
				return TCResult.newSuccessResult();
			} else {
				return new TCResult(TCResult.STAT_FAILURE);
			}
		} else {
			return new TCResult(TCResult.STAT_FAILURE);
		}
	}

	/**
	 * @param src
	 *            入参|源匹配字符串|{@link java.lang.String}
	 * @param case1
	 *            入参|匹配模式1|{@link java.lang.String}
	 * @param case2
	 *            入参|匹配模式2|{@link java.lang.String}
	 * @param case3
	 *            入参|匹配模式3|{@link java.lang.String}
	 * @param case4
	 *            入参|匹配模式4|{@link java.lang.String}
	 * @param case5
	 *            入参|匹配模式5|{@link java.lang.String}
	 * @param case6
	 *            入参|匹配模式6|{@link java.lang.String}
	 * @param case7
	 *            入参|匹配模式7|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 与case1匹配<br/>
	 *         2 与case2匹配<br/>
	 *         3 与case3匹配<br/>
	 *         4 与case4匹配<br/>
	 *         5 与case5匹配<br/>
	 *         6 与case6匹配<br/>
	 *         7 与case7匹配<br/>
	 */
	@InParams(param = {
			@Param(name = "src", comment = "源匹配字符串", type = java.lang.String.class),
			@Param(name = "case1", comment = "匹配模式1", type = java.lang.String.class),
			@Param(name = "case2", comment = "匹配模式2", type = java.lang.String.class),
			@Param(name = "case3", comment = "匹配模式3", type = java.lang.String.class),
			@Param(name = "case4", comment = "匹配模式4", type = java.lang.String.class),
			@Param(name = "case5", comment = "匹配模式5", type = java.lang.String.class),
			@Param(name = "case6", comment = "匹配模式6", type = java.lang.String.class),
			@Param(name = "case7", comment = "匹配模式7", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "与case1匹配"),
			@Return(id = "2", desp = "与case2匹配"),
			@Return(id = "3", desp = "与case3匹配"),
			@Return(id = "4", desp = "与case4匹配"),
			@Return(id = "5", desp = "与case5匹配"),
			@Return(id = "6", desp = "与case6匹配"),
			@Return(id = "7", desp = "与case7匹配") })
	@Component(label = "字符串SWITCH匹配", style = "判断型", type = "同步组件", comment = "字符串SWITCH匹配", date = "Wed Jul 22 15:31:54 CST 2015")
	public static TCResult switchCaseFrame(String src, String case1,
			String case2, String case3, String case4, String case5,
			String case6, String case7) {
		AppLogger.trace("调用虚拟组件  'switchCaseFrame'");
		if (src == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "src 源匹配对象不能为空");
		}
		if (src.equals(case1)) {
			return new TCResult(1);
		} else if (src.equals(case2)) {
			return new TCResult(2);
		} else if (src.equals(case3)) {
			return new TCResult(3);
		} else if (src.equals(case4)) {
			return new TCResult(4);
		} else if (src.equals(case5)) {
			return new TCResult(5);
		} else if (src.equals(case6)) {
			return new TCResult(6);
		} else if (src.equals(case7)) {
			return new TCResult(7);
		} else {
			return new TCResult(0);
		}
	}

}
