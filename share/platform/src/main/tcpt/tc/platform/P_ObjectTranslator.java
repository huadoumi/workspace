package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.lang.reflect.Field;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;

/**
 * 对象转换类组件
 * 
 * @date 2015-09-07 19:59:50
 */
@ComponentGroup(level = "平台", groupName = "对象转换类组件")
public class P_ObjectTranslator {

	/**
	 * @param bean
	 *            入参|Bean|{@link Object}
	 * @param dict
	 *            出参|Dict|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "bean", comment = "Bean", type = Object.class) })
	@OutParams(param = { @Param(name = "dict", comment = "Dict", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "Bean转成字典", style = "判断型", type = "同步组件", comment = "把Bean转成字典，bean属性的名称作为key，属性值（原封不动）作为value", date = "2015-09-07 08:03:44")
	public static TCResult convertBeanToDict(Object bean) {
		JavaDict dict = new JavaDict();
        TCResult result = fillPropsIntoDict(bean, dict);
        if (result.getStatus() == TCResult.STAT_SUCCESS) {
            return TCResult.newSuccessResult(dict);
        } else {
            return result;
        }
	}

	/**
	 * @param bean
	 *            入参|Bean|{@link Object}
	 * @param dict
	 *            入参|Dict|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "bean", comment = "Bean", type = Object.class),
			@Param(name = "dict", comment = "Dict", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "填充Bean属性到字典", style = "判断型", type = "同步组件", comment = "bean属性的名称作为key，属性值（原封不动）作为value", date = "2015-09-07 08:05:50")
	public static TCResult fillPropsIntoDict(Object bean, JavaDict dict) {
		Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field.setAccessible(fields, true);
        try {
            for (Field f : fields) {
                dict.setItem(f.getName(), f.get(bean));
            }
        } catch (Exception e) {
            return TCResult.newFailureResult(ErrorCode.HANDLING, e);
        }
        return TCResult.newSuccessResult();
	}

}
