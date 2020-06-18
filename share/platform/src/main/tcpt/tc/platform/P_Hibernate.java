package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.jcomponent.HBSessionProvider;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * Hibernate操作类组件
 * 
 * @author linsheng
 * @date 2015-06-29 15:29:25
 * @version 2.0
 */
@ComponentGroup(level = "平台", groupName = "Hibernate操作类组件")
public class P_Hibernate {

	/**
	 * @param factoryName
	 *            入参|hibernate的Factory名|{@link java.lang.String}
	 * @param pojo
	 *            入参|要添加或者更新对应表的持久化类|{@link java.lang.Object}
	 * @param commitFlg
	 *            入参|事务提交标识|boolean
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "pojo", comment = "要添加或者更新对应表的持久化类Object", type = java.lang.Object.class),
			@Param(name = "commitFlg", comment = "事务提交标识Boolean", type = boolean.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "添加或更新数据", style = "判断型", type = "同步组件", comment = "根据实体类添加或更新记录，如果没有记录就添加，有记录就更新", date = "Tue Jul 07 17:02:25 CST 2015")
	public static TCResult saveOrUpdate(String factoryName, Object pojo, boolean commitFlg) {
		Session session = HBSessionProvider.getSession(factoryName);
		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}
			session.saveOrUpdate(pojo);
			if (commitFlg) {
				session.getTransaction().commit();
			}
			return TCResult.newSuccessResult();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			return TCResult.newFailureResult(ErrorCode.HIBERNATE, e);
		}
	}

	/**
	 * @param factoryName
	 *            入参|指定的Factory名,如果为空则从第一个Factory中获取session|
	 *            {@link java.lang.String}
	 * @param entityClassName
	 *            入参|完全限定类名Sring，如："cn.com.hibernate.table.Usertable"|
	 *            {@link java.lang.String}
	 * @param data
	 *            入参|表的列名和列值JavaDict，如：{"col1":val1, "col2": val2}|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param commitFlg
	 *            入参|提交标识符|boolean
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "entityClassName", comment = "类名String，如：\"hibernate.table.User\"或\"User\"", type = java.lang.String.class),
			@Param(name = "data", comment = "表的列名和列值JavaDict，如：{ \"col1\":val1, \"col2\": {\"xx\":val3,\"yy\":val4} }", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "commitFlg", comment = "提交标识符boolean", type = boolean.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "用类名添加或更新数据", style = "判断型", type = "同步组件", comment = "根据实体类名添加或更新记录，如果没有记录就添加，有记录就更新，data的参数用法：{ \"col1\":val1, \"col2\": {\"xx\":val3,\"yy\":val4} }；类名可以是包名+类名，如hibernate.table.Usertable；也可以是单独的类名Usertable，使用单独类名时确保classpath中没有其他同名的类", date = "2015-12-04 10:36:27")
	public static TCResult saveOrUpdateWithClassName(String factoryName, String entityClassName, JavaDict data,
			boolean commitFlg) {
		if (data == null || data.size() <= 0) {
			throw new IllegalArgumentException("表的列名和列值非法，用法：{\"col1\":val1, \"col2\": val2}");
		}
		Object entityObject;
		try {
			entityObject = createEntityInstance(entityClassName, data);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.OBJCTL, e);
		}
		return saveOrUpdate(factoryName, entityObject, commitFlg);
	}

	/**
	 * @param factoryName
	 *            入参|hibernate的Factory名|{@link java.lang.String}
	 * @param pojo
	 *            入参|需要删除表记录对应的实体类|{@link java.lang.Object}
	 * @param commitFlg
	 *            入参|事务提交标识|boolean
	 * @param closeFlg
	 *            入参|关闭Session标识|boolean
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "pojo", comment = "需要删除表记录对应的实体类Object", type = java.lang.Object.class),
			@Param(name = "commitFlg", comment = "事务提交标识Boolean", type = boolean.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "删除数据", style = "判断型", type = "同步组件", comment = "根据实体类删除", date = "Tue Jul 07 17:11:41 CST 2015")
	public static TCResult delete(String factoryName, Object pojo, boolean commitFlg) {
		Session session = HBSessionProvider.getSession(factoryName);
		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}
			session.delete(pojo);
			if (commitFlg) {
				session.getTransaction().commit();
			}
			return TCResult.newSuccessResult();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			return TCResult.newFailureResult(ErrorCode.HIBERNATE, e);
		}
	}

	/**
	 * @param factoryName
	 *            入参|指定的Factory名,如果为空则从第一个Factory中获取session|
	 *            {@link java.lang.String}
	 * @param entityClassName
	 *            入参|完全限定类名Sring，如："cn.com.hibernate.table.Usertable"|
	 *            {@link java.lang.String}
	 * @param data
	 *            入参|表的列名和列值JavaDict，如：{"col1":val1, "col2": val2}|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param commitFlg
	 *            入参|提交标识符|boolean
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "entityClassName", comment = "类名Sring，如：\"hibernate.table.User\"或\"User\"", type = java.lang.String.class),
			@Param(name = "data", comment = "表的列名和列值JavaDict，如：{ \"col1\":val1, \"col2\": {\"xx\":val3,\"yy\":val4} }", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "commitFlg", comment = "提交标识符boolean", type = boolean.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "用类名删除数据", style = "判断型", type = "同步组件", comment = "根据实体类名删除记录，data的参数用法：{ \"col1\":val1, \"col2\": {\"xx\":val3,\"yy\":val4} }；类名可以是包名+类名，如hibernate.table.Usertable；也可以是单独的类名Usertable，使用单独类名时确保classpath中没有其他同名的类", date = "2015-12-04 10:36:27")
	public static TCResult deleteWithClassName(String factoryName, String entityClassName, JavaDict data,
			boolean commitFlg) {
		if (data == null || data.size() <= 0) {
			throw new IllegalArgumentException("表的列名和列值非法，用法：{\"col1\":val1, \"col2\": val2}");
		}
		Object entityObject = null;
		try {
			entityObject = createEntityInstance(entityClassName, data);
		} catch (Exception e) {
			return TCResult.newFailureResult(ErrorCode.OBJCTL, e);
		}
		return delete(factoryName, entityObject, commitFlg);
	}

	/**
	 * @param factoryName
	 *            入参|hibernate的Factory名|{@link java.lang.String}
	 * @param hql
	 *            入参|hql语句|{@link java.lang.String}
	 * @param fieldInfo
	 *            入参|条件查询语句中的属性信息，key为属性名，value为属性的值|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param limit
	 *            入参|分页查找标识|boolean
	 * @param firstResult
	 *            入参|分页查找开始位置（首位从0开始）|int
	 * @param maxResult
	 *            入参|查找的个数|int
	 * @param closeFlg
	 *            入参|关闭Session标识|boolean
	 * @param result
	 *            出参|查询结果|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "hql", comment = "hql语句String", type = java.lang.String.class),
			@Param(name = "fieldInfo", comment = "条件查询语句中的属性信息JavaDict，key为属性名(表的列名)，value为属性的值(列值),如[\"key\",val]", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "limit", comment = "分页查找标识Boolean", type = boolean.class),
			@Param(name = "firstResult", comment = "分页查找开始位置int（首位从0开始）", type = int.class),
			@Param(name = "maxResult", comment = "查找的行数int", type = int.class) })
	@OutParams(param = { @Param(name = "result", comment = "查询结果", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "查询数据", style = "判断型", type = "同步组件", comment = "使用HQL语句查询操作，其中fieldInfo中的key为属性名(表的列名)，value为属性的值(列值)", date = "Tue Jul 07 17:23:13 CST 2015")
	public static TCResult hqlSelect(String factoryName, String hql, JavaDict fieldInfo, boolean limit,
			int firstResult, int maxResult) {
		Session session = HBSessionProvider.getSession(factoryName);
		try {
			Query query = session.createQuery(hql);
			if (limit) {
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResult);
			}
			if (fieldInfo != null && fieldInfo.size() > 0) {
				for (Entry<Object, Object> entry : fieldInfo.entrySet()) {
					query.setParameter((String) entry.getKey(), entry.getValue());
				}
			}
			return TCResult.newSuccessResult(query.list());
		} catch (HibernateException e) {
			return TCResult.newFailureResult(ErrorCode.HIBERNATE, e);
		}
	}

	/**
	 * @param factoryName
	 *            入参|需要关闭的Session所属的SessionFactory名|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "关闭Session", style = "判断型", type = "同步组件", comment = "关闭Session", date = "Thu Jul 09 14:37:16 CST 2015")
	public static TCResult closeSession(String factoryName) {
		Session session = HBSessionProvider.getSession(factoryName);
		if (!session.isOpen()) {
			return TCResult.newFailureResult(ErrorCode.AGR, "session已经关闭");
		}
		try {
			session.close();
		} catch (HibernateException e) {
			return TCResult.newFailureResult(ErrorCode.HIBERNATE, e);
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param factoryName
	 *            入参|hibernate的Factory名|{@link java.lang.String}
	 * @param sql
	 *            入参|SQL语句|{@link java.lang.String}
	 * @param values
	 *            入参|添加更新与删除字段的值|{@link
	 *            cn.com.agree.afa.svc.javaengine.context.JavaList.class}
	 * @param commitFlg
	 *            入参|事务提交标识|boolean
	 * @param rows
	 *            出参|更新的行数|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "sql", comment = "SQL语句String", type = java.lang.String.class),
			@Param(name = "values", comment = "添加更新与删除字段的值JavaList,如[VAL1,VAL2]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "commitFlg", comment = "事务提交标识Boolean", type = boolean.class) })
	@OutParams(param = { @Param(name = "rows", comment = "更新的行数int", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "SQL添加更新与删除数据", style = "判断型", type = "同步组件", comment = "根据SQL添加、更新和删除数据，values参数为：[值1,值2,...]，值的位置对应SQL语句里的?，顺序不能乱", date = "Fri Jul 10 14:57:05 CST 2015")
	public static TCResult sqlDataModify(String factoryName, String sql, JavaList values, boolean commitFlg) {
		Session session = HBSessionProvider.getSession(factoryName);
		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}
			SQLQuery query = session.createSQLQuery(sql);
			if (values != null && values.size() > 0) {
				for (Object value : values) {
					query.setParameter(values.indexOf(value), value);
				}
			}
			int rows = query.executeUpdate();
			if (commitFlg) {
				session.getTransaction().commit();
			}
			return TCResult.newSuccessResult(rows);
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			return TCResult.newFailureResult(ErrorCode.HIBERNATE, e);
		}
	}

	/**
	 * @param factoryName
	 *            入参|hibernate的Factory名|{@link java.lang.String}
	 * @param tableName
	 *            入参|表名|{@link java.lang.String}
	 * @param values
	 *            入参|新增列值key,value的list|{@link
	 *            cn.com.agree.afa.svc.javaengine.context.JavaList.class}
	 * @param commitFlg
	 *            入参|事务提交标识|boolean
	 * @param rows
	 *            出参|更新的行数|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "tableName", comment = "表名String", type = java.lang.String.class),
			@Param(name = "values", comment = "新增列值JavaList，如：[[\"COL1\",VAL1],[\"COL2\",VAL2]...]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "commitFlg", comment = "事务提交标识Boolean", type = boolean.class) })
	@OutParams(param = { @Param(name = "rows", comment = "更新的行数int", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功") })
	@Component(label = "SQL添加数据", style = "判断型", type = "同步组件", comment = "SQL添加数据，登记数据到数据库中,values参数格式为[[\"列1\",值1],[\"列2\",值2],...]", date = "Fri Jul 10 14:57:05 CST 2015")
	public static TCResult sqlInsert(String factoryName, String tableName, JavaList values, boolean commitFlg) {
		List<Object> columnValues = new ArrayList<Object>();
		StringBuilder sqlcmd = new StringBuilder();
		sqlcmd.append("INSERT INTO ").append(tableName).append("(");
		for (Object value : values) {
			if (value instanceof JavaList) {
				if (((JavaList) value).size() != 2) {
					return TCResult.newFailureResult(ErrorCode.AGR, "List中的每个单元列表值非法,里面要素个数不为2");
				}
				sqlcmd.append(((JavaList) value).get(0));
				if (values.indexOf(value) != values.size() - 1) {
					sqlcmd.append(",");
				}
				columnValues.add(((JavaList) value).get(1));
			} else {
				return TCResult.newFailureResult(ErrorCode.AGR, "列值列表中的每个单元类型非法,不是列表");
			}
		}
		sqlcmd.append(") ").append("VALUES(");
		for (int i = 0; i < columnValues.size(); i++) {
			Object val = columnValues.get(i);
			if (val == null) {
				sqlcmd.append("\'\'");
			} else {
				if (val instanceof String || val instanceof Character) {
					sqlcmd.append("\'").append(val).append("\'");
				} else {
					sqlcmd.append(String.valueOf(val));
				}
				if (i != columnValues.size() - 1) {
					sqlcmd.append(",");
				}
			}
		}
		sqlcmd.append(")");
		AppLogger.info("insert 的语句为：" + sqlcmd.toString());

		Session session = HBSessionProvider.getSession(factoryName);
		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}
			SQLQuery query = session.createSQLQuery(sqlcmd.toString());
			int rows = query.executeUpdate();
			if (commitFlg) {
				session.getTransaction().commit();
			}
			return TCResult.newSuccessResult(rows);
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			return TCResult.newFailureResult(ErrorCode.HIBERNATE, e);
		}
	}

	/**
	 * @param factoryName
	 *            入参|hibernate的Factory名|{@link java.lang.String}
	 * @param tableName
	 *            入参|SQL语句|{@link java.lang.String}
	 * @param colInfos
	 *            入参|列信息|{@link
	 *            cn.com.agree.afa.svc.javaengine.context.JavaList.class}
	 * @param condition
	 *            入参|条件信息|{@link
	 *            cn.com.agree.afa.svc.javaengine.context.JavaList.class}
	 * @param commitFlg
	 *            入参|事务提交标识|boolean
	 * @param rows
	 *            出参|更新的行数|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "tableName", comment = "表名String", type = java.lang.String.class),
			@Param(name = "colInfos", comment = "列信息JavaList，如：[[\"COL1\",VAL1],[\"COL2\",VAL2]]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "condition", comment = "条件信息JavaList，如：[\"列\",\"=\",值]或者[[\"列1\",\"=\",值1,\"and\"],[\"列2\",\"<\",值2]]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "commitFlg", comment = "事务提交标识Boolean", type = boolean.class) })
	@OutParams(param = { @Param(name = "rows", comment = "更新的行数int", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功"), @Return(id = "2", desp = "异常") })
	@Component(label = "SQL更新数据", style = "选择型", type = "同步组件", comment = "SQL更新数据，更新指定条件指定库表的指定列值，条件信息的用法为：[[\"列1\",\"=\",值,\"and\"],[\"列2\",\"=\",值2]] = (列1=值1 and 列2=值2)，更新影响的笔数放到list[3]中", date = "Fri Jul 10 14:57:05 CST 2015")
	public static TCResult sqlUpdate(String factoryName, String tableName, JavaList colInfos, JavaList condition,
			boolean commitFlg) {
		StringBuilder sqlcmd = new StringBuilder();
		sqlcmd.append("UPDATE ").append(tableName).append(" SET ");
		for (int i = 0; i < colInfos.size(); i++) {
			Object colInfo = colInfos.get(i);
			if (colInfo instanceof JavaList) {
				if (((JavaList) colInfo).size() != 2) {
					return TCResult.newFailureResult(ErrorCode.AGR,
							"列信息的每个单元列表值非法，里面要素个数不为2，用法：[[\"列1\",值1],[\"列2\",值2]...]");
				}
				sqlcmd.append(((JavaList) colInfo).get(0)).append("=");
				if (((JavaList) colInfo).get(1) instanceof String || ((JavaList) colInfo).get(1) instanceof Character) {
					sqlcmd.append("\'").append(((JavaList) colInfo).get(1)).append("\'");
				} else {
					sqlcmd.append(String.valueOf(((JavaList) colInfo).get(1)));
				}
				if (i != colInfos.size() - 1) {
					sqlcmd.append(",");
				}
			} else {
				return TCResult.newFailureResult(ErrorCode.AGR,
						"列信息的每个单元值非法，每个单元必须是List，用法：[[\"列1\",值1],[\"列2\",值2]...]");
			}
		}
		if (condition != null && condition.size() != 0) {
			sqlcmd.append(" WHERE ");
			for (Object o : condition) {
				if (o instanceof JavaList) {
					if (((JavaList) o).size() < 3) {
						return TCResult.newFailureResult(ErrorCode.AGR,
								"条件信息错误，参数个数不正确，用法：[\"列\",\"=\",值]或者[[\"列1\",\"=\",值1,\"and\"],[\"列2\",\"<\",值2]]");
					}
					sqlcmd.append(((JavaList) o).get(0)).append(((JavaList) o).get(1));
					if (((JavaList) o).get(2) instanceof String || ((JavaList) o).get(2) instanceof Character) {
						sqlcmd.append("\'").append(((JavaList) o).get(2)).append("\'");
					} else {
						sqlcmd.append(String.valueOf(((JavaList) o).get(2)));
					}
					if (((JavaList) o).size() == 4 && ((JavaList) o).get(3) != null) {
						sqlcmd.append(((JavaList) o).get(3)).append(" ");
					}
				} else {
					if (condition.size() != 3) {
						return TCResult.newFailureResult(ErrorCode.AGR,
								"条件信息错误，用法：[\"列\",\"=\",值]或者[[\"列1\",\"=\",值1,\"and\"],[\"列2\",\"<\",值2]]");
					}
					sqlcmd.append(condition.get(0)).append(condition.get(1));
					if (condition.get(2) instanceof String || condition.get(2) instanceof Character) {
						sqlcmd.append("\'").append(condition.get(2)).append("\'");
					} else {
						sqlcmd.append(String.valueOf(condition.get(2)));
					}
					break;
				}
			}
		}

		Session session = HBSessionProvider.getSession(factoryName);
		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}
			SQLQuery query = session.createSQLQuery(sqlcmd.toString());
			int rows = query.executeUpdate();
			if (rows == 0) {
				return new TCResult(2, ErrorCode.REMOTE, "更新无满足条件记录");
			}
			if (commitFlg) {
				session.getTransaction().commit();
			}
			return TCResult.newSuccessResult(rows);
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			return TCResult.newFailureResult(ErrorCode.HIBERNATE, e);
		}
	}

	/**
	 * @param factoryName
	 *            入参|hibernate的Factory名|{@link java.lang.String}
	 * @param tableName
	 *            入参|表名|{@link java.lang.String}
	 * @param condition
	 *            入参|条件信息|{@link
	 *            cn.com.agree.afa.svc.javaengine.context.JavaList.class}
	 * @param commitFlg
	 *            入参|事务提交标识|boolean
	 * @param rows
	 *            出参|删除的行数|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "tableName", comment = "表名String", type = java.lang.String.class),
			@Param(name = "condition", comment = "条件的值JavaList，如：[\"列\",\"=\",值]或[[\"列1\",\"=\",值1,\"and\"],[\"列2\",\"<\",值2]]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "commitFlg", comment = "事务提交标识Boolean", type = boolean.class) })
	@OutParams(param = { @Param(name = "rows", comment = "删除的行数int", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功"), @Return(id = "2", desp = "异常") })
	@Component(label = "SQL删除数据", style = "选择型", type = "同步组件", comment = "原生SQL删除数据，删除指定条件的记录,其中条件信息的用法为：[[\"COL1\",\"=\",VAL1,\"AND\"],[\"COL2\",\"!=\",VAL2,null]] = COL1=VAL1 AND COL2=VAL2", date = "Fri Jul 10 14:57:05 CST 2015")
	public static TCResult sqlDelete(String factoryName, String tableName, JavaList condition, boolean commitFlg) {
		StringBuilder sqlcmd = new StringBuilder();
		sqlcmd.append("DELETE FROM ").append(tableName);
		if (condition == null || condition.size() == 0) {
			sqlcmd.append("");
		} else {
			sqlcmd.append(" WHERE ");
			for (Object o : condition) {
				if (o instanceof JavaList) {
					if (((JavaList) o).size() < 3) {
						return TCResult.newFailureResult(ErrorCode.AGR,
								"输入的条件参数错误，用法：[\"列\",\"=\",值]或者[[\"列1\",\"=\",值1,\"and\"],[\"列2\",\"<\",值2]]");
					}
					sqlcmd.append(((JavaList) o).get(0)).append(((JavaList) o).get(1));
					if (((JavaList) o).get(2) instanceof String || ((JavaList) o).get(2) instanceof Character) {
						sqlcmd.append("\'").append(((JavaList) o).get(2)).append("\'");
					} else {
						sqlcmd.append(String.valueOf(((JavaList) o).get(2)));
					}
					if (((JavaList) o).size() == 4 && ((JavaList) o).get(3) != null) {
						sqlcmd.append(" ").append(((JavaList) o).get(3)).append(" ");
					}
				} else {
					if (condition.size() != 3) {
						return TCResult.newFailureResult(ErrorCode.AGR,
								"输入的条件参数错误，用法：[\"列\",\"=\",值]或者[[\"列1\",\"=\",值1,\"and\"],[\"列2\",\"<\",值2]]");
					}
					sqlcmd.append(condition.get(0)).append(condition.get(1));
					if (condition.get(2) instanceof String || condition.get(2) instanceof Character) {
						sqlcmd.append("\'").append(condition.get(2)).append("\'");
					} else {
						sqlcmd.append(String.valueOf(condition.get(2)));
					}
					break;
				}
			}
		}

		Session session = HBSessionProvider.getSession(factoryName);
		try {
			if (!session.getTransaction().isActive()) {
				session.beginTransaction();
			}
			SQLQuery query = session.createSQLQuery(sqlcmd.toString());
			int rows = query.executeUpdate();
			if (rows == 0) {
				return new TCResult(2, ErrorCode.REMOTE, "删除时未找到记录");
			}
			if (commitFlg) {
				session.getTransaction().commit();
			}
			return TCResult.newSuccessResult(rows);
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			return TCResult.newFailureResult(ErrorCode.HIBERNATE, e);
		}
	}

	/**
	 * @param factoryName
	 *            入参|hibernate的Factory名|{@link java.lang.String}
	 * @param sql
	 *            入参|SQL语句|{@link java.lang.String}
	 * @param maxResult
	 *            入参|查找的行数|int
	 * @param num
	 *            出参|查询到的行数|int
	 * @param result
	 *            出参|所有的行数据|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "factoryName", comment = "指定的Factory名,如果为空则从第一个Factory中获取session", type = java.lang.String.class),
			@Param(name = "sql", comment = "SQL语句", type = java.lang.String.class),
			@Param(name = "maxResult", comment = "查找的行数int", type = int.class) })
	@OutParams(param = {
			@Param(name = "num", comment = "查询结果的行数int", type = int.class),
			@Param(name = "result", comment = "所有的行数据JavaList", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"), @Return(id = "1", desp = "成功"), @Return(id = "2", desp = "异常") })
	@Component(label = "SQL查询数据", style = "选择型", type = "同步组件", comment = "原生SQL查询数据，根据sql查询语句获取查询的数据,默认是取所有,可以指定需要获取的行数,list[3]中存放查询到的笔数和数据,格式为:[笔数,所有行的list]", date = "Fri Jul 10 10:40:31 CST 2015")
	public static TCResult sqlSelect(String factoryName, String sql, int maxResult) {
		Session session = HBSessionProvider.getSession(factoryName);
		try {
			SQLQuery query = session.createSQLQuery(sql);
			if (maxResult > 0) {
				query.setMaxResults(maxResult);
			}
			List<?> result = query.list();
			if (result.size() == 0) {
				return new TCResult(2, ErrorCode.REMOTE, "无满足条件的记录");
			}
			JavaList allRows = new JavaList();
			for (int i = 0; i < result.size(); i++) {
				Object[] values = (Object[]) result.get(i);
				JavaList singleRow = new JavaList();
				for (int j = 0; j < values.length; j++) {
					singleRow.add(values[j]);
				}
				allRows.add(singleRow);
			}
			return TCResult.newSuccessResult(result.size(), allRows);
		} catch (HibernateException e) {
			return TCResult.newFailureResult(ErrorCode.HIBERNATE, e);
		}
	}

	private static Object createEntityInstance(String entityClassName, JavaDict data) throws Exception {
		Class<?> entityClass = null;
		if (entityClassName.contains(".")) {
			String simpleEntityClassName = entityClassName.substring(entityClassName.lastIndexOf(".") + 1);
			entityClass = HBSessionProvider.getEntityClass(simpleEntityClassName);
			if (entityClass == null) {
				entityClass = HBSessionProvider.getEntityClass(entityClassName);
			}
		} else {
			entityClass = HBSessionProvider.getEntityClass(entityClassName);
		}
		if (entityClass == null) {
			throw new ClassNotFoundException("找不到指定的类：" + entityClassName);
		}
		Object entityClassInstance = entityClass.newInstance();
		for (Entry<Object, Object> entry : data.entrySet()) {
			String colName = (String) entry.getKey();
			Object colValue = entry.getValue();

			if (colValue instanceof JavaDict) { // 属性是对象
				String foreignKeyClassName = entityClass.getDeclaredField(colName).getType().getName();
				Object foreignKeyObject = createEntityInstance(foreignKeyClassName, (JavaDict) colValue);
				invokeSetMethod(colName, entityClass, entityClassInstance, foreignKeyObject);
			} else if (colValue instanceof JavaList) { // 属性是对象集合(不支持)
				throw new IllegalArgumentException(
						"表的列名和列值非法，列值不支持JavaList类型，用法：{\"col1\":val1, \"col2\":{\"col3\":val3,\"col4\":val4}}");
			} else { // 属性是基础数据类型
				invokeSetMethod(colName, entityClass, entityClassInstance, colValue);
			}
		}
		return entityClassInstance;
	}

	private static void invokeSetMethod(String colName, Class<?> entityClass, Object entityClassInstance,
			Object parameterValue) throws Exception {
		String methodName = "set" + colName.substring(0, 1).toUpperCase() + colName.substring(1);
		Method method;
		try {
			method = entityClass.getDeclaredMethod(methodName, parameterValue.getClass());
		} catch (NoSuchMethodException e) {
			throw new NoSuchMethodException("[" + entityClass.getName() + "]中没有字段：" + colName + "，请检查data中表的列名是否正确");
		}
		method.invoke(entityClassInstance, parameterValue);
	}

}