package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.jcomponent.MBSessionProvider;
import cn.com.agree.afa.mybatis.plugins.page.ISelect;
import cn.com.agree.afa.mybatis.plugins.page.Page;
import cn.com.agree.afa.mybatis.plugins.page.PageHelper;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * Mybatis操作类组件
 * 
 * @date 2016-12-29 11:54:8
 */
@ComponentGroup(level = "平台", groupName = "Mybatis操作类组件")
public class P_Mybatis {
	private static final int LAST_PAGE_MARK = 1;
	private static final int PREV_PAGE_MARK = 2;
	private static final int NEXT_PAGE_MARK = 3;
	private static final int CURRENT_PAGE_MARK = 4;

	/**
	 * @category 字典分页查询
	 * @param factory
	 *            入参|mybatis工厂|{@link java.lang.String}
	 * @param sqlName
	 *            入参|接口_id|{@link java.lang.String}
	 * @param dataMap
	 *            入参|数据字典|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param sortMap
	 *            入参|排序字典|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param pageList
	 *            入参|分页控制|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param result
	 *            出参|查询结果|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param resultCount
	 *            出参|结果笔数|int
	 * @param recordCount
	 *            出参|总记录数|int
	 * @param pageCount
	 *            出参|总页数|int
	 * @param currentPage
	 *            出参|当前页数|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 无满足条件记录<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "dataMap", comment = "数据字典,绑定到sql语句的查询条件,取决于具体的映射文件", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "sortMap", comment = "排序字典,绑定到sql语句的排序条件,取决于具体的映射文件", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "pageList", comment = "分页控制,页码从1开始,格式:[分页标识1-最后一页 2-上一页 3-下一页 4-当前页, 当前页码, 每页记录数],例如[4,1,10]表示分页大小为10,查询第1页", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = {
			@Param(name = "result", comment = "查询结果,javalist,每条记录代表一行,每行数据由javadict表示", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "resultCount", comment = "结果笔数", type = int.class),
			@Param(name = "recordCount", comment = "总记录数", type = int.class),
			@Param(name = "pageCount", comment = "总页数", type = int.class),
			@Param(name = "currentPage", comment = "当前页码", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "字典分页查询", style = "判断型", type = "同步组件", comment = "通过字典分页查询数据", date = "2016-12-30 03:00:30")
	public static TCResult P_selectByMap(String factory, String sqlName,
			JavaDict dataMap, JavaDict sortMap, JavaList pageList,
			boolean commitFlag) {
		try {
			checkConflict(dataMap, sortMap);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.AGR, e);
		}
		try {
			JavaDict params = new JavaDict();
			params.putAll(dataMap);
			params.putAll(sortMap);

			return _query(factory, sqlName, params, pageList, commitFlag);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.MYBATIS, e);
		}
	}

	/**
	 * @category pojo分页查询
	 * @param factory
	 *            入参|工厂名|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sql|{@link java.lang.String}
	 * @param pojo
	 *            入参|pojo|{@link Object}
	 * @param sortList
	 *            入参|排序|{@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param result
	 *            出参|查询结果|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param resultCount
	 *            出参|结果笔数|int
	 * @param recordCount
	 *            出参|总记录数|int
	 * @param pageCount
	 *            出参|总页数|int
	 * @param currentPage
	 *            出参|当前页数|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "pojo", comment = "pojo", type = Object.class),
			@Param(name = "pageList", comment = "分页控制,页码从1开始,格式:[分页标识1-最后一页 2-上一页 3-下一页 4-当前页, 当前页码, 每页记录数],例如[4,1,10]表示分页大小为10,查询第1页", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = {
			@Param(name = "result", comment = "查询结果,javalist,每条记录代表一行,每行数据由javadict表示", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "resultCount", comment = "结果笔数", type = int.class),
			@Param(name = "recordCount", comment = "总记录数", type = int.class),
			@Param(name = "pageCount", comment = "总页数", type = int.class),
			@Param(name = "currentPage", comment = "当前页码", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "pojo分页查询", style = "判断型", type = "同步组件", comment = "通过POJO对象进行分页查询", author = "Anonymous", date = "2017-01-03 01:57:07")
	public static TCResult P_selectByObject(String factory, String sqlName,
			Object pojo, JavaList pageList, boolean commitFlag) {
		try {

			return _query(factory, sqlName, pojo, pageList, commitFlag);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.MYBATIS, e);
		}
	}

	/**
	 * @category 类名分页查询
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param className
	 *            入参|className|{@link java.lang.String}
	 * @param data
	 *            入参|data|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param pageList
	 *            入参|pagelist|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param result
	 *            出参|查询结果|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param resultCount
	 *            出参|结果笔数|int
	 * @param recordCount
	 *            出参|总记录数|int
	 * @param pageCount
	 *            出参|总页数|int
	 * @param currentPage
	 *            出参|当前页数|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "className", comment = "简单名或全限定名", type = java.lang.String.class),
			@Param(name = "data", comment = "对象的字段，key为属性名，value为属性值", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "pageList", comment = "分页控制,页码从1开始,格式:[分页标识1-最后一页 2-上一页 3-下一页 4-当前页, 当前页码, 每页记录数],例如[4,1,10]表示分页大小为10,查询第1页", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = {
			@Param(name = "result", comment = "查询结果,javalist,每条记录代表一行,每行数据由javadict表示", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "resultCount", comment = "结果笔数", type = int.class),
			@Param(name = "recordCount", comment = "总记录数", type = int.class),
			@Param(name = "pageCount", comment = "总页数", type = int.class),
			@Param(name = "currentPage", comment = "当前页码", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "类名分页查询", style = "判断型", type = "同步组件", comment = "通过提供类名与相关数据查询数据", author = "Anonymous", date = "2017-01-03 02:09:00")
	public static TCResult P_selectByClassName(String factory, String sqlName,
			String className, JavaDict data, JavaList pageList,
			boolean commitFlag) {
		Object entityObject;
		try {
			entityObject = createEntityInstance(className, data);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.OBJCTL, e);
		}
		try {
			return _query(factory, sqlName, entityObject, pageList, commitFlag);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.MYBATIS, e);
		}

	}

	/**
	 * @category 字典条件插入
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param params
	 *            入参|params|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param count
	 *            出参|受影响的行数|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "params", comment = "绑定的参数字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = { @Param(name = "count", comment = "受影响的行数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "字典条件插入", style = "判断型", type = "同步组件", comment = "根据字典提供的绑定参数插入数据", date = "2017-01-03 02:48:25")
	public static TCResult P_insertByMap(String factory, String sqlName,
			JavaDict params, boolean commitFlag) {
		return _insert(factory, sqlName, params, commitFlag);
	}

	/**
	 * @category pojo条件插入
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param pojo
	 *            入参|pojo| {@link java.lang.Object}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param count
	 *            出参|受影响的行数|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "pojo", comment = "pojo", type = java.lang.Object.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = { @Param(name = "count", comment = "受影响的行数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "pojo条件插入", style = "判断型", type = "同步组件", comment = "根据pojo插入数据", date = "2017-01-03 02:48:25")
	public static TCResult P_insertByPojo(String factory, String sqlName,
			Object pojo, boolean commitFlag) {
		return _insert(factory, sqlName, pojo, commitFlag);
	}

	/**
	 * @category 类名条件插入
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param className
	 *            入参|className|{@link java.lang.String}
	 * @param data
	 *            入参|data|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param count
	 *            出参|受影响的行数|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "className", comment = "简单名或全限定名", type = java.lang.String.class),
			@Param(name = "data", comment = "对象的字段，key为属性名，value为属性值", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = { @Param(name = "count", comment = "受影响的行数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "类名条件插入", style = "判断型", type = "同步组件", comment = "通过提供类名与相关数据插入数据", date = "2017-01-03 02:48:25")
	public static TCResult P_insertByClassName(String factory, String sqlName,
			String className, JavaDict data, boolean commitFlag) {
		Object entityObject;
		try {
			entityObject = createEntityInstance(className, data);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.OBJCTL, e);
		}
		return _insert(factory, sqlName, entityObject, commitFlag);
	}

	/**
	 * @category 字典条件更新
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param params
	 *            入参|params|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param count
	 *            出参|受影响的行数|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "params", comment = "绑定的参数字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = { @Param(name = "count", comment = "受影响的行数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "字典条件更新", style = "判断型", type = "同步组件", comment = "根据字典提供的绑定参数更新数据", date = "2017-01-03 02:48:25")
	public static TCResult P_updateByMap(String factory, String sqlName,
			JavaDict params, boolean commitFlag) {
		return _update(factory, sqlName, params, commitFlag);
	}

	/**
	 * @category pojo条件更新
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param pojo
	 *            入参|pojo| {@link java.lang.Object}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param count
	 *            出参|受影响的行数|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "pojo", comment = "pojo", type = java.lang.Object.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = { @Param(name = "count", comment = "受影响的行数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "pojo条件更新", style = "判断型", type = "同步组件", comment = "根据pojo更新数据", date = "2017-01-03 02:48:25")
	public static TCResult P_updateByPojo(String factory, String sqlName,
			Object pojo, boolean commitFlag) {
		return _update(factory, sqlName, pojo, commitFlag);
	}

	/**
	 * @category 类名条件更新
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param className
	 *            入参|className|{@link java.lang.String}
	 * @param data
	 *            入参|data|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param count
	 *            出参|受影响的行数|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "className", comment = "简单名或全限定名", type = java.lang.String.class),
			@Param(name = "data", comment = "对象的字段，key为属性名，value为属性值", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = { @Param(name = "count", comment = "受影响的行数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "类名条件更新", style = "判断型", type = "同步组件", comment = "通过提供类名与相关数据更新数据", date = "2017-01-03 02:48:25")
	public static TCResult P_updateByClassName(String factory, String sqlName,
			String className, JavaDict data, boolean commitFlag) {
		Object entityObject;
		try {
			entityObject = createEntityInstance(className, data);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.OBJCTL, e);
		}
		return _update(factory, sqlName, entityObject, commitFlag);
	}

	/**
	 * @category 字典条件删除
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param params
	 *            入参|params|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param count
	 *            出参|受影响的行数|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "params", comment = "绑定的参数字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = { @Param(name = "count", comment = "受影响的行数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "字典条件删除", style = "判断型", type = "同步组件", comment = "根据字典提供的绑定参数删除数据", date = "2017-01-03 02:48:25")
	public static TCResult P_deleteByMap(String factory, String sqlName,
			JavaDict params, boolean commitFlag) {
		return _delete(factory, sqlName, params, commitFlag);
	}

	/**
	 * @category pojo条件删除
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param pojo
	 *            入参|pojo| {@link java.lang.Object}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param count
	 *            出参|受影响的行数|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "pojo", comment = "pojo", type = java.lang.Object.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = { @Param(name = "count", comment = "受影响的行数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "pojo条件删除", style = "判断型", type = "同步组件", comment = "根据pojo删除数据", date = "2017-01-03 02:48:25")
	public static TCResult P_deleteByPojo(String factory, String sqlName,
			Object pojo, boolean commitFlag) {
		return _delete(factory, sqlName, pojo, commitFlag);
	}

	/**
	 * @category 类名条件删除
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @param sqlName
	 *            入参|sqlName|{@link java.lang.String}
	 * @param className
	 *            入参|className|{@link java.lang.String}
	 * @param data
	 *            入参|data|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param commitFlag
	 *            入参|事务提交标识|boolean
	 * @param count
	 *            出参|受影响的行数|{@link int}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class),
			@Param(name = "sqlName", comment = "接口_id,映射文件中规定的nameSpace+id", type = java.lang.String.class),
			@Param(name = "className", comment = "简单名或全限定名", type = java.lang.String.class),
			@Param(name = "data", comment = "对象的字段，key为属性名，value为属性值", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "commitFlag", comment = "事务提交标识", type = boolean.class) })
	@OutParams(param = { @Param(name = "count", comment = "受影响的行数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "无满足条件记录") })
	@Component(label = "类名条件删除", style = "判断型", type = "同步组件", comment = "通过提供类名与相关数据删除数据", date = "2017-01-03 02:48:25")
	public static TCResult P_deleteByClassName(String factory, String sqlName,
			String className, JavaDict data, boolean commitFlag) {
		Object entityObject;
		try {
			entityObject = createEntityInstance(className, data);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.OBJCTL, e);
		}
		return _delete(factory, sqlName, entityObject, commitFlag);
	}

	/**
	 * @category 数据库事务提交
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "数据库事务提交", style = "判断型", type = "同步组件", comment = "提交当前session的事务", author = "Anonymous", date = "2017-04-26 10:30:10")
	public static TCResult P_commit(String factory) {
		SqlSession session = MBSessionProvider.getSession(factory);
		try {
			session.commit(true);
			return TCResult.newSuccessResult();
		} catch (Exception e) {
			session.rollback();
			return TCResult.newFailureResult(ErrorCode.MYBATIS, e);
		}
	}

	/**
	 * @category 数据库事务回滚
	 * @param factory
	 *            入参|factory|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "factory", comment = "mybatis工厂，如果为null或空字符串，则取第一个", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "数据库事务回滚", style = "判断型", type = "同步组件", comment = "回滚当前session的事务", author = "Anonymous", date = "2017-04-26 10:30:10")
	public static TCResult P_rollback(String factory) {
		SqlSession session = MBSessionProvider.getSession(factory);
		try {
			session.rollback();
			return TCResult.newSuccessResult();
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.MYBATIS, e);
		}
	}

	@SuppressWarnings("unchecked")
	private static TCResult _query(String factory, String sqlName,
			Object params, JavaList pageList, boolean commitFlag)
			throws Exception {
		try {
			SqlSession session = MBSessionProvider.getSession(factory);
			if (commitFlag)
				session.commit(true);
			int page = 1;
			int pageSize = 20;
			if (pageList != null && pageList.size() >= 3) {
				int control = pageList.getIntItem(0);
				int current = pageList.getIntItem(1);
				pageSize = pageList.getIntItem(2);
				switch (control) {
				case LAST_PAGE_MARK:
					long count = PageHelper.count(new CountSelect(session,
							sqlName, params));
					page = (int) Math.ceil((count * 1.0) / 3);
					break;
				case PREV_PAGE_MARK:
					page = current - 1;
					break;
				case NEXT_PAGE_MARK:
					page = current + 1;
					break;
				case CURRENT_PAGE_MARK:
					page = current;
					break;
				default:
					throw new RuntimeException("unknow page control num["
							+ control + "]");
				}
			}
			PageHelper.startPage(page, pageSize);
			Page<Object> nowPage = PageHelper.getLocalPage();
			List<Object> result = null;
			if (params != null) {
				result = session.selectList(sqlName, params);
			} else {
				result = session.selectList(sqlName);
			}
			JavaList rlist = new JavaList();
			for (Object o : result) {
				JavaDict dict = null;
				if (o instanceof Map) {
					dict = new JavaDict();
					dict.putAll((Map<? extends Object, ? extends Object>) o);
					dict = mapToJavaDict((Map<?, ?>) o);
				} else {
					dict = (JavaDict) objectToDict(o, 0);
				}
				rlist.add(dict);
			}
			rlist = listToJavaList(rlist);
			int resultCount = rlist.size();
			long allCount = nowPage.getTotal();
			int allPage = nowPage.getPages();
			int current = nowPage.getPageNum();
			return resultCount == 0 ? new TCResult(2) : TCResult
					.newSuccessResult(rlist, resultCount, allCount, allPage,
							current);
		} finally {
			PageHelper.clearPage();
		}
	}

	private static TCResult _insert(String factory, String sqlName,
			Object params, boolean commitFlg) {
		SqlSession session = MBSessionProvider.getSession(factory);
		try {
			int result = 0;
			if (params != null) {
				result = session.insert(sqlName, params);
			} else {
				result = session.insert(sqlName);
			}
			if (commitFlg) {
				session.commit();
			}
			return result == 0 ? new TCResult(2) : TCResult
					.newSuccessResult(result);
		} catch (Exception e) {
			session.rollback();
			return TCResult.newFailureResult(ErrorCode.MYBATIS, e);
		}
	}

	private static TCResult _update(String factory, String sqlName,
			Object params, boolean commitFlg) {
		SqlSession session = MBSessionProvider.getSession(factory);
		try {
			int result = 0;
			if (params != null) {
				result = session.update(sqlName, params);
			} else {
				result = session.update(sqlName);
			}
			if (commitFlg) {
				session.commit();
			}
			return result == 0 ? new TCResult(2) : TCResult
					.newSuccessResult(result);
		} catch (Exception e) {
			session.rollback();
			return TCResult.newFailureResult(ErrorCode.MYBATIS, e);
		}
	}

	private static TCResult _delete(String factory, String sqlName,
			Object params, boolean commitFlg) {
		SqlSession session = MBSessionProvider.getSession(factory);
		try {
			int result = 0;
			if (params != null) {
				result = session.delete(sqlName, params);
			} else {
				result = session.delete(sqlName);
			}
			if (commitFlg) {
				session.commit();
			}
			return result == 0 ? new TCResult(2) : TCResult
					.newSuccessResult(result);
		} catch (Exception e) {
			session.rollback();
			return TCResult.newFailureResult(ErrorCode.MYBATIS, e);
		}
	}

	private static JavaList listToJavaList(List<?> rlist) {
		JavaList list = new JavaList();
		for (Object o : rlist) {
			if (o instanceof List && !(o instanceof JavaList)) {
				list.add(listToJavaList((List<?>) o));
			} else {
				list.add(o);
			}
		}
		return list;
	}

	private static JavaDict mapToJavaDict(Map<?, ?> o) {
		JavaDict dict = new JavaDict();
		for (Map.Entry<? extends Object, ? extends Object> entry : o.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Map && !(value instanceof JavaDict)) {
				dict.put(key, mapToJavaDict((Map<?, ?>) value));
			} else {
				dict.put(key, value);
			}
		}
		return dict;
	}

	@SuppressWarnings("rawtypes")
	private static Object objectToDict(Object o, int deep)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (o == null) {
			return null;
		}
		if (deep >= 10) {
			throw new RuntimeException(
					"convert object to map terminated, the deep is larger than 10, maybe the object ["
							+ o + "]contains circular reference!");
		}
		if (isBasic(o)) {
			return o;
		}
		if (o instanceof List) {
			JavaList list = new JavaList();
			for (Object ob : (List) o) {
				list.add(objectToDict(ob, deep + 1));
			}
			return list;
		}
		if (o instanceof Map) {
			return o;
		}
		JavaDict dict = new JavaDict();
		for (Method method : o.getClass().getDeclaredMethods()) {
			Class<?>[] params = method.getParameterTypes();
			if (params.length != 0)
				continue;
			if (method.getReturnType() == Void.class)
				continue;
			int modifiers = method.getModifiers();
			if (!Modifier.isPublic(modifiers) || Modifier.isStatic(modifiers))
				continue;
			String getName = method.getName();
			if (!getName.startsWith("get"))
				continue;
			String name = normalNameFromGet(getName);
			Object value = method.invoke(o);
			dict.put(name, objectToDict(value, deep + 1));

		}
		return dict;
	}

	private static boolean isBasic(Object value) {
		return value instanceof Number || value instanceof String;
	}

	private static String normalNameFromGet(String name) {
		int index = name.indexOf("get") + 3;
		return name.substring(index, index + 1) + name.substring(index + 1);
	}

	private static void checkConflict(JavaDict dataMap, JavaDict sortMap) {
		if (dataMap == null || sortMap == null) {
			return;
		}
		for (Object key : sortMap.keySet()) {
			if (dataMap.containsKey(key)) {
				throw new IllegalArgumentException(
						"argument conflict between dataMap and sortMap,key=["
								+ key.toString() + "]");
			}
		}
	}

	private static Object createEntityInstance(String entityClassName,
			JavaDict data) throws Exception {
		Class<?> entityClass = null;
		if (entityClassName.contains(".")) {
			String simpleEntityClassName = entityClassName
					.substring(entityClassName.lastIndexOf(".") + 1);
			entityClass = MBSessionProvider
					.getEntityClass(simpleEntityClassName);
			if (entityClass == null) {
				entityClass = MBSessionProvider.getEntityClass(entityClassName);
			}
		} else {
			entityClass = MBSessionProvider.getEntityClass(entityClassName);
		}
		if (entityClass == null) {
			throw new ClassNotFoundException("找不到指定的类：" + entityClassName);
		}
		Object entityClassInstance = entityClass.newInstance();
		for (Entry<Object, Object> entry : data.entrySet()) {
			String colName = (String) entry.getKey();
			Object colValue = entry.getValue();

			if (colValue instanceof JavaDict) { // 属性是对象
				String foreignKeyClassName = entityClass
						.getDeclaredField(colName).getType().getName();
				Object foreignKeyObject = createEntityInstance(
						foreignKeyClassName, (JavaDict) colValue);
				invokeSetMethod(colName, entityClass, entityClassInstance,
						foreignKeyObject);
			} else if (colValue instanceof JavaList) { // 属性是对象集合(不支持)
				throw new IllegalArgumentException(
						"表的列名和列值非法，列值不支持JavaList类型，用法：{\"col1\":val1, \"col2\":{\"col3\":val3,\"col4\":val4}}");
			} else { // 属性是基础数据类型
				invokeSetMethod(colName, entityClass, entityClassInstance,
						colValue);
			}
		}
		return entityClassInstance;
	}

	private static void invokeSetMethod(String colName, Class<?> entityClass,
			Object entityClassInstance, Object parameterValue) throws Exception {
		String methodName = "set" + colName.substring(0, 1).toUpperCase()
				+ colName.substring(1);
		Method method;
		try {
			method = entityClass.getDeclaredMethod(methodName,
					parameterValue.getClass());
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethodException("[" + entityClass.getName()
					+ "]中没有字段：" + colName + "，请检查data中表的列名是否正确");
		}
		method.invoke(entityClassInstance, parameterValue);
	}

	private static class CountSelect implements ISelect {
		private SqlSession session;
		private Object parameters;
		private String sql;

		public CountSelect(SqlSession session, String sql, Object parameters) {
			super();
			this.session = session;
			this.parameters = parameters;
			this.sql = sql;
			if (session == null || sql == null) {
				throw new IllegalArgumentException(
						"session and sql can not be null");
			}
		}

		@Override
		public void doSelect() {
			if (parameters == null) {
				session.selectList(sql);
			} else {
				session.selectList(sql, parameters);
			}
		}
	}

}
