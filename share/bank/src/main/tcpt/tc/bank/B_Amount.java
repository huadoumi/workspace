package tc.bank;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

/**
 * 金额处理类组件
 * 
 * @date 2019-08-02 11:37:40
 */
@ComponentGroup(level = "银行", groupName = "金额处理类组件")
public class B_Amount {

	/**
	 * @category 获取子账户余额
	 * @param sourceList
	 *            入参|子账户余额列表|{@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @since flowAmount 出参|活期余额|{@link java.lang.String}
	 * @since fixAmount 出参|定期余额|{@link java.lang.String}
	 * @return 0 失败<br>
	 * 		1 成功<br>
	 */
	@InParams(param = {
			@Param(name = "sourceList", comment = "子账户余额列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = { @Param(name = "flowAmount", comment = "活期余额", type = java.lang.String.class),
			@Param(name = "fixAmount", comment = "定期余额", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@galaxy.ide.tech.cpt.Component(label = "获取子账户余额", style = "判断型", type = "同步组件", author = "pang", date = "2019-08-02 11:41:41")
	public static TCResult B_gatherSubAmount(JavaList sourceList) {
		if (sourceList == null) {
			return TCResult.newFailureResult(ErrorCode.PARAM, "子账户余额列表不能为空");
		}
		String flowAmount = null;
		String fixAmount = null;
		for(Object source : sourceList) {
			if(source instanceof JavaDict) {
				JavaDict javaDict = (JavaDict) source;
				if("0".equals(javaDict.get("sub_type"))) {//活期
					flowAmount = String.valueOf(javaDict.get("amount"));
				}else if("1".equals(javaDict.get("sub_type"))) {//定期
					fixAmount = String.valueOf(javaDict.get("amount"));
				}
			}else {
				return TCResult.newFailureResult(ErrorCode.PARAM, "子账户余额列表不合法");
			}
			
		}
		if(flowAmount == null || "".equals(flowAmount)) {
			flowAmount = "0";
		}
		if(fixAmount == null || "".equals(fixAmount)) {
			fixAmount = "0";
		}
		return TCResult.newSuccessResult(flowAmount,fixAmount);
	}

}
