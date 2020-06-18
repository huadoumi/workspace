package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * 统一数据访问组件
 * 
 * @date 2016-12-05 15:37:14
 */
@ComponentGroup(level = "平台", groupName = "数据访问组件")
public class P_DataAccesser {

	/**
	 * 定义数据库存储类型
	 */
	static enum Type {

		/** 关系型数据库 */
		RELATIONAL,

		/** 非关系型数据库, 键值对存储型 */
		KEY_VALUE,

		/** 非关系型数据库, 文档型 */
		DOCUMENT,

		/** 非关系型数据库, 列存储型 */
		WILD_COLUMN,

		/** 非关系型数据库, 图存储型 */
		GRAPH,
	}

	/**
	 * 数据库类型
	 */
	static enum Database {
		
		// RDBMS
		MYSQL(Type.RELATIONAL), ORACLE(Type.RELATIONAL), SQLSERVER(
				Type.RELATIONAL), SYBASE(Type.RELATIONAL), DB2(Type.RELATIONAL), POSTGRESQL(
				Type.RELATIONAL), HIVE(Type.RELATIONAL),INFORMIX(Type.RELATIONAL),

		// key-value
		REDIS(Type.KEY_VALUE), MEMCACHED(Type.KEY_VALUE),

		// document
		MONGODB(Type.DOCUMENT), COUCHDB(Type.DOCUMENT), SEQUOIADB(Type.DOCUMENT),

		// wild column
		HBASE(Type.WILD_COLUMN), HYPERTABLE(Type.WILD_COLUMN), CASSANDRA(Type.WILD_COLUMN),

		// graph
		NEO4J(Type.GRAPH), FLOCKDB(Type.GRAPH);

		final Type type;

		Database(Type type) {
			this.type = type;
		}

		static Database fromString(String name) {
			try {
				return valueOf(name.toUpperCase());
			} catch (IllegalArgumentException e) {
				AppLogger.error("unsupported database " + name);
				throw new IllegalArgumentException("unsupported database "
						+ name, e);
			}
		}
		
		boolean isRelational() {
			return this.type == Type.RELATIONAL;
		}
	}

