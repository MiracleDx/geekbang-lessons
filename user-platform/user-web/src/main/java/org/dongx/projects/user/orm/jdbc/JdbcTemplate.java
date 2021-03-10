package org.dongx.projects.user.orm.jdbc;

import org.apache.commons.lang.ClassUtils;
import org.dongx.function.ThrowableFunction;
import org.dongx.projects.user.sql.DBConnectionManager;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Jdbc ORM 映射类
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class JdbcTemplate {

	private final DBConnectionManager dbConnectionManager;

	/**
	 * jdbc模板
	 *
	 * @param dbConnectionManager 数据库连接管理器
	 */
	public JdbcTemplate(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	/**
	 * 数据类型与 ResultSet 方法名映射，获取值
	 */
	static Map<Class, String> resultSetMethodMappings = new HashMap<>();

	/**
	 * 数据类型与 preparedStatement 方法名映射，设置参数
	 */
	static Map<Class, String> preparedStatementMethodMappings = new HashMap<>();

	static {
		resultSetMethodMappings.put(Long.class, "getLong");
		resultSetMethodMappings.put(String.class, "getString");

		// long
		preparedStatementMethodMappings.put(Long.class, "setLong"); 
		preparedStatementMethodMappings.put(String.class, "setString");
	}

	/**
	 * 执行查询
	 *
	 * @param sql sql
	 * @param function 自定义 ResultSet 处理方法
	 * @param exceptionHandler 自定义异常处理
	 * @param args 传入的 SQL 参数
	 * @return 返回值
	 */
	public <T> T executeQuery(String sql, ThrowableFunction<ResultSet, T> function, Consumer<Throwable> exceptionHandler, Object... args) {
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				Class argType = arg.getClass();
				// 包装类型转换成原始类型
				Class wrapperType = ClassUtils.wrapperToPrimitive(argType);
				// 防止 String 等类型无法转换
				if (wrapperType == null) {
					wrapperType = argType;
				}
				
				// 获取设置参数的方法名
				String methodName = preparedStatementMethodMappings.get(argType);
				// 设置参数的方法 参数均为 原始类型
				Method method = PreparedStatement.class.getMethod(methodName, int.class, wrapperType);
				// 请求方法
				method.invoke(preparedStatement, i + 1, arg);
			}
			ResultSet resultSet = preparedStatement.executeQuery();
			// 返回一个 POJO List -> ResultSet -> POJO List
			// ResultSet -> T
			return function.apply(resultSet);
		} catch (Throwable e) {
			exceptionHandler.accept(e);
		}
		return null;
	}

	public Integer executeUpdate(String sql, Consumer<Throwable> exceptionHandler, Object... args) {
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				Class argType = arg.getClass();
				// 包装类型转换成原始类型
				Class wrapperType = ClassUtils.wrapperToPrimitive(argType);
				// 防止 String 等类型无法转换
				if (wrapperType == null) {
					wrapperType = argType;
				}
				
				// 获取设置参数的方法名
				String methodName = preparedStatementMethodMappings.get(argType);
				// 设置参数的方法 参数均为 原始类型
				Method method = PreparedStatement.class.getMethod(methodName, int.class, wrapperType);
				// 请求方法
				method.invoke(preparedStatement, i + 1, arg);
			}
			return preparedStatement.executeUpdate();
		} catch (Throwable e) {
			exceptionHandler.accept(e);
		}
		return null;
	}

	/**
	 * 类属性映射方法
	 * @param <T> obj
	 * @param clazz 该类的 class
	 * @param resultSet {@link ResultSet}
	 * @return <T>
	 * @throws Exception
	 */
	public <T> T objMapping(Class<T> clazz, ResultSet resultSet) throws Exception {
		T obj = clazz.newInstance();
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
		for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
			String fieldName = propertyDescriptor.getName();
			Class fieldType = propertyDescriptor.getPropertyType();
			String methodName = resultSetMethodMappings.get(fieldType);
			// 可能存在映射关系（不过此处是相等的）
			String columnLabel = mapColumnLabel(fieldName);
			Method resultSetMethod = ResultSet.class.getMethod(methodName, String.class);
			// 通过放射调用 getXXX(String) 方法
			Object resultValue = resultSetMethod.invoke(resultSet, columnLabel);
			// 获取 Setter方法
			// PropertyDescriptor ReadMethod 等于 Getter 方法
			// PropertyDescriptor WriteMethod 等于 Setter 方法
			Method setterMethodFromUser = propertyDescriptor.getWriteMethod();
			setterMethodFromUser.invoke(obj, resultValue);
		}
		return obj;
	}

	/**
	 * 获得连接
	 *
	 * @return {@link Connection}
	 */
	private Connection getConnection() {
		return dbConnectionManager.getConnection();
	}

	/**
	 * 获取列名映射
	 *
	 * @param fieldName 字段名
	 * @return {@link String}
	 */
	private static String mapColumnLabel(String fieldName) {
		return fieldName;
	}
}
