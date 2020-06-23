package tc.Agree.bookMgr;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

/**
 * 测试
 * 
 * @date 2018-07-17 18:41:34
 */
@ComponentGroup(level = "服务", groupName = "通用业务", projectName = "Agree", appName = "bookMgr")
public class A_Packages {
	/**
	 * @category 解析键值对
	 * @param source
	 *            入参|源数据|{@link Object}
	 * @param container
	 *            入参|容器|{@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 * 		1 成功<br/>
	 */
	@InParams(param = { @Param(name = "source", comment = "源数据", type = Object.class),
			@Param(name = "container", comment = "容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "解析键值对", style = "判断型", type = "同步组件", author = "Administrator", date = "2018-06-29 10:26:38")
	public static TCResult A_parseKV(Object source, JavaDict container) {
		if(source == null)
			return TCResult.newFailureResult(ErrorCode.AGR, "source 入参为空");
		if(container == null)
			return TCResult.newFailureResult(ErrorCode.AGR, "container 入参为空");
		
		String kvString=null;
		if(source instanceof byte[]) {
			kvString=new String((byte[]) source);
		}else if(source instanceof String) {
			kvString=(String) source;
		}else {
			return TCResult.newFailureResult(ErrorCode.AGR, "不支持的参数类型."+source.getClass().getName());
		}
		String [] kvs=kvString.split("\\s*&\\s*");
		for(String kv:kvs) {
			String[] kvArray=kv.split("\\s*=\\s*");
			if(kvArray.length!=2)
				continue;
			container.put(kvArray[0], kvArray[1]);
		}
		return TCResult.newSuccessResult();
	}
	
	public static void main(String[] args) {
		
	}

	
}
