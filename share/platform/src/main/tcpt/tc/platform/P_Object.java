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
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * @date 2015-07-01 11:29:46
 */
@ComponentGroup(level = "平台", groupName = "对象操作类组件")
public class P_Object {

	/**
	 * @param __REQ__
	 *            入参|数据字典容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param varList
	 *            入参|要追加的变量名称|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param listIndex
	 *            入参|追加的下标|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param valueList
	 *            入参|要追加的值|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "__REQ__", comment = "数据字典容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "varList", comment = "要追加的变量名称", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "listIndex", comment = "追加的下标", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "valueList", comment = "要追加的值", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "为容器内的List追加信息", style = "判断型", type = "同步组件", comment = "使用示例：假设目前有__REQ__容器，已有内容如下：{\"bank\":[\"CBC\",\"ABC\"], \"country\":[[\"China\",\"Japan\"],[\"UK\",\"Germany\"]]}。现想要为bank这个增加工行ICBC，为country中的亚洲国家增加韩国Korea。则组件的入参应该为：(__REQ__, [\"bank\", \"country\"], [-1, 0], [\"ICBC\", \"Korea\"])。需要注意到，当listIndex列表中的-1代表为__REQ__对应的\"bank\"字段的值不是一个嵌套的List。若大于-1则代表其容器里这个字段对应的值是一个嵌套的列表，index标注了第二层List的下标", date = "2015-12-21 10:40:23")
	public static TCResult appendList(JavaDict __REQ__, JavaList varList,
			JavaList listIndex, JavaList valueList) {
		if (__REQ__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参数据字典容器参数非法，为null");
		}
		if (varList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参要追加的变量名称参数非法，为Null");
		}
		if (listIndex == null) {
			return TCResult
					.newFailureResult(ErrorCode.AGR, "入参追加的下标参数非法，为Null");
		}
		if (valueList == null) {
			return TCResult
					.newFailureResult(ErrorCode.AGR, "入参要追加的值参数非法，为Null");
		}
		int varListLen = varList.size();
		for (int i = 0; i < varListLen; i++) {
			Object keyItem = varList.getItem(i);
			Object valueItem = valueList.getItem(i);
			int index = listIndex.getItem(i);
			if (index == -1) {
				((JavaList) __REQ__.get(keyItem)).add(valueItem);
			} else {
				((JavaList) (((JavaList) __REQ__.get(keyItem)).get(index)))
						.add(valueItem);
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param argContext
	 *            入参|数据字典容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param dataObjList
	 *            入参|数据对象描述list|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "argContext", comment = "数据字典容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "dataObjList", comment = "数据对象描述list", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "创建信息的数据对象", style = "判断型", type = "同步组件", comment = "按照数据对象的描述list创建数据对象,数据对象描述list的格式为[[\"名称\",类型(JavaDict, JavaList)], ...]。例如想要创建一个名称为container的JavaDict，则参数为[[\"container\", {}]]，若为JavaList，则参数为[[\"container\", []]]", date = "2015-12-21 10:20:03")
	public static TCResult createObject(JavaDict argContext,
			JavaList dataObjList) {
		if (argContext == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参数据字典容器参数非法，为Null");
		}
		if (dataObjList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参数据对象描述list参数非法，为null");
		}
		for (Object item : dataObjList) {
			if (!(item instanceof JavaList)) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"数据对象描述内部格式非法,类型不为列表");
			}
			JavaList listItem = (JavaList) item;
			if (listItem.size() != 2) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"数据对象描述内部格式非法,列表内参数非法");
			}
			Object type = listItem.get(1);
			Object key = listItem.get(0);
			if (type instanceof JavaDict) {
				argContext.setItem(key, new JavaDict());
			} else if (type instanceof JavaList) {
				argContext.setItem(key, new JavaList());
			} else {
				return TCResult.newFailureResult(ErrorCode.OBJCTL,
						"数据对象描述中数据的对象类型" + type.getClass().getSimpleName()
								+ "非法,现仅支持JavaList,JavaDict");
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param inObj
	 *            入参|输入对象|{@link java.lang.Object}
	 * @param outStr
	 *            出参|输出字符串|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "inObj", comment = "输入对象", type = java.lang.Object.class) })
	@OutParams(param = { @Param(name = "outStr", comment = "输出字符串", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "对象转换为字符串", style = "判断型", type = "同步组件", comment = "将传入的对象转换为字符串(返回值是该Java对象的toString()方法返回的值)", date = "2015-12-21 10:42:05")
	public static TCResult convertObjectToStr(Object inObj) {
		if (inObj == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参输入对象参数非法，为Null");
		}
		return TCResult.newSuccessResult(inObj.toString());
	}

	/**
	 * @param resource
	 *            入参|源数据|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param result
	 *            入参|指定字符串|{@link java.lang.String}
	 * @param resource
	 *            出参|转换后的数据|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "resource", comment = "源数据", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "result", comment = "指定字符串", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "resource", comment = "转换后的数据", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "过滤容器结果Null信息", style = "判断型", type = "同步组件", comment = "以递归的方式将List中的所有null值替换成指定字符串。使用示例：例如已有一个List形式如下：[\"Ubuntu\", null, \"Redhat\", null, [\"Suse\", \"Fedora\", null, \"CentOS\"] ]。则可以通过本组件，将这个list中原本为null的元素替换成特定字符串\"unix-like\"。替换后的List如下所示：[\"Ubuntu\", \"unix-like\", \"Redhat\", \"unix-like\", [\"Suse\", \"Fedora\", \"unix-like\", \"CentOS\"] ]", date = "2015-12-21 10:49:27")
	public static TCResult filterNull(JavaList resource, String result) {
		if (result == null) {
			result = "";
		}
		if (resource == null) {
			return TCResult
					.newFailureResult(ErrorCode.AGR, "输入参数源数据参数非法，为null");
		}
		int len = resource.size();
		for (int i = 0; i < len; i++) {
			Object item = resource.get(i);
			if (item == null) {
				resource.set(i, result);
				continue;
			}
			if (item instanceof JavaList) {
				resource.set(i, filterNull((JavaList) item, result)
						.getOutputParams().get(0));
			}
		}
		return TCResult.newSuccessResult(resource);
	}

	/**
	 * @param __REQ__
	 *            入参|数据字典容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param varList
	 *            入参|要替换的变量名称列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param listIndex
	 *            入参|要替换的下标列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param valueList
	 *            入参|要替换的值列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "__REQ__", comment = "数据字典容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "varList", comment = "要替换的变量名称列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "listIndex", comment = "要替换的下标列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "valueList", comment = "要替换的值列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "List替换信息", style = "判断型", type = "同步组件", comment = "替换容器内List的信息，例如已有容器__REQ__: {\"小明\": [\"男\", 18, \"学生\"], \"小红\": [\"女\", 18, \"学生\"]}。现需要把小明由学生身份改为老师，并将小红的年龄改为30。则组件入参如下(__REQ__, [\"小明\"，\"小红\"], [2, 1], [\"老师\", 30])", date = "2015-12-21 11:34:30")
	public static TCResult replaceListValue(JavaDict __REQ__, JavaList varList,
			JavaList listIndex, JavaList valueList) {
		if (__REQ__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参数据字典容器参数非法，为null");
		}
		if (varList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参要替换的变量名称列表参数非法，为null");
		}
		if (listIndex == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参要替换的下标列表参数非法，为null");
		}
		if (valueList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参要替换的值列表参数非法，为null");
		}
		int len = varList.size();
		for (int i = 0; i < len; i++) {
			JavaList list = (JavaList) __REQ__.get(varList.get(i));
			list.set((Integer) listIndex.get(i), valueList.get(i));
		}
		return TCResult.newSuccessResult();
	}
	
	/**
	 * @category List合并
	 * @param srcList
	 *            入参|需要合并的List|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param tarList
	 *            出参|合并后的List|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "srcList", comment = "需要合并的List", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = { @Param(name = "tarList", comment = "合并后的List", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "List合并", style = "判断型", type = "同步组件", comment = "将多个List合并为一个List，入参中的每一个元素都应该是List", date = "2016-08-02 04:11:35")
	public static TCResult P_mergeList(JavaList srcList) {
		if (srcList == null || !(srcList instanceof JavaList)) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参要合并的变量名称参数非法，为Null或不为JavaList");
		}
		int srcListLen = srcList.size();
		JavaList tarList = new JavaList();
		for (int i = 0; i < srcListLen; i++) {
			if (!(srcList.getItem(i) instanceof JavaList)) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参要合并的元素不为JavaList");
			}
			for (Object listitem : (JavaList) srcList.getItem(i)) {
				tarList.add(listitem);
			}
		}
		return TCResult.newSuccessResult(tarList);
	}
}
