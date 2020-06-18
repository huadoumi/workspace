package tc.platform;

import java.util.UUID;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;
import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

/**
 * Utils
 * 
 * @date 2016-09-12 18:45:9
 */
@ComponentGroup(level = "平台", groupName = "自定义工具")
public class P_Utils {

	/**
	 * @category UUID
	 * @param uuid
	 *            出参|uuid|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@OutParams(param = { @Param(name = "uuid", comment = "uuid", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "生成UUID", style = "判断型", type = "同步组件", comment = "获得UUID", date = "2016-09-12 06:48:22")
	public static TCResult P_getUUID() {
		return TCResult.newSuccessResult(UUID.randomUUID().toString());
	}

	/**
	 * @category 合并字典
	 * @param dict1
	 *            入参|字典1| {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param dict2
	 *            入参|字典2| {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param dictName1
	 *            入参|字典名1|{@link java.lang.String}
	 * @param dictName2
	 *            入参|字典名2|{@link java.lang.String}
	 * @param dict
	 *            出参|返回字典| {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dict1", comment = "字典1", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "dict2", comment = "字典2", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "dictName1", comment = "字典名1", type = java.lang.String.class),
			@Param(name = "dictName2", comment = "字典名2", type = java.lang.String.class) })
	@OutParams(param = {
			@Param(name = "dict", comment = "返回字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "合并字典", style = "判断型", type = "同步组件", comment = "合并两个数据字典", date = "2016-09-20 06:51:06")
	public static TCResult P_mergeDict(JavaList dict1, JavaList dict2, String dictName1, String dictName2) {
		JavaDict dict = new JavaDict();
		dict.setItem(dictName1, dict1);
		dict.setItem(dictName2, dict2);
		return TCResult.newSuccessResult(dict);
	}



}
