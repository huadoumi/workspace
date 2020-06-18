package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * 字典处理类组件
 * 
 * @date 2015-07-01 17:12:36
 */
@ComponentGroup(level = "平台", groupName = "字典处理类组件")
public class P_Dict {

	/**
	 * @param __REQ__
	 *            入参|请求容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param paramList
	 *            入参|删除容器指定变量的键列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "__REQ__", comment = "请求容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "paramList", comment = "删除容器中变量的键列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "容器变量删除", style = "判断型", type = "同步组件", comment = "删除容器中指定的变量,例如当入参__REQ__={\"key1\":\"value1\", \"key2\":\"value2\"},入参paramList=[\"key1\"]时,处理后会删除__REQ__中键为key1的键值对,即__REQ__={\"key2\":\"value2\"}", date = "Wed Jul 01 17:15:17 CST 2015")
	public static TCResult delete(JavaDict __REQ__, JavaList paramList) {
		if (__REQ__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参请求容器不能为空");
		}
		if (paramList == null) {
			return TCResult
					.newFailureResult(ErrorCode.AGR, "入参删除容器中变量的键列表不能为空");
		}
		for (int i = 0; i < paramList.size(); i++) {
			if (__REQ__.hasKey(paramList.getItem(i))) {
				__REQ__.removeItem(paramList.getItem(i));
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param __REQ__
	 *            入参|请求容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param paramList
	 *            入参|删除容器指定变量的键列表,如果为null则删除所有的|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "__REQ__", comment = "请求容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "paramList", comment = "删除容器中变量的键列表,如果为null则删除容器中所有的", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "删除容器中空字段", style = "判断型", type = "同步组件", comment = "删除容器中空字段,空的标准为空字符串或null,例如__REQ__={\"key1\":null, \"key2\":\"\", \"key3\":null, \"key4\":\"\", \"key5\":\"value5\"}, 当paramList=[\"key1\", \"key2\", \"key5\"]时，其处理后则__REQ__={\"key3\":null, \"key4\":\"\", \"key5\":\"value5\"};当paramList=null时,其处理后则__REQ__={\"key5\":\"value5\"}", date = "Wed Jul 01 17:27:45 CST 2015")
	public static TCResult delNull(JavaDict __REQ__, JavaList paramList) {
		if (__REQ__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参字典容器参数不能为空");
		}
		if (paramList != null) {
			for (int i = 0; i < paramList.size(); i++) {
				if (__REQ__.hasKey(paramList.getItem(i))) {
					Object obj = __REQ__.getItem(paramList.getItem(i));
					if (obj == null || String.valueOf(obj).equals("")) {
						__REQ__.removeItem(paramList.getItem(i));
					}
				}
			}
		} else {
			Set<Object> keys = __REQ__.keySet();
			if (keys != null) {
				Iterator<Object> it = keys.iterator();
				while (it.hasNext()) {
					Object key = it.next();
					Object value = __REQ__.getItem(key);
					if (value == null || String.valueOf(value).equals("")) {
						it.remove();
					}
				}
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param __SRC__
	 *            入参|源容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param __DCT__
	 *            入参|目的容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param strOrList
	 *            入参|[[\
	 *            "源key名称\",\"目的key名称\"],...]或字符串,如果strOrList为String类型，而不是列表时 则
	 *            进行匹配拷贝|{@link java.lang.Object}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "__SRC__", comment = "源容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "__DCT__", comment = "目的容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "strOrList", comment = "[[\"源key名称\",\"目的key名称\"],...]或字符串;如果strOrList为String类型时,则拷贝strOrList中包含__SRC__中键的部分键值对到__DCT__", type = java.lang.Object.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "容器变量拷贝", style = "判断型", type = "同步组件", comment = "拷贝映射源容器字典中的变量到目的容器中,例如__SRC__={\"key_a1\":\"value1\", \"key_a2\":\"value2\"}, __DCT__={}, 当strOrList=[[\"key_a1\", \"key_b1\"]]时, 则处理后__DCT__={\"key_b1\":\"value1\"}; 当strOrList=\"abkey_a1key_a2\"时，则处理后__DCT__={\"key_a1\":\"value1\", \"key_a2\":\"value2\"}", date = "Wed Jul 01 17:53:45 CST 2015")
	public static TCResult varCopy(JavaDict __SRC__, JavaDict __DCT__,
			Object strOrList) {
		if (__SRC__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参源容器不能为空");
		}
		if (__DCT__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参目的容器不能为空");
		}

		if (strOrList instanceof JavaList) {
			// 入参paramList类型是JavaList的处理
			JavaList params = (JavaList) strOrList;
			for (int i = 0; i < params.size(); i++) {
				Object param = params.getItem(i);
				if (!(param instanceof JavaList)) {
					return TCResult.newFailureResult(ErrorCode.AGR,
							"入参paramList列表的子元素必须为列表类型");
				}
				JavaList param0 = (JavaList) param;
				if (param0.size() < 2) {
					return TCResult.newFailureResult(ErrorCode.AGR,
							"入参paramList列表的第一个子元素列表的长度不能小于2");
				}
				if (__SRC__.hasKey(param0.getItem(0))) {
					__DCT__.setItem(param0.getItem(1),
							__SRC__.getItem(param0.getItem(0)));
				} else {
					return TCResult.newFailureResult(
							ErrorCode.AGR,
							"源容器" + String.valueOf(__SRC__) + "中参数["
									+ String.valueOf(param0) + "]不存在");
				}
			}
		} else if (strOrList instanceof String) {
			// 入参paramLists类型是String的处理
			String paramstr = (String) strOrList;
			Iterator<Object> it = __SRC__.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				if (paramstr.indexOf(String.valueOf(key), 0) != -1) {
					__DCT__.setItem(key, __SRC__.getItem(key));
				}
			}
		} else {
			return TCResult.newFailureResult(ErrorCode.DICTCTL,
					"容器变量拷贝失败：入参paramList的类型必须为字符串或列表类型");
		}
		AppLogger.trace("容器变量拷贝处理成功");
		return TCResult.newSuccessResult();
	}

	/**
	 * @param inContext
	 *            入参|请求容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param paramList
	 *            入参|检查容器中变量的列表,如：[[\"key1\",类型(,长度)],[\"key2\",类型(,长度)]...]|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 判断变量正确<br/>
	 *         2 判断变量错误<br/>
	 */
	@InParams(param = {
			@Param(name = "inContext", comment = "请求容器, 如{\"key1\":value1, \"key2\":value2, ...}", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "paramList", comment = "检查容器中变量的列表,如：[[\"key1\",类型(,长度)],[\"key2\",类型(,长度)]...]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "判断变量正确"),
			@Return(id = "2", desp = "判断变量错误") })
	@Component(label = "容器变量检查", style = "选择型", type = "同步组件", comment = "检查入参字典inContext中key对应value的类型和长度, 需要检查的键值对在入参列表paramList中指明", date = "Thu Jul 02 10:00:27 CST 2015")
	public static TCResult keyCheck(JavaDict inContext, JavaList paramList) {
		if (inContext == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "请求容器不能为空");
		}
		if (paramList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参检查容器中变量的列表不能为空");
		}
		for (int i = 0; i < paramList.size(); i++) {
			Object param = paramList.getItem(i);
			if (!(param instanceof JavaList)) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参检查容器中变量的列表的子元素必须为列表类型");
			}
			JavaList param0 = (JavaList) param;
			if (param0.size() < 1) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参检查容器中变量的列表的子元素列表的长度不能小于1");
			}
			if (!inContext.hasKey(param0.getItem(0))) {
				return TCResult.newFailureResult(
						ErrorCode.AGR,
						"请求容器" + inContext + "中参数["
								+ String.valueOf(param0.getItem(0)) + "]不存在");
			}
			if (param0.size() > 1) {
				if (param0.getItem(1) != null) {
					if (!(inContext.getItem(param0.getItem(0)).getClass()
							.equals(param0.getItem(1)))) {
						return new TCResult(2, ErrorCode.AGR, "入参中的值["
								+ String.valueOf(param0.getItem(0) + "]数据类型错误"));
					}
					if (param0.size() > 2) {
						if ((param0.getItem(1).equals(java.lang.String.class))
								&& (param0.getItem(2) != null)) {
							if (String.valueOf(
									inContext.getItem(param0.getItem(0)))
									.length() > Integer.parseInt(String
									.valueOf(param0.getItem(2)))) {
								return new TCResult(2, ErrorCode.AGR, "入参中的值["
										+ String.valueOf(param0.getItem(0)
												+ "]长度错误"));
							}
						}
					}
				}
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param inContext
	 *            入参|请求容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param paramList
	 *            入参|转换变量列表,如：[["源容器key名称",int/float/String类型,value(可没有)],...],
	 *            当paramList子元素列表中value不为null或不存在或为空字符串时,
	 *            则inContext中该key键对应的值修改为该key所在paramList子元素列表对应的value值|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "inContext", comment = "请求容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "paramList", comment = "转换变量列表,如：[[\"源容器key名称\",int/float/String类型,value(可没有)],...], 当paramList子元素列表中value不为null或不存在或为空字符串时, 则inContext中该key键对应的值修改为该key所在paramList子元素列表对应的value值", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "容器变量转换", style = "判断型", type = "同步组件", comment = "inContext中key对应的value变量转换为指定的类型和值value", date = "Thu Jul 02 10:39:46 CST 2015")
	public static TCResult keyCov(JavaDict inContext, JavaList paramList) {
		if (inContext == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "请求容器不能为空");
		}
		if (paramList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参转换变量列表不能为空");
		}
		for (int i = 0; i < paramList.size(); i++) {
			Object param = paramList.getItem(i);
			if (!(param instanceof JavaList)) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参转换变量列表的子元素必须是列表类型");
			}
			JavaList param0 = (JavaList) param;
			int paramListLen = param0.size();
			if (paramListLen < 2) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参转换变量列表的子元素的长度不能小于2");
			}
			Object tmpValue = null;
			if (paramListLen == 2 || param0.getItem(2) == null
					|| String.valueOf(param0.getItem(2)).length() == 0) {
				tmpValue = inContext.getItem(param0.getItem(0));
			} else {
				tmpValue = param0.getItem(2);
			}

			inContext.setItem(param0.getItem(0),
					typeCast(tmpValue, (Class<?>) param0.getItem(1)));
		}
		return TCResult.newSuccessResult();
	}

	private static Object typeCast(Object obj, Class<?> clazz) {
		// 类型相同，不需转换
		if (obj.getClass().equals(clazz)) {
			return obj;
		}
		if (clazz.equals(String.class)) {
			return String.valueOf(obj);
		} else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
			return Integer.parseInt(String.valueOf(obj));
		} else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
			return Float.parseFloat(String.valueOf(obj));
		}
		return obj;
	}

	/**
	 * @param inContext
	 *            入参|请求容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param paramList
	 *            入参|变量赋值列表，如：[[key1,value1],[key2,value2],[key3,value3]...]|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "inContext", comment = "请求容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "paramList", comment = "变量赋值列表，如：[[\"key1\",value1],[\"key2\",value2],[\"key3\",value3]...]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "容器变量赋值", style = "判断型", type = "同步组件", comment = "容器变量赋值, 例如入参inContext={\"key1\":value1}, 当入参paramList=[[\"key1\",value1_1],[\"key2\",value2]]时, 则处理后inContext={\"key1\":value1_1, \"key2\":value2}; 当入参inContext={\"key1\":value1}, paramList=[\"key2\", value2]时, 则处理后inContext={\"key1\":\"value1\", \"key2\", value2}", date = "Thu Jul 02 11:48:56 CST 2015")
	public static TCResult setValue(JavaDict inContext, JavaList paramList) {
		if (paramList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参变量赋值列表不能为空");
		}
		if (inContext == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参请求容器不能为空");
		}
		for (int i = 0; i < paramList.size(); i++) {
			Object param = paramList.getItem(i);
			if (!(param instanceof JavaList)) {
				if (paramList.size() < 2) {
					return TCResult.newFailureResult(ErrorCode.AGR,
							"当入参paramList列表不是嵌套列表时, 入参列表paramList的长度不能小于2");
				}
				inContext.setItem(paramList.getItem(0), paramList.getItem(1));
				break;
			} else if (((JavaList) param).size() < 2) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参变量赋值列表的子元素的长度不能小于2");
			}
			JavaList param0 = (JavaList) param;
			inContext.setItem(param0.getItem(0), param0.getItem(1));
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param inContext
	 *            入参|源容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param outContext
	 *            入参|目的容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param loopVar
	 *            入参|源容器指定的列|int
	 * @param listData
	 *            入参|源容器到目的容器的映射 列表[[\"源key名称\", \"目的key名称\"],...]|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "inContext", comment = "源容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "outContext", comment = "目的容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "loopVar", comment = "源容器指定的列", type = int.class),
			@Param(name = "listData", comment = "源容器到目的容器的映射列表 [[\"源key名称\", \"目的key名称\"],...]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "取循环列表值", style = "判断型", type = "同步组件", comment = "取循环列表值", date = "Thu Jul 02 12:01:06 CST 2015")
	public static TCResult getLoopList(JavaDict inContext, JavaDict outContext,
			int loopVar, JavaList listData) {
		if (inContext == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参inContext不能为空");
		}
		if (outContext == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参outContext不能为空");
		}
		if (listData == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参源容器到目的容器的映射列表listData不能为空");
		}
		for (int i = 0; i < listData.size(); i++) {
			Object data = listData.getItem(i);
			if (!(data instanceof JavaList)) {
				if (!(inContext.getItem(data) instanceof JavaList)) {
					if (loopVar == 0) {
						outContext.setItem(listData.getItem(1),
								inContext.getItem(listData.getItem(0)));
						break;
					} else {
						return TCResult.newFailureResult(ErrorCode.AGR,
								"源容器中键:" + String.valueOf(data)
										+ "对应的值也不为列表类型时，源容器指定的列必须为0");
					}
				} else {
					Object obj = inContext.getItem(listData.getItem(0));
					if (!(obj instanceof JavaList)) {
						return TCResult.newFailureResult(ErrorCode.AGR,
								"源容器中键所对应的值必须为列表类型");
					}
					outContext.setItem(listData.getItem(1),
							((JavaList) obj).getItem(loopVar));
					break;
				}
			} else {
				if (!(data instanceof JavaList)) {
					return TCResult.newFailureResult(ErrorCode.AGR,
							"源容器到目的容器的映射列表中的子项必须为列表类型");
				}
				if (!(inContext.getItem(((JavaList) data).getItem(0)) instanceof JavaList)) {
					if (loopVar == 0) {
						outContext
								.setItem(((JavaList) data).getItem(1),
										inContext.getItem(((JavaList) data)
												.getItem(0)));
					} else {
						return TCResult.newFailureResult(ErrorCode.AGR,
								"源容器中键:" + String.valueOf(data)
										+ "对应的值也不为列表类型时，源容器指定的列必须为0");
					}
				} else {
					if (!(inContext.get(((JavaList) data).getItem(0)) instanceof JavaList)) {
						return TCResult.newFailureResult(ErrorCode.AGR,
								"源容器中键所对应的值必须为列表类型");
					}
					outContext.setItem(((JavaList) data).getItem(1),
							((JavaList) inContext.get(((JavaList) data)
									.getItem(0))).getItem(loopVar));
				}
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param keyList
	 *            入参|容器的键列表, 以该列表中各值作为入参context字典的键值对的键, 如[\"key1\", \"key2\",
	 *            ...]| {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param records
	 *            入参|容器的数据, 为嵌套列表(即子元素的类型也是JavaList, 且子元素列表的长度需与入参keyList的长度相同)|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param context
	 *            入参|容器|{@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "keyList", comment = "容器的键列表, 以该列表中各值作为入参context字典的键值对的键, 如[\"key1\", \"key2\", ...]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "records", comment = "容器的数据, 为嵌套列表(即子元素的类型也是JavaList, 且子元素列表的长度需与入参keyList的长度相同)", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "context", comment = "容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "多笔记录列赋值", style = "判断型", type = "同步组件", comment = "多笔记录列赋值, 以keyList中的子元素作为键、以records中对应索引的子元素(若子元素有null,则以空字符串代替)作为值赋值给context", date = "Thu Jul 02 14:44:36 CST 2015")
	public static TCResult setColumeValue(JavaList keyList, JavaList records,
			JavaDict context) {
		if (keyList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参容器keyList不能为空");
		}
		if (records == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参容器records不能为空");
		}
		if (context == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参容器context不能为空");
		}
		int keyLen = keyList.size();
		for (int i = 0; i < keyLen; i++) {
			context.setItem(keyList.getItem(i), new JavaList());
		}
		Iterator<Object> it = records.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (!(obj instanceof JavaList)) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参容器的数据的子项必须为列表类型");
			}
			JavaList record = (JavaList) obj;
			if (keyLen != record.size()) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"容器的键列表长度和容器的数据子列表的长度不一致");
			}
			String value = "";
			for (int i = 0; i < keyLen; i++) {
				if (record.getItem(i) == null) {
					value = "";
				} else {
					value = record.getItem(i);
				}
				if (!(context.getItem(keyList.getItem(i)) instanceof JavaList)) {
					return TCResult.newFailureResult(ErrorCode.AGR, "容器中键为"
							+ keyList.getItem(i) + "对应的值必须为列表类型");
				}
				((JavaList) context.getItem(keyList.getItem(i))).add(value);
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param __SRC__
	 *            入参|源容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param __DCT__
	 *            入参|目的容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param strOrList
	 *            入参|[["源key名称", "目的key名称"],...]货字符串,如果strOrList为String类型 则
	 *            进行匹配拷贝,如果为空则根据深度全量拷贝|{@link java.lang.Object}
	 * @param typeList
	 *            入参|拷贝类型[null是基础类型(非JavaList/JavaDict),list是JavaList/
	 *            JavaDict类型的列表, 如果typelist为空JavaList则全量拷贝]|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "__SRC__", comment = "源容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "__DCT__", comment = "目的容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "strOrList", comment = "[[\"源key名称\", \"目的key名称\"],...]或字符串,如果strOrList为String类型,则进行匹配拷贝,如果为空则根据深度全量拷贝", type = java.lang.Object.class),
			@Param(name = "typeList", comment = "拷贝类型[null是基础类型(非JavaList/JavaDict),list是JavaList/JavaDict类型的列表,如果typelist为空JavaList则全量拷贝]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "容器间变量参数化拷贝", style = "判断型", type = "同步组件", comment = "拷贝映射字典中的变量到另外一个容器中,可全量拷贝,如果原容器没有的,则跳过不拷贝继续处理下面字典", date = "Thu Jul 02 15:15:26 CST 2015")
	public static TCResult copy(JavaDict __SRC__, JavaDict __DCT__,
			Object strOrList, JavaList typeList) {
		if (__SRC__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参源容器不能为空");
		}
		if (__DCT__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参目的容器不能为空");
		}
		if (strOrList == null) {
			// 入参ParamLists类型是null的处理
			if (typeList == null) {
				Iterator<Object> it = __SRC__.keySet().iterator();
				while (it.hasNext()) {
					Object param = it.next();
					Object srcObj = __SRC__.getItem(param);
					if (!(srcObj instanceof JavaList)
							&& !(srcObj instanceof JavaDict)) {
						__DCT__.setItem(param, srcObj);
					}
				}
			} else if (typeList.size() == 0) {
				__DCT__.putAll(__SRC__);
			}
		} else if (strOrList instanceof JavaList) {
			// 入参ParamLists类型是List的处理
			JavaList paramList0 = (JavaList) strOrList;
			for (int i = 0; i < paramList0.size(); i++) {
				Object param = paramList0.getItem(i);
				if (!(param instanceof JavaList)) {
					return TCResult.newFailureResult(ErrorCode.AGR,
							"入参paramList为列表类型时,其子元素也必须为列表类型");
				}
				JavaList param0 = (JavaList) param;
				if (param0.size() < 2) {
					return TCResult.newFailureResult(ErrorCode.AGR,
							"参paramList为列表类型时,其子元素列表长度不能小于2");
				}
				if (!__SRC__.hasKey(param0.getItem(0))) {
					continue;
				}
				__DCT__.setItem(param0.getItem(1),
						__SRC__.getItem(param0.getItem(0)));
			}
		} else if (strOrList instanceof String) {
			// 入参ParamLists类型是String的处理
			String param = (String) strOrList;
			Iterator<Object> it = __SRC__.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (param.indexOf(key, 0) != -1) {
					__DCT__.setItem(key, __SRC__.getItem(key));
				}
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param context
	 *            入参|字典容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param listInfo
	 *            入参|转换信息列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "context", comment = "字典容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "listInfo", comment = "转换信息列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "字典列表转换", style = "判断型", type = "同步组件", comment = "根据输入的描述关系转换列表值,转换信息列表格式为[[字典项,[源列表值list], [目标列表值list]], ...]", date = "Thu Jul 02 15:57:08 CST 2015")
	public static TCResult covList(JavaDict context, JavaList listInfo) {
		AppLogger.trace("入参[转换信息list]:" + listInfo == null ? null : String
				.valueOf(listInfo));

		if (context == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参字典容器不能为空");
		}
		if (listInfo == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参转换信息列表不能为空");
		}
		for (int i = 0; i < listInfo.size(); i++) {
			if (!(listInfo.getItem(i) instanceof JavaList)) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参转换信息列表的子元素必须为列表类型");
			}
			JavaList colInfo = (JavaList) listInfo.getItem(i);
			if (!context.hasKey(colInfo.getItem(0))) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"字典容器不包含转换信息列表中指定的" + colInfo.getItem(0) + "字段");
			}
			if (!(colInfo.getItem(1) instanceof JavaList)) {
				return TCResult.newFailureResult(ErrorCode.AGR, "转换信息列表第"
						+ (i + 1) + "项的第1个元素必须为列表类型");
			}
			if (!((JavaList) colInfo.getItem(1)).contains(context
					.getItem(colInfo.getItem(0)))) {
				return TCResult.newFailureResult(
						ErrorCode.AGR,
						"转换信息列表中不含转换的字段" + colInfo.getItem(0) + ",源值"
								+ context.getItem(colInfo.getItem(0))
								+ "非法,非转换信息列表内值");
			}
			if (!(colInfo.getItem(2) instanceof JavaList)) {
				return TCResult.newFailureResult(ErrorCode.AGR, "转换信息列表第"
						+ (i + 1) + "项的第2个元素必须为列表类型");
			}
			int tmp = ((JavaList) colInfo.getItem(1)).indexOf(context
					.getItem(colInfo.getItem(0)));
			context.setItem(colInfo.getItem(0),
					((JavaList) colInfo.getItem(2)).getItem(tmp));
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param context
	 *            入参|字典容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param listInfo
	 *            入参|转换信息列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "context", comment = "字典容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "listInfo", comment = "转换信息列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "批量字典列表转换", style = "判断型", type = "同步组件", comment = "根据输入的描述关系转换列表值,转换信息列表格式为[[字典项list,[源列表值list], [目标列表值list]], ...]", date = "Thu Jul 02 16:26:45 CST 2015")
	public static TCResult multiCovList(JavaDict context, JavaList listInfo) {
		AppLogger.trace("入参[字典容器]:" + context == null ? null : String
				.valueOf(context));
		AppLogger.trace("入参[转换信息列表]:" + listInfo == null ? null : String
				.valueOf(listInfo));

		if (context == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "字典容器不能为空");
		}
		if (listInfo == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "转换信息列表不能为空");
		}
		if (!(listInfo.getItem(0) instanceof JavaList)) {
			return TCResult
					.newFailureResult(ErrorCode.AGR, "转换信息列表的子元素必须为列表类型");
		}
		JavaList listInfo_0 = listInfo.getItem(0);
		Object obj = context.getItem(listInfo_0.getItem(0));
		if (obj instanceof String) {
			context.setItem(listInfo_0.getItem(0), new JavaList(obj));
		}
		if (!(context.getItem(listInfo_0.getItem(0)) instanceof JavaList)) {
			return TCResult.newFailureResult(ErrorCode.AGR, "字典容器中键为"
					+ listInfo_0.getItem(0) + "对应的值必须为列表类型");
		}
		int tmpLen = ((JavaList) context.getItem(listInfo_0.getItem(0))).size();
		for (int i = 0; i < tmpLen; i++) {
			for (int j = 0; j < listInfo.size(); j++) {
				JavaList colInfo = listInfo.getItem(j);
				if (!(context.hasKey(colInfo.getItem(0)))) {
					AppLogger.trace(String.valueOf(new JavaList(0,
							ErrorCode.AGR, "入参字典容器不包含转换信息列表中的转换字段:"
									+ colInfo.getItem(0))));
				}
				if (!(colInfo.getItem(1) instanceof JavaList)) {
					return TCResult.newFailureResult(ErrorCode.AGR, "转换信息列表第"
							+ (j + 1) + "个子项下的第1个元素必须为列表类型");
				}
				if (!(colInfo.getItem(2) instanceof JavaList)) {
					return TCResult.newFailureResult(ErrorCode.AGR, "转换信息列表第"
							+ (j + 1) + "个子项下的第2个元素必须为列表类型");
				}
				if (!(context.getItem(colInfo.getItem(0)) instanceof JavaList)) {
					return TCResult.newFailureResult(ErrorCode.AGR, "字典容器中键为"
							+ colInfo.getItem(0) + "对应的值必须为列表类型");
				}
				int tmp = ((JavaList) colInfo.getItem(1))
						.indexOf(((JavaList) context.getItem(colInfo.getItem(0)))
								.getItem(i));

				((JavaList) context.getItem(colInfo.getItem(0))).set(i,
						((JavaList) colInfo.getItem(2)).getItem(tmp));
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param __SRC__
	 *            入参|数据源容器, 如{\"keys\":[key1, key2, ...], \"values\":[[value0_0,
	 *            value0_1, ...], ...]}, 注意必须以keys和values字符串作为键,
	 *            且两个键对应的值都为JavaList列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param __TAR__
	 *            入参|数据目标容器|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param srcKeyList
	 *            入参|源变量列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param line
	 *            入参|行号, 必须大于等于数据源容器__SRC__中的\"values\"字符串为键对应的列表的长度|int
	 * @param tarKeyList
	 *            入参|目标变量列表(如果为null，则和源列表srcKeyList相同)|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "__SRC__", comment = "数据源容器, 如{\"keys\":[\"key1\", \"key2\", ...], \"values\":[[value0_0, value0_1, ...], ...]}, 注意必须以keys和values字符串作为键, 且两个键对应的值都为JavaList列表", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "__TAR__", comment = "数据目标容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "srcKeyList", comment = "源变量列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "line", comment = "行号, 必须大于等于数据源容器__SRC__中的\"values\"字符串为键对应的列表的长度", type = int.class),
			@Param(name = "tarKeyList", comment = "目标变量列表(如果为null，则和源列表srcKeyList相同)", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "容器变量多列值获取", style = "判断型", type = "同步组件", comment = "根据容器中的key在相应的值列表中获取相应的值, 即把tarKeyList中子元素与srcKeyList中子元素匹配的元素作为key, 并根据该key的索引, 把出__SRC__的第line个子元素列表中该索引的值作为value, 赋值给__TAR__容器", date = "Thu Jul 02 17:55:18 CST 2015")
	public static TCResult getKeyList(JavaDict __SRC__, JavaDict __TAR__,
			JavaList srcKeyList, int line, JavaList tarKeyList) {
		if (__SRC__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "源容器不能为空");
		}
		if (__TAR__ == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "数据目标容器不能为空");
		}
		if (srcKeyList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "源变量列表不能为空");
		}
		// 如果目标列表是null，则取源列表
		if (tarKeyList == null) {
			tarKeyList = srcKeyList;
		} else {
			if (tarKeyList.size() != srcKeyList.size()) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"源变量列表和目标变量列表的长度不一致");
			}
		}
		if (!(__SRC__.getItem("keys") instanceof JavaList)) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"源容器中键为keys的值必须为列表类型");
		}
		if (!(__SRC__.getItem("values") instanceof JavaList)) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"源容器中键为values的值必须为列表类型");
		}
		// 获取key列表
		JavaList keyList = (JavaList) __SRC__.getItem("keys");
		// 获取values的列表
		JavaList valueList = (JavaList) __SRC__.getItem("values");

		// 行数校验
		if (valueList.size() < line) {
			return TCResult.newFailureResult(ErrorCode.AGR, "值列表行数小于" + line);
		}
		// 处理列表
		for (int i = 0; i < srcKeyList.size(); i++) {
			Object key = srcKeyList.getItem(i);
			// 容器变量检查
			if (!keyList.contains(key)) {
				return TCResult.newFailureResult(ErrorCode.AGR, "数据源容器中不存在变量["
						+ key + "]");
			}
			// 获取key的下标
			int keyIndex = keyList.indexOf(key);
			if (!(valueList.getItem(line - 1) instanceof JavaList)) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"源容器中键为values对应列表值的第" + line + "个元素必须为列表类型");
			}
			// 获取对应的值
			Object values = ((JavaList) valueList.getItem(line - 1))
					.getItem(keyIndex);
			// 获取对应的目标key
			int tarKeyIndex = tarKeyList.indexOf(key);
			Object tarKey = tarKeyList.getItem(tarKeyIndex);
			// 给目标key
			__TAR__.setItem(tarKey, values);
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param keyList
	 *            入参|容器变量比较的键列表 如：[\"key1\",\"key2\",\"key3\",...]|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param dict1
	 *            入参|比较容器,如：{\"key1\":value1_1, \"key2\":value1_2....}|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param dict2
	 *            入参|被比较容器,如：{\"key1\":value2_1,\"key2\":value2_2....}|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 判断变量错误<br/>
	 *         1 判断变量正确<br/>
	 */
	@InParams(param = {
			@Param(name = "keyList", comment = "容器变量比较的键列表, 如：[\"key1\",\"key2\",\"key3\"...]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "dict1", comment = "比较容器,如：{\"key1\":value1_1, \"key2\":value1_2....}", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "dict2", comment = "被比较容器,如：{\"key1\":value2_1,\"key2\":value2_2....}", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "判断变量错误"),
			@Return(id = "1", desp = "判断变量正确") })
	@Component(label = "容器值比较", style = "判断型", type = "同步组件", comment = "根据列表keyList中的key值比较字典dict1与dict2中对应keyList的值是否相等", date = "Fri Jul 03 09:29:26 CST 2015")
	public static TCResult validKeyValue(JavaList keyList, JavaDict dict1,
			JavaDict dict2) {
		if (dict1 == null || dict2 == null) {
			return TCResult
					.newFailureResult(ErrorCode.AGR, "入参比较容器和被比较容器都不能为空");
		}
		if (keyList == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参容器变量比较的键列表不能为空");
		}
		for (int i = 0; i < keyList.size(); i++) {
			Object key = keyList.getItem(i);
			if ((key instanceof JavaList) || (key instanceof JavaDict)) {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"入参容器变量比较的键列表的子元素不能为列表或字典类型");
			}
			if (dict1.hasKey(key) && dict2.hasKey(key)) {
				Object obj1 = dict1.getItem(key);
				Object obj2 = dict2.getItem(key);
				if ((obj1 != null && obj2 == null)
						|| (obj1 == null && obj2 != null)) {
					return logForValidKeyValue(obj1, obj2, key);
				}
				if (obj1 != null && obj2 != null) {
					if (obj1.getClass() != (obj2.getClass())) {
						return logForValidKeyValue(obj1, obj2, key);
					}
					if (String.valueOf(obj1) != String.valueOf(obj2)) {
						return logForValidKeyValue(obj1, obj2, key);
					}
				}
			} else {
				if (!dict1.hasKey(key)) {
					return TCResult.newFailureResult(ErrorCode.AGR, "dict1键："
							+ key + "不一致");
				}
				if (!dict2.hasKey(key)) {
					return TCResult.newFailureResult(ErrorCode.AGR, "dict2键："
							+ key + "不一致");
				}
			}
		}
		return TCResult.newSuccessResult();
	}

	private static TCResult logForValidKeyValue(Object obj1, Object obj2,
			Object key) {
		AppLogger.trace(String.valueOf(obj1) + ":" + String.valueOf(obj2));
		return TCResult.newFailureResult(ErrorCode.AGR,
				"键：" + String.valueOf(key) + "值不一致");
	}

	/**
	 * @param srcDict
	 *            入参|源数据容器(单笔)|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param cycleTime
	 *            入参|当前循环次数|int
	 * @param dctDict
	 *            入参|目的数据容器(合集)|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 判断变量错误<br/>
	 *         1 判断变量正确<br/>
	 */
	@InParams(param = {
			@Param(name = "srcDict", comment = "源数据容器(单笔)", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "cycleTime", comment = "当前循环次数, 当源数据容器srcDict中有key时，而目的数据容器dctDict中的没有key值时，则在目的数据容器新增以key为键的键值对，且该key对应的value值为包含cycleTime个空字符串的JavaList对象（当cycleTime=0时先将入参dctDict目的容器清空再新增键值对）", type = int.class),
			@Param(name = "dctDict", comment = "目的数据容器(合集), 如{\"key0\":[value0_0, value0_1, ...], ...}", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "判断变量错误"),
			@Return(id = "1", desp = "判断变量正确") })
	@Component(label = "字典循环合并", style = "判断型", type = "同步组件", comment = "将源数据容器中的所有key值循环追加目的容器中，用于输出指定长度指定顺序的所有源容器的合集，原容器中所有的key值若在目的容器中存在则拼接到目的容器对应的key值中，若不存在对应的key赋值[\"\"]*i并append源容器的key值，若目的容器有源容器中没有的key则自动追加\"\"，循环变量从0开始，目的容器会被初始化为空", date = "Fri Jul 03 09:45:40 CST 2015")
	public static TCResult multDictUnite(JavaDict srcDict, int cycleTime,
			JavaDict dctDict) {
		if (srcDict == null || dctDict == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"入参源数据容器和目的数据容器都不能为空");
		}
		if (cycleTime == 0) {
			dctDict.clear();
		}
		Iterator<Object> it1 = srcDict.keySet().iterator();
		while (it1.hasNext()) {
			Object key = it1.next();
			if (dctDict.hasKey(key)) {
				Object obj = dctDict.getItem(key);
				if (!(obj instanceof JavaList)) {
					return TCResult.newFailureResult(ErrorCode.AGR, "目的数据容器中键为"
							+ key + "对应的值必须为列表类型");
				}
				((JavaList) obj).add(srcDict.getItem(key));
			} else {
				JavaList value = new JavaList();
				for (int tmp = 0; tmp < cycleTime; tmp++) {
					value.add("");
				}
				dctDict.setItem(key, value);
			}
		}
		Iterator<Object> it2 = dctDict.keySet().iterator();
		while (it2.hasNext()) {
			Object key = it2.next();
			if (!srcDict.hasKey(key)) {
				if (!(dctDict.getItem(key) instanceof JavaList)) {
					return TCResult.newFailureResult(ErrorCode.AGR,
							"目的数据容器的子项必须为列表类型");
				}
				((JavaList) dctDict.getItem(key)).add("");
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param dict
	 *            入参|字典|{@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param key
	 *            入参|要检测的key值|{@link Object}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dict", comment = "字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "key", comment = "要检测的key值", type = Object.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "检查key存在", style = "判断型", type = "同步组件", comment = "检测字典中的key是否存在", date = "2016-01-07 10:22:48")
	public static TCResult checkKeyExist(JavaDict dict, Object key) {
		if (dict == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, new String(
					"字典参数不能为null"));
		}
		if(key instanceof List) {
			List keyList = (List) key; 
			for(Object keyObj : keyList) {
				if (dict.hasKey(keyObj)) {
					return TCResult.newSuccessResult();
				} else {
					return TCResult.newFailureResult(ErrorCode.AGR, "请求容器" + dict
							+ "中参数[" + String.valueOf(keyObj) + "]不存在");
				}
			}
		}else {
			if (dict.hasKey(key)) {
				return TCResult.newSuccessResult();
			} else {
				return TCResult.newFailureResult(ErrorCode.AGR, "请求容器" + dict
						+ "中参数[" + String.valueOf(key) + "]不存在");
			}
		}
		return TCResult.newSuccessResult();

	}

}