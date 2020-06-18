package tc.platform;

import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

/**
 * 资源组件
 * 
 * @date 2020-05-25 14:6:56
 */
@ComponentGroup(level = "服务", groupName = "资源", projectName = "P0002", appName = "P0002")
public class Resource {

	/**
	 * @category 遍历组件
	 * @param dict 入参|遍历字典|{@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param aaaa 入参|aaaa|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dict", comment = "遍历字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "aaaa", comment = "aaaa", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "遍历组件", style = "回调型", type = "同步组件", author = "pang", date = "2020-05-25 04:27:51")
	public static TCResult loop(JavaDict dict, String aaaa) {
		return TCResult.newSuccessResult();
	}

}
