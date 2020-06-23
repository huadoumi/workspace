package tc.platform;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.IResourceManager;
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
 * demo
 * 
 * @date 2020-05-14 20:20:23
 */
@ComponentGroup(level = "平台", groupName = "cate")
public class P_demo {

	/**
	 * @category axaxa
	 * @since aaaa 出参|aaaaa|{@link java.lang.String}
	 * @return 0 失败<br>
	 *         1 成功<br>
	 */
	@InParams(param = { @Param(name = "qwer", comment = "qwer", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "aaaa", comment = "aaaaa", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "axaxa", style = "判断型", type = "异步组件", comment = "ttttt", author = "pang", date = "2020-06-04 03:19:24")
	public static TCResult P_axax(String qwer) {

		/**
		 * 这是一个测试
		 */
		System.out.println("hello world，pang");
		// 你好
		return TCResult.newSuccessResult();
	}

	public static <T> TCResult ormSelect(Class dboType, JavaDict whereDict, JavaList orderList, int count) {

		return ormSelect(dboType, whereDict, orderList, count, null);

	}

	/**
	 * 
	 * @category 数据库查询
	 * 
	 * @param dboType       入参|数据库对象|{@link Class}
	 * 
	 * @param whereDict     入参|where字典|{@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * 
	 * @param orderList     入参|排序列表|{@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * 
	 * @param count         入参|查询记录数|int
	 * 
	 * @param whereNullList 入参|where空字段列表|{@link java.util.List}
	 * 
	 * @since retCount 出参|返回记录数|int
	 * 
	 * @since retList 出参|返回结果列表|{@link Object}
	 * 
	 * @return 0 失败<br/>
	 * 
	 *         1 成功<br/>
	 * 
	 *         2 无记录<br/>
	 * 
	 */

	@InParams(param = { @Param(name = "dboType", comment = "数据库对象", type = Class.class),

			@Param(name = "whereDict", comment = "where字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),

			@Param(name = "orderList", comment = "排序列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),

			@Param(name = "count", comment = "查询记录数", defaultParam = "0", type = int.class),

			@Param(name = "whereNullList", comment = "where空字段列表", type = java.util.List.class) })

	@OutParams(param = { @Param(name = "retCount", comment = "返回记录数", type = int.class),

			@Param(name = "retList", comment = "返回结果列表", type = Object.class) })

	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功"),

			@Return(id = "2", desp = "无记录") })

	@Component(label = "数据库查询", style = "选择型", type = "同步组件", author = "tan", date = "2020-06-03 10:33:45")

	public static <T> TCResult ormSelect(Class dboType, JavaDict whereDict, JavaList orderList, int count,

			List<Object> whereNullList) {
		return null;

	}

	/**
	 * @category 测试回调组件
	 * @param dict 入参|dict|{@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dict", comment = "dict", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "测试循环回调组件", style = "回调型", type = "同步组件", comment = "callbackCpt", author = "pang", date = "2020-06-04 03:22:38")
	public static TCResult callbackCpt(JavaDict dict) {

		/**
		 * 这是一个测试
		 */
		Iterator<Entry<Object, Object>> iterator = dict.entrySet().iterator();

		// 你好，世界
		IResourceManager resMgr = new IResourceManager() {
			@Override
			public void close() throws IOException {
				AppLogger.info("靓仔靓女，请释放你的资源");
			}

			@Override
			public TCResult after() {
				return null;
			}

			@Override
			public JavaDict next(int resultCode) {
				if (iterator.hasNext()) {
					Entry<Object, Object> entry = iterator.next();
					JavaDict javadict = new JavaDict();
					javadict.put("key", entry.getKey());
					javadict.put("value", entry.getValue());
					return javadict;
				}
				return null;
			}
		};
		return TCResult.newSuccessResult(resMgr);
	}

	/**
	 * @category aa
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "aaaa", comment = "aaaa", type = java.lang.String.class),
			@Param(name = "bbbb", comment = "bbbbb", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "aaaa", comment = "aaaa", type = java.util.concurrent.Future.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "测试异步组件", style = "判断型", type = "异步组件", comment = "测试异步组件", author = "pang", date = "2020-06-05 11:21:40")
	public static TCResult asyncCpt(String aaaa, String bbbb) {
		return TCResult.newSuccessResult();
	}

}
