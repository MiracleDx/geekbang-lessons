package org.dongx.projects.user.repository;

import org.dongx.projects.user.domain.User;
import org.dongx.projects.user.orm.JdbcTemplate;
import org.dongx.projects.user.sql.DBConnectionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 数据库 {@link UserRepository} 实现
 *
 * @author <a href="mailto:dongxiang886@gmail.com>Dongx</a>
 * @since
 */
public class DatabaseUserRepository implements UserRepository {

	private static Logger logger = Logger.getLogger(DatabaseUserRepository.class.getName());

	/**
	 * 插入用户 SQL
	 */
	public static final String INSERT_USER_DML_SQL = "INSERT INTO users(name, password, email, phoneNumber) VALUES (?, ?, ?, ?)";

	/**
	 * 保存用户 SQL
	 */
	public static final String QUERY_ALL_USERS_DML_SQL = "SELECT id, name, password, email, phoneNumber FROM users";

	/**
	 * 通用处理方式
	 */
	private static Consumer<Throwable> COMMON_EXCEPTION_HANDLER = e -> logger.log(Level.SEVERE, e.getMessage());

	private JdbcTemplate jdbcTemplate;
	
	public DatabaseUserRepository(DBConnectionManager dbConnectionManager) {
		this.jdbcTemplate = new JdbcTemplate(dbConnectionManager);
	}
	
	@Override
	public boolean save(User user) {
		return jdbcTemplate
				.executeUpdate(INSERT_USER_DML_SQL, COMMON_EXCEPTION_HANDLER, user.getName(),
						user.getPassword(), user.getEmail(), user.getPhoneNumber()) > 0;
	}

	@Override
	public boolean deleteById(Long userId) {
		return false;
	}

	@Override
	public boolean update(User user) {
		return false;
	}

	@Override
	public User getById(Long userId) {
		return null;
	}

	@Override
	public User getByNameAndPassword(String userName, String password) {
		return jdbcTemplate.executeQuery("SELECT id, name, password, email, phoneNumber FROM users where name = ? and password = ?",
				resultSet -> {
					User user = null;
					while (resultSet.next()) {
						user = jdbcTemplate.objMapping(User.class, resultSet);
						return user;
					}
					return user;
				}, COMMON_EXCEPTION_HANDLER, userName, password);
	}

	@Override
	public Collection<User> getAll() {
		return jdbcTemplate.executeQuery(QUERY_ALL_USERS_DML_SQL, resultSet -> {
			List<User> users = new ArrayList<>();
			while (resultSet.next()) { // 如果存在并且游标滚动 // SQLException
				User user = jdbcTemplate.objMapping(User.class, resultSet);
				users.add(user);
			}
			return users;
		}, COMMON_EXCEPTION_HANDLER);
	}
	
}