	/**
	 * 开启事务, 目前只支持关系型数据库
	 * @category 开启事务
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @param database
	 *            入参|数据库名字|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class),
			@Param(name = "database", comment = "数据库名字", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "开启事务", style = "判断型", type = "同步组件", comment = "开启事务, 暂不支持NoSQL", date = "2016-12-05 03:47:08")
	public static TCResult P_beginTransaction(String poolName, String database) {
		Database db = Database.fromString(requireNonNull(database, "database"));
		
		if (!db.isRelational()) {
			throw new UnsupportedOperationException("unsupported database: " + database);
		}
		
		// TODO implement
		
		return TCResult.newSuccessResult();
	}

	/**
	 * 提交事务, 目前只支持关系型数据库
	 * @category 提交事务
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @param database
	 *            入参|数据库名字|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class),
			@Param(name = "database", comment = "数据库名字", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "提交事务", style = "判断型", type = "同步组件", comment = "提交事务, 暂不支持NoSQL", author = "Anonymous", date = "2016-12-05 03:48:16")
	public static TCResult P_commit(String poolName, String database) {
		Database db = Database.fromString(requireNonNull(database, "database"));
		
		if (!db.isRelational()) {
			throw new UnsupportedOperationException("unsupported database: " + database);
		}
		
		// TODO implement
		
		return TCResult.newSuccessResult();
	}

	/**
	 * 回滚事务, 目前只支持关系型数据库
	 * @category 回滚事务
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @param database
	 *            入参|数据库名字|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class),
			@Param(name = "database", comment = "数据库名字", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "回滚事务", style = "判断型", type = "同步组件", comment = "回滚事务, 暂不支持NoSQL", author = "Anonymous", date = "2016-12-05 03:49:19")
	public static TCResult P_rollback(String poolName, String database) {
		Database db = Database.fromString(requireNonNull(database, "database"));
		
		if (!db.isRelational()) {
			throw new UnsupportedOperationException("unsupported database: " + database);
		}
		
		// TODO implement
		
		return TCResult.newSuccessResult();
	}

	/**
	 * 查询操作
	 * 
	 * @category 查询操作
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @param database
	 *            入参|数据库名字|{@link java.lang.String}
	 * @param statement
	 *            入参|查询语句|{@link java.lang.String}
	 * @param hits
	 *            出参|查询结果条数|int
	 * @param resultList
	 *            出参|查询结果列表, 规则如下:<br/><ul><li>1.当为关系型数据库时, 每一个item都为一个JavaList, 每个JavaList为一行数据, 其值顺序对应着statement中select后面跟着的字段的值</li>
	 *			   <li>2.当为非关系型数据库且为key-value类型时, 如果预期查询结果是键值对类型(比如redis 的get)，则偶下标为键，奇下标为值，如[key1，value1，key2，value2,…]；如果预期查询结果不是键值对时(比如redis的llen)，则list顺序对应查询结果值，如[value1,value2,…]</li>
	 *			   <li>3.当为非关系型数据库且为document类型时, resultList中每一项都为一个JavaDict, 每个JavaDict代表一个document</li>
	 *			   <li>4.当为非关系型数据库其为列存储类型时, 其规则和关系型数据库一致</li>
	 *			   <li>5.当为非关系型数据库且为图存储类型时, resultList中每一项都为一个JavaDict, 每个JavaDict为一个node</li></ul>|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList} | <br/>
	 *            
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class),
			@Param(name = "database", comment = "数据库名字", type = java.lang.String.class),
			@Param(name = "statement", comment = "查询语句", type = java.lang.String.class) })
	@OutParams(param = {
			@Param(name = "hits", comment = "查询结果条数", type = int.class),
			@Param(name = "resultList", comment = "查询结果列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "查询操作", style = "判断型", type = "同步组件", comment = "查询操作", date = "2016-12-06 02:56:00")
	public static TCResult P_query(String poolName, String database,
			String statement) {
		@SuppressWarnings("unused")
		Database db = Database.fromString(requireNonNull(database, "database"));
		
		// TODO implement
		
		return TCResult.newSuccessResult();
	}

	/**
	 * @category 更新操作
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @param database
	 *            入参|数据库名字|{@link java.lang.String}
	 * @param statement
	 *            入参|更新语句|{@link java.lang.String}
	 * @param affectedRows
	 *            出参|受影响记录行数|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class),
			@Param(name = "database", comment = "数据库名字", type = java.lang.String.class),
			@Param(name = "statement", comment = "更新语句", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "affectedRecords", comment = "受影响记录数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "更新操作", style = "判断型", type = "同步组件", comment = "更新操作", author = "Anonymous", date = "2016-12-05 04:04:07")
	public static TCResult P_update(String poolName, String database,
			String statement) {
		@SuppressWarnings("unused")
		Database db = Database.fromString(requireNonNull(database, "database"));
		
		// TODO implement
		
		return TCResult.newSuccessResult();
	}

	/**
	 * @throws UnsupportedOperationException
	 *             如果对应数据库不支持预编译
	 * @category 预编译查询
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @param database
	 *            入参|数据库名字|{@link java.lang.String}
	 * @param statement
	 *            入参|查询语句|{@link java.lang.String}
	 * @param hits
	 *            出参|查询结果条数|int
	 * @param resultList
	 *            出参|查询结果列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class),
			@Param(name = "database", comment = "数据库名字", type = java.lang.String.class),
			@Param(name = "statement", comment = "查询语句", type = java.lang.String.class) })
	@OutParams(param = {
			@Param(name = "hits", comment = "查询结果条数", type = int.class),
			@Param(name = "resultList", comment = "查询结果列表", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "预编译查询", style = "判断型", type = "同步组件", comment = "预编译查询", date = "2016-12-06 02:57:36")
	public static TCResult P_preparedQuery(String poolName, String database,
			String statement) {
		Database db = Database.fromString(requireNonNull(database,
				"database"));
		if (!db.isRelational()) {
			throw new UnsupportedOperationException("unsupported database " + database);
		}
		
		// TODO implement
		
		return TCResult.newSuccessResult();
	}

	/**
	 * @category 预编译更新
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @param database
	 *            入参|数据库名字|{@link java.lang.String}
	 * @param statement
	 *            入参|更新语句|{@link java.lang.String}
	 * @param affectedRecords
	 *            出参|受影响记录数|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * @throws UnsupportedOperationException 如果对应数据库不支持预编译
	 */
	@InParams(param = {
			@Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class),
			@Param(name = "database", comment = "数据库名字", type = java.lang.String.class),
			@Param(name = "statement", comment = "更新语句", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "affectedRecords", comment = "受影响记录数", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "预编译更新", style = "判断型", type = "同步组件", comment = "预编译更新, 只能用于RDBMS,", author = "Anonymous", date = "2016-12-05 04:14:34")
	public static TCResult P_preparedUpdate(String poolName, String database,
			String statement) {
		Database db = Database.fromString(requireNonNull(database, "database"));
		if (!db.isRelational()) {
			throw new UnsupportedOperationException("unsupported database " + database);
		}
		
		// TODO implement
		
		return TCResult.newSuccessResult();
	}

	/**
	 * @category 调用存储过程
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @param database
	 *            入参|数据库名字|{@link java.lang.String}
	 * @param procedureName
	 *            入参|存储过程名字|{@link java.lang.String}
	 * @param paramList
	 *            入参|参数列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param resultList
	 *            出参|结果列表|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @return 0 失败<br/>
	 *         1 成功, 结果列表, [outParam1, outParam2, ... , outParamN]<br/>
	 * @throws UnsupportedOperationException 如果对应数据库不支持存储过程
	 */
	@InParams(param = {
			@Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class),
			@Param(name = "database", comment = "数据库名字", type = java.lang.String.class),
			@Param(name = "procedureName", comment = "存储过程名字", type = java.lang.String.class),
			@Param(name = "paramList", comment = "参数列表, [[in,入参值],[out,参数类型(如string)],[inout,出入参值,参数类型(如string)]]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@OutParams(param = { @Param(name = "resultList", comment = "结果列表, [outParam1, outParam2, ... , outParamN]", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "调用存储过程", style = "判断型", type = "同步组件", comment = "调用存储过程, 只能用于RDBMS", author = "Anonymous", date = "2016-12-05 04:19:19")
	public static TCResult P_callProcedure(String poolName, String database,
			String procedureName, JavaList paramList) {
		Database db = Database.fromString(requireNonNull(database, "database"));
		
		if (!db.isRelational()) {
			throw new UnsupportedOperationException("unsupported database " + database);
		}
		
		// TODO implement
		
		return TCResult.newSuccessResult();
	}

	/**
	 * @category 关闭连接
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "关闭连接", style = "判断型", type = "同步组件", comment = "关闭连接", author = "Anonymous", date = "2016-12-05 04:23:37")
	public static TCResult P_closeConnection(String poolName) {
		return TCResult.newSuccessResult();
	}

	/**
	 * @category 序列号操作
	 * @param poolName
	 *            入参|连接池名字|{@link java.lang.String}
	 * @param database
	 *            入参|数据库名字|{@link java.lang.String}
	 * @param sequenceName
	 *            入参|序列名字|{@link java.lang.String}
	 * @param sequenceLength
	 *            入参|序号长度|int
	 * @param value
	 *            出参|序号的当前值|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "poolName", comment = "连接池名字", type = java.lang.String.class),
			@Param(name = "database", comment = "数据库名字", type = java.lang.String.class),
			@Param(name = "sequenceName", comment = "序列名字", type = java.lang.String.class),
			@Param(name = "sequenceLength", comment = "序号长度", type = int.class) })
	@OutParams(param = { @Param(name = "value", comment = "序号的当前值", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "序列号操作", style = "判断型", type = "同步组件", comment = "序列号操作, 仅支持Oracle/DB2/Informix三种数据库,对于Informix,dual表是手工创建的", author = "Anonymous", date = "2016-12-05 04:29:04")
	public static TCResult P_getSequence(String poolName, String database,
			String sequenceName, int sequenceLength) {
		Database db = Database.fromString(requireNonNull(database, "database"));
		
		if (!(db == Database.ORACLE || db == Database.DB2 || db == Database.INFORMIX)) {
			throw new UnsupportedOperationException("unsupported database " + database);
		}
		
		// TODO implement
		
		return TCResult.newSuccessResult();
	}

	// --------------- util methods -------------------------
	
	private static <T> T requireNonNull(T t, String message) {
		if (t == null) {
			throw new NullPointerException(message);
		}
		return t;
	}
}
