package org.dongx.projects.user.sql;

import org.dongx.projects.user.domain.User;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * TODO
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DBConnectionManager {

	private static Logger logger = Logger.getLogger(DBConnectionManager.class.getName());
	
	private Connection connection;

	/**
	 * 删除用户表 SQL
	 */
	public static final String DROP_USERS_TABLE_DDL_SQL = "DROP TABLE users";

	/**
	 * 生成用户表 SQL
	 */
	public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
			"id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
			"name VARCHAR(16) NOT NULL, " +
			"password VARCHAR(64) NOT NULL, " +
			"email VARCHAR(64) NOT NULL, " +
			"phoneNumber VARCHAR(64) NOT NULL" +
			")";

	/**
	 * 插入用户表 SQL
	 */
	public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name,password,email,phoneNumber) VALUES " +
			"('A','******','a@gmail.com','1') , " +
			"('B','******','b@gmail.com','2') , " +
			"('C','******','c@gmail.com','3') , " +
			"('D','******','d@gmail.com','4') , " +
			"('E','******','e@gmail.com','5')";

	private static String mapColumnLabel(String fieldName) {
		return fieldName;
	}

	/**
	 * 数据类型与 ResultSet 方法名映射
	 */
	static Map<Class, String> typeMethodMappings = new HashMap<>();

	static {
		typeMethodMappings.put(Long.class, "getLong");
		typeMethodMappings.put(String.class, "getString");
	}

	/**
	 * 设置数据库连接
	 * @param connection 数据库连接
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 获取数据库连接
	 *
	 * @return {@link Connection}
	 */
	public Connection getConnection() {
		try {
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/UserPlatformDB");
			return ds.getConnection();
		} catch (Exception e) {
			// JNDI 数据源失败采用 Class.forName 注入
			logger.severe("JNDI injected datasource failed, used Class.ForName() to injected");
			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				String databaseURL = "jdbc:derby:D:/db/user-platform;create=true";
				return DriverManager.getConnection(databaseURL);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 释放数据库连接
	 */
	public void releaseConnection() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getCause());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// 通过 ClassLoader 加载 java.sql.DriverManager -> static {} 模块
		// DriverManager.setLogWriter(new PrintWriter(System.out));

		// Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        // Driver driver = DriverManager.getDriver("jdbc:derby:/db/user-platform;create=true");
        // Connection connection = driver.connect("jdbc:derby:/db/user-platform;create=true", new Properties());

		String databaseURL = "jdbc:derby:D:/db/user-platform;create=true";
		Connection connection = DriverManager.getConnection(databaseURL);
		
		try (Statement statement = connection.createStatement()) {
			// 删除 users 表 返回 false
			System.out.println(statement.execute(DROP_USERS_TABLE_DDL_SQL));
			// 创建 users 表 返回 false
			System.out.println(statement.execute(CREATE_USERS_TABLE_DDL_SQL));
			// 返回插入的条数 5
			System.out.println(statement.executeUpdate(INSERT_USER_DML_SQL));

			// 执行查询语句（DML）
			ResultSet resultSet = statement.executeQuery("SELECT id, name, password, email, phoneNumber FROM users");
			
			// 创建 User Bean 忽略 Object 类的属性和方法
			BeanInfo userBeanInfo = Introspector.getBeanInfo(User.class, Object.class);
			// userBeanInfo 所有的 Properties 属性信息
			for (PropertyDescriptor propertyDescriptor : userBeanInfo.getPropertyDescriptors()) {
				System.out.println(propertyDescriptor.getName() + ", " + propertyDescriptor.getPropertyType());
			}
			
			// ORM 映射
			while (resultSet.next()) {
				User user = new User();
				
				// ResultSet 元信息
				ResultSetMetaData metaData = resultSet.getMetaData();
				System.out.println("当前表的名称：" + metaData.getTableName(1));
				System.out.println("当前表的列个数：" + metaData.getColumnCount());
				// 数据库中是从 1 开始计数
				for (int i = 1; i < metaData.getColumnCount(); i++) {
					System.out.println("列名称：" + metaData.getColumnLabel(i) + ", 类型：" + metaData.getColumnClassName(i));
				}
				
				// 生成 getAll SQL
				StringBuilder queryAllUsersSQLBuilder = new StringBuilder("SELECT");
				for (int i = 1; i < metaData.getColumnCount(); i++) {
					queryAllUsersSQLBuilder.append(" ").append(metaData.getColumnLabel(i)).append(",");
				}
				// 移除最后一个 ","
				queryAllUsersSQLBuilder.deleteCharAt(queryAllUsersSQLBuilder.length() - 1);
				queryAllUsersSQLBuilder.append(" FROM ").append(metaData.getTableName(1));
				
				System.out.println(queryAllUsersSQLBuilder);

				// 方法直接调用（编译时，生成字节码）
				// user.setId(resultSet.getLong("id"));
				// user.setName(resultSet.getString("name"));
				// user.setPassword(resultSet.getString("password"));
				// user.setEmail(resultSet.getString("email"));
				// user.setPhoneNumber(resultSet.getString("phoneNumber"));
				
				// 需要利用反射 API，来实现字节码提升

				// User 类由容器管理时是通过配置文件 类名注入，本质上是 ClassLoader.loadClass -> Class.newInstance()
				// ORM 映射核心思想：通过反射执行代码（性能相对开销大）
				for (PropertyDescriptor propertyDescriptor : userBeanInfo.getPropertyDescriptors()) {
					String fieldName = propertyDescriptor.getName();
					Class fieldType = propertyDescriptor.getPropertyType();
					String methodName = typeMethodMappings.get(fieldType);
					// 可能存在映射关系（不过此处是相等的）
					String columnLabel = mapColumnLabel(fieldName);
					Method resultSetMethod = ResultSet.class.getMethod(methodName, String.class);
					// 通过放射调用 getXXX(String) 方法
					Object resultValue = resultSetMethod.invoke(resultSet, columnLabel);
					// 获取 User 类 Setter方法
					// PropertyDescriptor ReadMethod 等于 Getter 方法
					// PropertyDescriptor WriteMethod 等于 Setter 方法
					Method setterMethodFromUser = propertyDescriptor.getWriteMethod();
					// 以 id 为例，  user.setId(resultSet.getLong("id"));
					setterMethodFromUser.invoke(user, resultValue);
				}
				System.out.println(user);
			}
		}
	}
}
