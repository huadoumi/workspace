package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;
import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;

/**
 * 调试类组件
 * 
 * @date 2015-07-01 15:54:35
 */
@ComponentGroup(level = "平台", groupName = "调试类组件")
public class P_Debug {

	/**
	 * @return 1 处理成功<br/>
	 */
	@Returns(returns = { @Return(id = "1", desp = "处理成功") })
	@Component(label = "空组件", style = "处理型", type = "同步组件", comment = "空组件，用于进行断点调试", date = "Wed Jul 01 15:56:04 CST 2015")
	public static TCResult pass() {
		// do nothing
		return TCResult.newSuccessResult();
	}

	/**
	 * @param sleepTime
	 *            入参|休眠时间(秒)|int
	 * @return 0 处理失败<br/>
	 *         1 处理成功<br/>
	 */
	@InParams(param = { @Param(name = "sleepTime", comment = "休眠时间(秒)", type = int.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "处理失败"),
			@Return(id = "1", desp = "处理成功") })
	@Component(label = "休眠组件", style = "判断型", type = "同步组件", date = "Wed Jul 15 14:44:49 CST 2015")
	public static TCResult sleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime * 1000);
		} catch (InterruptedException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		return TCResult.newSuccessResult();
	}

}