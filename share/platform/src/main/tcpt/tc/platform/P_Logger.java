package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;
import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * 日志操作类组件
 * 
 * @date 2015-07-01 16:22:16
 */
@ComponentGroup(level = "平台", groupName = "日志操作类组件")
public class P_Logger {

	/**
	 * @param strOrList
	 *            入参|日志信息|{@link java.lang.Object}
	 * @return 0 异常<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "strOrList", comment = "日志信息(JavaList/String)", type = java.lang.Object.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "异常"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "信息日志", style = "判断型", type = "同步组件", date = "Wed Jul 01 16:27:37 CST 2015")
	public static TCResult info(Object strOrList) {
		if(strOrList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参日志信息参数不能为空");
		}
		if (strOrList instanceof JavaList) {
			JavaList list = (JavaList) strOrList;
			for (int i = 0; i < list.size(); i++) {
				AppLogger.info(toString(list.getItem(i)));
			}
		} else {
			AppLogger.info(toString(strOrList));
		}
		return TCResult.newSuccessResult();
	}

	private static String toString(Object strOrList) {
		if(strOrList instanceof byte[]){
			return new String((byte[])strOrList);
		}
		return String.valueOf(strOrList);
	}

	/**
	 * @param strOrList
	 *            入参|日志信息|{@link java.lang.Object}
	 * @return 0 异常<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "strOrList", comment = "日志信息(JavaList/String)", type = java.lang.Object.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "异常"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "调试日志", style = "判断型", type = "同步组件", date = "Wed Jul 01 16:34:09 CST 2015")
	public static TCResult debug(Object strOrList) {
		if(strOrList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参日志信息参数不能为空");
		}
		if (strOrList instanceof JavaList) {
			JavaList list = (JavaList) strOrList;
			for (int i = 0; i < list.size(); i++) {
				AppLogger.debug(toString(list.getItem(i)));
			}
		} else {
			AppLogger.debug(toString(strOrList));
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param strOrList
	 *            入参|日志信息|{@link java.lang.Object}
	 * @return 0 异常<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "strOrList", comment = "日志信息(JavaList/String)", type = java.lang.Object.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "异常"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "跟踪日志", style = "判断型", type = "同步组件", date = "Wed Jul 01 16:37:04 CST 2015")
	public static TCResult trace(Object strOrList) {
		if(strOrList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参日志信息参数不能为空");
		}
		if (strOrList instanceof JavaList) {
			JavaList list = (JavaList) strOrList;
			for (int i = 0; i < list.size(); i++) {
				AppLogger.trace(toString(list.getItem(i)));
			}
		} else {
			AppLogger.trace(toString(strOrList));
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param strOrList
	 *            入参|日志信息|{@link java.lang.Object}
	 * @return 0 异常<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "strOrList", comment = "日志信息(JavaList/String)", type = java.lang.Object.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "异常"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "错误日志", style = "判断型", type = "同步组件", date = "Wed Jul 01 16:39:14 CST 2015")
	public static TCResult error(Object strOrList) {
		if(strOrList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参日志信息参数不能为空");
		}
		if (strOrList instanceof JavaList) {
			JavaList list = (JavaList) strOrList;
			for (int i = 0; i < list.size(); i++) {
				AppLogger.error(toString(list.getItem(i)));
			}
		} else {
			AppLogger.error(toString(strOrList));
		}
		return TCResult.newSuccessResult();
	}

}